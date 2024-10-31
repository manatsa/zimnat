package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.FailureResponseAsserts.*;
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
import org.zimnat.domain.FailureResponse;
import org.zimnat.repository.FailureResponseRepository;

/**
 * Integration tests for the {@link FailureResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FailureResponseResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/failure-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FailureResponseRepository failureResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFailureResponseMockMvc;

    private FailureResponse failureResponse;

    private FailureResponse insertedFailureResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FailureResponse createEntity(EntityManager em) {
        FailureResponse failureResponse = new FailureResponse()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .date(DEFAULT_DATE)
            .version(DEFAULT_VERSION);
        return failureResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FailureResponse createUpdatedEntity(EntityManager em) {
        FailureResponse failureResponse = new FailureResponse()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .date(UPDATED_DATE)
            .version(UPDATED_VERSION);
        return failureResponse;
    }

    @BeforeEach
    public void initTest() {
        failureResponse = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFailureResponse != null) {
            failureResponseRepository.delete(insertedFailureResponse);
            insertedFailureResponse = null;
        }
    }

    @Test
    @Transactional
    void createFailureResponse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FailureResponse
        var returnedFailureResponse = om.readValue(
            restFailureResponseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(failureResponse)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FailureResponse.class
        );

        // Validate the FailureResponse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFailureResponseUpdatableFieldsEquals(returnedFailureResponse, getPersistedFailureResponse(returnedFailureResponse));

        insertedFailureResponse = returnedFailureResponse;
    }

    @Test
    @Transactional
    void createFailureResponseWithExistingId() throws Exception {
        // Create the FailureResponse with an existing ID
        failureResponse.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFailureResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(failureResponse)))
            .andExpect(status().isBadRequest());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFailureResponses() throws Exception {
        // Initialize the database
        insertedFailureResponse = failureResponseRepository.saveAndFlush(failureResponse);

        // Get all the failureResponseList
        restFailureResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(failureResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getFailureResponse() throws Exception {
        // Initialize the database
        insertedFailureResponse = failureResponseRepository.saveAndFlush(failureResponse);

        // Get the failureResponse
        restFailureResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, failureResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(failureResponse.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNonExistingFailureResponse() throws Exception {
        // Get the failureResponse
        restFailureResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFailureResponse() throws Exception {
        // Initialize the database
        insertedFailureResponse = failureResponseRepository.saveAndFlush(failureResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the failureResponse
        FailureResponse updatedFailureResponse = failureResponseRepository.findById(failureResponse.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFailureResponse are not directly saved in db
        em.detach(updatedFailureResponse);
        updatedFailureResponse.partnerReference(UPDATED_PARTNER_REFERENCE).date(UPDATED_DATE).version(UPDATED_VERSION);

        restFailureResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFailureResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFailureResponse))
            )
            .andExpect(status().isOk());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFailureResponseToMatchAllProperties(updatedFailureResponse);
    }

    @Test
    @Transactional
    void putNonExistingFailureResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        failureResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFailureResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, failureResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(failureResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFailureResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        failureResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFailureResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(failureResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFailureResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        failureResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFailureResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(failureResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFailureResponseWithPatch() throws Exception {
        // Initialize the database
        insertedFailureResponse = failureResponseRepository.saveAndFlush(failureResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the failureResponse using partial update
        FailureResponse partialUpdatedFailureResponse = new FailureResponse();
        partialUpdatedFailureResponse.setId(failureResponse.getId());

        partialUpdatedFailureResponse.version(UPDATED_VERSION);

        restFailureResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFailureResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFailureResponse))
            )
            .andExpect(status().isOk());

        // Validate the FailureResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFailureResponseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFailureResponse, failureResponse),
            getPersistedFailureResponse(failureResponse)
        );
    }

    @Test
    @Transactional
    void fullUpdateFailureResponseWithPatch() throws Exception {
        // Initialize the database
        insertedFailureResponse = failureResponseRepository.saveAndFlush(failureResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the failureResponse using partial update
        FailureResponse partialUpdatedFailureResponse = new FailureResponse();
        partialUpdatedFailureResponse.setId(failureResponse.getId());

        partialUpdatedFailureResponse.partnerReference(UPDATED_PARTNER_REFERENCE).date(UPDATED_DATE).version(UPDATED_VERSION);

        restFailureResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFailureResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFailureResponse))
            )
            .andExpect(status().isOk());

        // Validate the FailureResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFailureResponseUpdatableFieldsEquals(
            partialUpdatedFailureResponse,
            getPersistedFailureResponse(partialUpdatedFailureResponse)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFailureResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        failureResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFailureResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, failureResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(failureResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFailureResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        failureResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFailureResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(failureResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFailureResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        failureResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFailureResponseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(failureResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FailureResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFailureResponse() throws Exception {
        // Initialize the database
        insertedFailureResponse = failureResponseRepository.saveAndFlush(failureResponse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the failureResponse
        restFailureResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, failureResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return failureResponseRepository.count();
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

    protected FailureResponse getPersistedFailureResponse(FailureResponse failureResponse) {
        return failureResponseRepository.findById(failureResponse.getId()).orElseThrow();
    }

    protected void assertPersistedFailureResponseToMatchAllProperties(FailureResponse expectedFailureResponse) {
        assertFailureResponseAllPropertiesEquals(expectedFailureResponse, getPersistedFailureResponse(expectedFailureResponse));
    }

    protected void assertPersistedFailureResponseToMatchUpdatableProperties(FailureResponse expectedFailureResponse) {
        assertFailureResponseAllUpdatablePropertiesEquals(expectedFailureResponse, getPersistedFailureResponse(expectedFailureResponse));
    }
}
