package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICResultAsserts.*;
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
import org.zimnat.domain.LICResult;
import org.zimnat.repository.LICResultRepository;

/**
 * Integration tests for the {@link LICResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICResultResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lic-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICResultRepository lICResultRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICResultMockMvc;

    private LICResult lICResult;

    private LICResult insertedLICResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICResult createEntity(EntityManager em) {
        LICResult lICResult = new LICResult()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .theVersion(DEFAULT_THE_VERSION)
            .partnerToken(DEFAULT_PARTNER_TOKEN)
            .licenceID(DEFAULT_LICENCE_ID)
            .function(DEFAULT_FUNCTION);
        return lICResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICResult createUpdatedEntity(EntityManager em) {
        LICResult lICResult = new LICResult()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .licenceID(UPDATED_LICENCE_ID)
            .function(UPDATED_FUNCTION);
        return lICResult;
    }

    @BeforeEach
    public void initTest() {
        lICResult = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICResult != null) {
            lICResultRepository.delete(insertedLICResult);
            insertedLICResult = null;
        }
    }

    @Test
    @Transactional
    void createLICResult() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICResult
        var returnedLICResult = om.readValue(
            restLICResultMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICResult.class
        );

        // Validate the LICResult in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICResultUpdatableFieldsEquals(returnedLICResult, getPersistedLICResult(returnedLICResult));

        insertedLICResult = returnedLICResult;
    }

    @Test
    @Transactional
    void createLICResultWithExistingId() throws Exception {
        // Create the LICResult with an existing ID
        lICResult.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartnerReferenceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICResult.setPartnerReference(null);

        // Create the LICResult, which fails.

        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICResult.setTheDate(null);

        // Create the LICResult, which fails.

        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICResult.setTheVersion(null);

        // Create the LICResult, which fails.

        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartnerTokenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICResult.setPartnerToken(null);

        // Create the LICResult, which fails.

        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLicenceIDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICResult.setLicenceID(null);

        // Create the LICResult, which fails.

        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICResult.setFunction(null);

        // Create the LICResult, which fails.

        restLICResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLICResults() throws Exception {
        // Initialize the database
        insertedLICResult = lICResultRepository.saveAndFlush(lICResult);

        // Get all the lICResultList
        restLICResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].theVersion").value(hasItem(DEFAULT_THE_VERSION)))
            .andExpect(jsonPath("$.[*].partnerToken").value(hasItem(DEFAULT_PARTNER_TOKEN)))
            .andExpect(jsonPath("$.[*].licenceID").value(hasItem(DEFAULT_LICENCE_ID)))
            .andExpect(jsonPath("$.[*].function").value(hasItem(DEFAULT_FUNCTION)));
    }

    @Test
    @Transactional
    void getLICResult() throws Exception {
        // Initialize the database
        insertedLICResult = lICResultRepository.saveAndFlush(lICResult);

        // Get the lICResult
        restLICResultMockMvc
            .perform(get(ENTITY_API_URL_ID, lICResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICResult.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.theVersion").value(DEFAULT_THE_VERSION))
            .andExpect(jsonPath("$.partnerToken").value(DEFAULT_PARTNER_TOKEN))
            .andExpect(jsonPath("$.licenceID").value(DEFAULT_LICENCE_ID))
            .andExpect(jsonPath("$.function").value(DEFAULT_FUNCTION));
    }

    @Test
    @Transactional
    void getNonExistingLICResult() throws Exception {
        // Get the lICResult
        restLICResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICResult() throws Exception {
        // Initialize the database
        insertedLICResult = lICResultRepository.saveAndFlush(lICResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICResult
        LICResult updatedLICResult = lICResultRepository.findById(lICResult.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLICResult are not directly saved in db
        em.detach(updatedLICResult);
        updatedLICResult
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .licenceID(UPDATED_LICENCE_ID)
            .function(UPDATED_FUNCTION);

        restLICResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICResult.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICResult))
            )
            .andExpect(status().isOk());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICResultToMatchAllProperties(updatedLICResult);
    }

    @Test
    @Transactional
    void putNonExistingLICResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResult.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICResult.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResult.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResult.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICResultWithPatch() throws Exception {
        // Initialize the database
        insertedLICResult = lICResultRepository.saveAndFlush(lICResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICResult using partial update
        LICResult partialUpdatedLICResult = new LICResult();
        partialUpdatedLICResult.setId(lICResult.getId());

        partialUpdatedLICResult.theDate(UPDATED_THE_DATE).function(UPDATED_FUNCTION);

        restLICResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICResult))
            )
            .andExpect(status().isOk());

        // Validate the LICResult in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICResultUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLICResult, lICResult),
            getPersistedLICResult(lICResult)
        );
    }

    @Test
    @Transactional
    void fullUpdateLICResultWithPatch() throws Exception {
        // Initialize the database
        insertedLICResult = lICResultRepository.saveAndFlush(lICResult);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICResult using partial update
        LICResult partialUpdatedLICResult = new LICResult();
        partialUpdatedLICResult.setId(lICResult.getId());

        partialUpdatedLICResult
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .licenceID(UPDATED_LICENCE_ID)
            .function(UPDATED_FUNCTION);

        restLICResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICResult))
            )
            .andExpect(status().isOk());

        // Validate the LICResult in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICResultUpdatableFieldsEquals(partialUpdatedLICResult, getPersistedLICResult(partialUpdatedLICResult));
    }

    @Test
    @Transactional
    void patchNonExistingLICResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResult.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResult.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICResult() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResult.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICResult)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICResult in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICResult() throws Exception {
        // Initialize the database
        insertedLICResult = lICResultRepository.saveAndFlush(lICResult);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICResult
        restLICResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICResultRepository.count();
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

    protected LICResult getPersistedLICResult(LICResult lICResult) {
        return lICResultRepository.findById(lICResult.getId()).orElseThrow();
    }

    protected void assertPersistedLICResultToMatchAllProperties(LICResult expectedLICResult) {
        assertLICResultAllPropertiesEquals(expectedLICResult, getPersistedLICResult(expectedLICResult));
    }

    protected void assertPersistedLICResultToMatchUpdatableProperties(LICResult expectedLICResult) {
        assertLICResultAllUpdatablePropertiesEquals(expectedLICResult, getPersistedLICResult(expectedLICResult));
    }
}
