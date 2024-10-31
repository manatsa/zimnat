package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.SBUAsserts.*;
import static org.zimnat.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
import org.zimnat.domain.SBU;
import org.zimnat.domain.enumeration.Status;
import org.zimnat.repository.SBURepository;

/**
 * Integration tests for the {@link SBUResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SBUResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.FRESH;

    private static final String ENTITY_API_URL = "/api/sbus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SBURepository sBURepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSBUMockMvc;

    private SBU sBU;

    private SBU insertedSBU;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SBU createEntity(EntityManager em) {
        SBU sBU = new SBU().name(DEFAULT_NAME).code(DEFAULT_CODE).address(DEFAULT_ADDRESS).status(DEFAULT_STATUS);
        return sBU;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SBU createUpdatedEntity(EntityManager em) {
        SBU sBU = new SBU().name(UPDATED_NAME).code(UPDATED_CODE).address(UPDATED_ADDRESS).status(UPDATED_STATUS);
        return sBU;
    }

    @BeforeEach
    public void initTest() {
        sBU = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSBU != null) {
            sBURepository.delete(insertedSBU);
            insertedSBU = null;
        }
    }

    @Test
    @Transactional
    void createSBU() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SBU
        var returnedSBU = om.readValue(
            restSBUMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SBU.class
        );

        // Validate the SBU in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSBUUpdatableFieldsEquals(returnedSBU, getPersistedSBU(returnedSBU));

        insertedSBU = returnedSBU;
    }

    @Test
    @Transactional
    void createSBUWithExistingId() throws Exception {
        // Create the SBU with an existing ID
        sBU.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSBUMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sBU.setName(null);

        // Create the SBU, which fails.

        restSBUMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sBU.setCode(null);

        // Create the SBU, which fails.

        restSBUMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sBU.setAddress(null);

        // Create the SBU, which fails.

        restSBUMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sBU.setStatus(null);

        // Create the SBU, which fails.

        restSBUMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSBUS() throws Exception {
        // Initialize the database
        insertedSBU = sBURepository.saveAndFlush(sBU);

        // Get all the sBUList
        restSBUMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sBU.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSBU() throws Exception {
        // Initialize the database
        insertedSBU = sBURepository.saveAndFlush(sBU);

        // Get the sBU
        restSBUMockMvc
            .perform(get(ENTITY_API_URL_ID, sBU.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sBU.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSBU() throws Exception {
        // Get the sBU
        restSBUMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSBU() throws Exception {
        // Initialize the database
        insertedSBU = sBURepository.saveAndFlush(sBU);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sBU
        SBU updatedSBU = sBURepository.findById(sBU.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSBU are not directly saved in db
        em.detach(updatedSBU);
        updatedSBU.name(UPDATED_NAME).code(UPDATED_CODE).address(UPDATED_ADDRESS).status(UPDATED_STATUS);

        restSBUMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSBU.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedSBU))
            )
            .andExpect(status().isOk());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSBUToMatchAllProperties(updatedSBU);
    }

    @Test
    @Transactional
    void putNonExistingSBU() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sBU.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSBUMockMvc
            .perform(put(ENTITY_API_URL_ID, sBU.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSBU() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sBU.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSBUMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sBU))
            )
            .andExpect(status().isBadRequest());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSBU() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sBU.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSBUMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSBUWithPatch() throws Exception {
        // Initialize the database
        insertedSBU = sBURepository.saveAndFlush(sBU);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sBU using partial update
        SBU partialUpdatedSBU = new SBU();
        partialUpdatedSBU.setId(sBU.getId());

        partialUpdatedSBU.code(UPDATED_CODE).address(UPDATED_ADDRESS);

        restSBUMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSBU.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSBU))
            )
            .andExpect(status().isOk());

        // Validate the SBU in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSBUUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSBU, sBU), getPersistedSBU(sBU));
    }

    @Test
    @Transactional
    void fullUpdateSBUWithPatch() throws Exception {
        // Initialize the database
        insertedSBU = sBURepository.saveAndFlush(sBU);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sBU using partial update
        SBU partialUpdatedSBU = new SBU();
        partialUpdatedSBU.setId(sBU.getId());

        partialUpdatedSBU.name(UPDATED_NAME).code(UPDATED_CODE).address(UPDATED_ADDRESS).status(UPDATED_STATUS);

        restSBUMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSBU.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSBU))
            )
            .andExpect(status().isOk());

        // Validate the SBU in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSBUUpdatableFieldsEquals(partialUpdatedSBU, getPersistedSBU(partialUpdatedSBU));
    }

    @Test
    @Transactional
    void patchNonExistingSBU() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sBU.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSBUMockMvc
            .perform(patch(ENTITY_API_URL_ID, sBU.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isBadRequest());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSBU() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sBU.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSBUMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sBU))
            )
            .andExpect(status().isBadRequest());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSBU() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sBU.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSBUMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sBU)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SBU in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSBU() throws Exception {
        // Initialize the database
        insertedSBU = sBURepository.saveAndFlush(sBU);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sBU
        restSBUMockMvc.perform(delete(ENTITY_API_URL_ID, sBU.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sBURepository.count();
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

    protected SBU getPersistedSBU(SBU sBU) {
        return sBURepository.findById(sBU.getId()).orElseThrow();
    }

    protected void assertPersistedSBUToMatchAllProperties(SBU expectedSBU) {
        assertSBUAllPropertiesEquals(expectedSBU, getPersistedSBU(expectedSBU));
    }

    protected void assertPersistedSBUToMatchUpdatableProperties(SBU expectedSBU) {
        assertSBUAllUpdatablePropertiesEquals(expectedSBU, getPersistedSBU(expectedSBU));
    }
}
