package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.SettingAsserts.*;
import static org.zimnat.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.zimnat.IntegrationTest;
import org.zimnat.domain.Setting;
import org.zimnat.repository.SettingRepository;

/**
 * Integration tests for the {@link SettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SettingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ADMIN_ACCESS = false;
    private static final Boolean UPDATED_ADMIN_ACCESS = true;

    private static final String ENTITY_API_URL = "/api/settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingMockMvc;

    private Setting setting;

    private Setting insertedSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createEntity(EntityManager em) {
        Setting setting = new Setting()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .lastDate(DEFAULT_LAST_DATE)
            .adminAccess(DEFAULT_ADMIN_ACCESS);
        return setting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createUpdatedEntity(EntityManager em) {
        Setting setting = new Setting()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .lastDate(UPDATED_LAST_DATE)
            .adminAccess(UPDATED_ADMIN_ACCESS);
        return setting;
    }

    @BeforeEach
    public void initTest() {
        setting = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSetting != null) {
            settingRepository.delete(insertedSetting);
            insertedSetting = null;
        }
    }

    @Test
    @Transactional
    void createSetting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Setting
        var returnedSetting = om.readValue(
            restSettingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Setting.class
        );

        // Validate the Setting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSettingUpdatableFieldsEquals(returnedSetting, getPersistedSetting(returnedSetting));

        insertedSetting = returnedSetting;
    }

    @Test
    @Transactional
    void createSettingWithExistingId() throws Exception {
        // Create the Setting with an existing ID
        setting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        setting.setName(null);

        // Create the Setting, which fails.

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        setting.setValue(null);

        // Create the Setting, which fails.

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffectiveDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        setting.setEffectiveDate(null);

        // Create the Setting, which fails.

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSettings() throws Exception {
        // Initialize the database
        insertedSetting = settingRepository.saveAndFlush(setting);

        // Get all the settingList
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastDate").value(hasItem(DEFAULT_LAST_DATE.toString())))
            .andExpect(jsonPath("$.[*].adminAccess").value(hasItem(DEFAULT_ADMIN_ACCESS.booleanValue())));
    }

    @Test
    @Transactional
    void getSetting() throws Exception {
        // Initialize the database
        insertedSetting = settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(setting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.lastDate").value(DEFAULT_LAST_DATE.toString()))
            .andExpect(jsonPath("$.adminAccess").value(DEFAULT_ADMIN_ACCESS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSetting() throws Exception {
        // Initialize the database
        insertedSetting = settingRepository.saveAndFlush(setting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the setting
        Setting updatedSetting = settingRepository.findById(setting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);
        updatedSetting
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .lastDate(UPDATED_LAST_DATE)
            .adminAccess(UPDATED_ADMIN_ACCESS);

        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSetting))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSettingToMatchAllProperties(updatedSetting);
    }

    @Test
    @Transactional
    void putNonExistingSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(put(ENTITY_API_URL_ID, setting.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(setting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(setting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSettingWithPatch() throws Exception {
        // Initialize the database
        insertedSetting = settingRepository.saveAndFlush(setting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the setting using partial update
        Setting partialUpdatedSetting = new Setting();
        partialUpdatedSetting.setId(setting.getId());

        partialUpdatedSetting
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .lastDate(UPDATED_LAST_DATE);

        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSetting))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSettingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSetting, setting), getPersistedSetting(setting));
    }

    @Test
    @Transactional
    void fullUpdateSettingWithPatch() throws Exception {
        // Initialize the database
        insertedSetting = settingRepository.saveAndFlush(setting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the setting using partial update
        Setting partialUpdatedSetting = new Setting();
        partialUpdatedSetting.setId(setting.getId());

        partialUpdatedSetting
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .lastDate(UPDATED_LAST_DATE)
            .adminAccess(UPDATED_ADMIN_ACCESS);

        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSetting))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSettingUpdatableFieldsEquals(partialUpdatedSetting, getPersistedSetting(partialUpdatedSetting));
    }

    @Test
    @Transactional
    void patchNonExistingSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, setting.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(setting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(setting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        setting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(setting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Setting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSetting() throws Exception {
        // Initialize the database
        insertedSetting = settingRepository.saveAndFlush(setting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the setting
        restSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, setting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return settingRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Setting getPersistedSetting(Setting setting) {
        return settingRepository.findById(setting.getId()).orElseThrow();
    }

    protected void assertPersistedSettingToMatchAllProperties(Setting expectedSetting) {
        assertSettingAllPropertiesEquals(expectedSetting, getPersistedSetting(expectedSetting));
    }

    protected void assertPersistedSettingToMatchUpdatableProperties(Setting expectedSetting) {
        assertSettingAllUpdatablePropertiesEquals(expectedSetting, getPersistedSetting(expectedSetting));
    }
}
