package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.BatchStatusAsserts.*;
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
import org.zimnat.domain.BatchStatus;
import org.zimnat.repository.BatchStatusRepository;

/**
 * Integration tests for the {@link BatchStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BatchStatusResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TOKEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/batch-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BatchStatusRepository batchStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatchStatusMockMvc;

    private BatchStatus batchStatus;

    private BatchStatus insertedBatchStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchStatus createEntity(EntityManager em) {
        BatchStatus batchStatus = new BatchStatus()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .theVersion(DEFAULT_THE_VERSION)
            .partnerToken(DEFAULT_PARTNER_TOKEN);
        return batchStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchStatus createUpdatedEntity(EntityManager em) {
        BatchStatus batchStatus = new BatchStatus()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);
        return batchStatus;
    }

    @BeforeEach
    public void initTest() {
        batchStatus = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBatchStatus != null) {
            batchStatusRepository.delete(insertedBatchStatus);
            insertedBatchStatus = null;
        }
    }

    @Test
    @Transactional
    void createBatchStatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BatchStatus
        var returnedBatchStatus = om.readValue(
            restBatchStatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BatchStatus.class
        );

        // Validate the BatchStatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBatchStatusUpdatableFieldsEquals(returnedBatchStatus, getPersistedBatchStatus(returnedBatchStatus));

        insertedBatchStatus = returnedBatchStatus;
    }

    @Test
    @Transactional
    void createBatchStatusWithExistingId() throws Exception {
        // Create the BatchStatus with an existing ID
        batchStatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isBadRequest());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartnerReferenceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        batchStatus.setPartnerReference(null);

        // Create the BatchStatus, which fails.

        restBatchStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        batchStatus.setTheDate(null);

        // Create the BatchStatus, which fails.

        restBatchStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        batchStatus.setTheVersion(null);

        // Create the BatchStatus, which fails.

        restBatchStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartnerTokenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        batchStatus.setPartnerToken(null);

        // Create the BatchStatus, which fails.

        restBatchStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBatchStatuses() throws Exception {
        // Initialize the database
        insertedBatchStatus = batchStatusRepository.saveAndFlush(batchStatus);

        // Get all the batchStatusList
        restBatchStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].theVersion").value(hasItem(DEFAULT_THE_VERSION)))
            .andExpect(jsonPath("$.[*].partnerToken").value(hasItem(DEFAULT_PARTNER_TOKEN)));
    }

    @Test
    @Transactional
    void getBatchStatus() throws Exception {
        // Initialize the database
        insertedBatchStatus = batchStatusRepository.saveAndFlush(batchStatus);

        // Get the batchStatus
        restBatchStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, batchStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batchStatus.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.theVersion").value(DEFAULT_THE_VERSION))
            .andExpect(jsonPath("$.partnerToken").value(DEFAULT_PARTNER_TOKEN));
    }

    @Test
    @Transactional
    void getNonExistingBatchStatus() throws Exception {
        // Get the batchStatus
        restBatchStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBatchStatus() throws Exception {
        // Initialize the database
        insertedBatchStatus = batchStatusRepository.saveAndFlush(batchStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchStatus
        BatchStatus updatedBatchStatus = batchStatusRepository.findById(batchStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBatchStatus are not directly saved in db
        em.detach(updatedBatchStatus);
        updatedBatchStatus
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);

        restBatchStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBatchStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBatchStatus))
            )
            .andExpect(status().isOk());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBatchStatusToMatchAllProperties(updatedBatchStatus);
    }

    @Test
    @Transactional
    void putNonExistingBatchStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batchStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(batchStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBatchStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(batchStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBatchStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatchStatusWithPatch() throws Exception {
        // Initialize the database
        insertedBatchStatus = batchStatusRepository.saveAndFlush(batchStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchStatus using partial update
        BatchStatus partialUpdatedBatchStatus = new BatchStatus();
        partialUpdatedBatchStatus.setId(batchStatus.getId());

        partialUpdatedBatchStatus.theDate(UPDATED_THE_DATE).theVersion(UPDATED_THE_VERSION);

        restBatchStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatchStatus))
            )
            .andExpect(status().isOk());

        // Validate the BatchStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchStatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBatchStatus, batchStatus),
            getPersistedBatchStatus(batchStatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateBatchStatusWithPatch() throws Exception {
        // Initialize the database
        insertedBatchStatus = batchStatusRepository.saveAndFlush(batchStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchStatus using partial update
        BatchStatus partialUpdatedBatchStatus = new BatchStatus();
        partialUpdatedBatchStatus.setId(batchStatus.getId());

        partialUpdatedBatchStatus
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);

        restBatchStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatchStatus))
            )
            .andExpect(status().isOk());

        // Validate the BatchStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchStatusUpdatableFieldsEquals(partialUpdatedBatchStatus, getPersistedBatchStatus(partialUpdatedBatchStatus));
    }

    @Test
    @Transactional
    void patchNonExistingBatchStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batchStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batchStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBatchStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batchStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBatchStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(batchStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBatchStatus() throws Exception {
        // Initialize the database
        insertedBatchStatus = batchStatusRepository.saveAndFlush(batchStatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the batchStatus
        restBatchStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, batchStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return batchStatusRepository.count();
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

    protected BatchStatus getPersistedBatchStatus(BatchStatus batchStatus) {
        return batchStatusRepository.findById(batchStatus.getId()).orElseThrow();
    }

    protected void assertPersistedBatchStatusToMatchAllProperties(BatchStatus expectedBatchStatus) {
        assertBatchStatusAllPropertiesEquals(expectedBatchStatus, getPersistedBatchStatus(expectedBatchStatus));
    }

    protected void assertPersistedBatchStatusToMatchUpdatableProperties(BatchStatus expectedBatchStatus) {
        assertBatchStatusAllUpdatablePropertiesEquals(expectedBatchStatus, getPersistedBatchStatus(expectedBatchStatus));
    }
}
