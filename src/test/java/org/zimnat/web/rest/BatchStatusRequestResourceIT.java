package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.BatchStatusRequestAsserts.*;
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
import org.zimnat.domain.BatchStatusRequest;
import org.zimnat.repository.BatchStatusRequestRepository;

/**
 * Integration tests for the {@link BatchStatusRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BatchStatusRequestResourceIT {

    private static final String DEFAULT_THE_FUNCTION = "AAAAAAAAAA";
    private static final String UPDATED_THE_FUNCTION = "BBBBBBBBBB";

    private static final String DEFAULT_INSURANCE_ID_BATCH = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_ID_BATCH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/batch-status-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BatchStatusRequestRepository batchStatusRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatchStatusRequestMockMvc;

    private BatchStatusRequest batchStatusRequest;

    private BatchStatusRequest insertedBatchStatusRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchStatusRequest createEntity(EntityManager em) {
        BatchStatusRequest batchStatusRequest = new BatchStatusRequest()
            .theFunction(DEFAULT_THE_FUNCTION)
            .insuranceIDBatch(DEFAULT_INSURANCE_ID_BATCH);
        return batchStatusRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchStatusRequest createUpdatedEntity(EntityManager em) {
        BatchStatusRequest batchStatusRequest = new BatchStatusRequest()
            .theFunction(UPDATED_THE_FUNCTION)
            .insuranceIDBatch(UPDATED_INSURANCE_ID_BATCH);
        return batchStatusRequest;
    }

    @BeforeEach
    public void initTest() {
        batchStatusRequest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBatchStatusRequest != null) {
            batchStatusRequestRepository.delete(insertedBatchStatusRequest);
            insertedBatchStatusRequest = null;
        }
    }

    @Test
    @Transactional
    void createBatchStatusRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BatchStatusRequest
        var returnedBatchStatusRequest = om.readValue(
            restBatchStatusRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatusRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BatchStatusRequest.class
        );

        // Validate the BatchStatusRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBatchStatusRequestUpdatableFieldsEquals(
            returnedBatchStatusRequest,
            getPersistedBatchStatusRequest(returnedBatchStatusRequest)
        );

        insertedBatchStatusRequest = returnedBatchStatusRequest;
    }

    @Test
    @Transactional
    void createBatchStatusRequestWithExistingId() throws Exception {
        // Create the BatchStatusRequest with an existing ID
        batchStatusRequest.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchStatusRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatusRequest)))
            .andExpect(status().isBadRequest());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheFunctionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        batchStatusRequest.setTheFunction(null);

        // Create the BatchStatusRequest, which fails.

        restBatchStatusRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatusRequest)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInsuranceIDBatchIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        batchStatusRequest.setInsuranceIDBatch(null);

        // Create the BatchStatusRequest, which fails.

        restBatchStatusRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatusRequest)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBatchStatusRequests() throws Exception {
        // Initialize the database
        insertedBatchStatusRequest = batchStatusRequestRepository.saveAndFlush(batchStatusRequest);

        // Get all the batchStatusRequestList
        restBatchStatusRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchStatusRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].theFunction").value(hasItem(DEFAULT_THE_FUNCTION)))
            .andExpect(jsonPath("$.[*].insuranceIDBatch").value(hasItem(DEFAULT_INSURANCE_ID_BATCH)));
    }

    @Test
    @Transactional
    void getBatchStatusRequest() throws Exception {
        // Initialize the database
        insertedBatchStatusRequest = batchStatusRequestRepository.saveAndFlush(batchStatusRequest);

        // Get the batchStatusRequest
        restBatchStatusRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, batchStatusRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batchStatusRequest.getId().intValue()))
            .andExpect(jsonPath("$.theFunction").value(DEFAULT_THE_FUNCTION))
            .andExpect(jsonPath("$.insuranceIDBatch").value(DEFAULT_INSURANCE_ID_BATCH));
    }

    @Test
    @Transactional
    void getNonExistingBatchStatusRequest() throws Exception {
        // Get the batchStatusRequest
        restBatchStatusRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBatchStatusRequest() throws Exception {
        // Initialize the database
        insertedBatchStatusRequest = batchStatusRequestRepository.saveAndFlush(batchStatusRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchStatusRequest
        BatchStatusRequest updatedBatchStatusRequest = batchStatusRequestRepository.findById(batchStatusRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBatchStatusRequest are not directly saved in db
        em.detach(updatedBatchStatusRequest);
        updatedBatchStatusRequest.theFunction(UPDATED_THE_FUNCTION).insuranceIDBatch(UPDATED_INSURANCE_ID_BATCH);

        restBatchStatusRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBatchStatusRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBatchStatusRequest))
            )
            .andExpect(status().isOk());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBatchStatusRequestToMatchAllProperties(updatedBatchStatusRequest);
    }

    @Test
    @Transactional
    void putNonExistingBatchStatusRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatusRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchStatusRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batchStatusRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(batchStatusRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBatchStatusRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatusRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(batchStatusRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBatchStatusRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatusRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchStatusRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatchStatusRequestWithPatch() throws Exception {
        // Initialize the database
        insertedBatchStatusRequest = batchStatusRequestRepository.saveAndFlush(batchStatusRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchStatusRequest using partial update
        BatchStatusRequest partialUpdatedBatchStatusRequest = new BatchStatusRequest();
        partialUpdatedBatchStatusRequest.setId(batchStatusRequest.getId());

        partialUpdatedBatchStatusRequest.theFunction(UPDATED_THE_FUNCTION).insuranceIDBatch(UPDATED_INSURANCE_ID_BATCH);

        restBatchStatusRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchStatusRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatchStatusRequest))
            )
            .andExpect(status().isOk());

        // Validate the BatchStatusRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchStatusRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBatchStatusRequest, batchStatusRequest),
            getPersistedBatchStatusRequest(batchStatusRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateBatchStatusRequestWithPatch() throws Exception {
        // Initialize the database
        insertedBatchStatusRequest = batchStatusRequestRepository.saveAndFlush(batchStatusRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchStatusRequest using partial update
        BatchStatusRequest partialUpdatedBatchStatusRequest = new BatchStatusRequest();
        partialUpdatedBatchStatusRequest.setId(batchStatusRequest.getId());

        partialUpdatedBatchStatusRequest.theFunction(UPDATED_THE_FUNCTION).insuranceIDBatch(UPDATED_INSURANCE_ID_BATCH);

        restBatchStatusRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchStatusRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatchStatusRequest))
            )
            .andExpect(status().isOk());

        // Validate the BatchStatusRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchStatusRequestUpdatableFieldsEquals(
            partialUpdatedBatchStatusRequest,
            getPersistedBatchStatusRequest(partialUpdatedBatchStatusRequest)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBatchStatusRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatusRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchStatusRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batchStatusRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batchStatusRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBatchStatusRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatusRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batchStatusRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBatchStatusRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchStatusRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchStatusRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(batchStatusRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchStatusRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBatchStatusRequest() throws Exception {
        // Initialize the database
        insertedBatchStatusRequest = batchStatusRequestRepository.saveAndFlush(batchStatusRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the batchStatusRequest
        restBatchStatusRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, batchStatusRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return batchStatusRequestRepository.count();
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

    protected BatchStatusRequest getPersistedBatchStatusRequest(BatchStatusRequest batchStatusRequest) {
        return batchStatusRequestRepository.findById(batchStatusRequest.getId()).orElseThrow();
    }

    protected void assertPersistedBatchStatusRequestToMatchAllProperties(BatchStatusRequest expectedBatchStatusRequest) {
        assertBatchStatusRequestAllPropertiesEquals(expectedBatchStatusRequest, getPersistedBatchStatusRequest(expectedBatchStatusRequest));
    }

    protected void assertPersistedBatchStatusRequestToMatchUpdatableProperties(BatchStatusRequest expectedBatchStatusRequest) {
        assertBatchStatusRequestAllUpdatablePropertiesEquals(
            expectedBatchStatusRequest,
            getPersistedBatchStatusRequest(expectedBatchStatusRequest)
        );
    }
}
