package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICQuoteRequestAsserts.*;
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
import org.zimnat.domain.LICQuoteRequest;
import org.zimnat.repository.LICQuoteRequestRepository;

/**
 * Integration tests for the {@link LICQuoteRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICQuoteRequestResourceIT {

    private static final String DEFAULT_THE_FUNCTION = "AAAAAAAAAA";
    private static final String UPDATED_THE_FUNCTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lic-quote-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICQuoteRequestRepository lICQuoteRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICQuoteRequestMockMvc;

    private LICQuoteRequest lICQuoteRequest;

    private LICQuoteRequest insertedLICQuoteRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuoteRequest createEntity(EntityManager em) {
        LICQuoteRequest lICQuoteRequest = new LICQuoteRequest().theFunction(DEFAULT_THE_FUNCTION);
        return lICQuoteRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuoteRequest createUpdatedEntity(EntityManager em) {
        LICQuoteRequest lICQuoteRequest = new LICQuoteRequest().theFunction(UPDATED_THE_FUNCTION);
        return lICQuoteRequest;
    }

    @BeforeEach
    public void initTest() {
        lICQuoteRequest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICQuoteRequest != null) {
            lICQuoteRequestRepository.delete(insertedLICQuoteRequest);
            insertedLICQuoteRequest = null;
        }
    }

    @Test
    @Transactional
    void createLICQuoteRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICQuoteRequest
        var returnedLICQuoteRequest = om.readValue(
            restLICQuoteRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICQuoteRequest.class
        );

        // Validate the LICQuoteRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICQuoteRequestUpdatableFieldsEquals(returnedLICQuoteRequest, getPersistedLICQuoteRequest(returnedLICQuoteRequest));

        insertedLICQuoteRequest = returnedLICQuoteRequest;
    }

    @Test
    @Transactional
    void createLICQuoteRequestWithExistingId() throws Exception {
        // Create the LICQuoteRequest with an existing ID
        lICQuoteRequest.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICQuoteRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteRequest)))
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheFunctionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteRequest.setTheFunction(null);

        // Create the LICQuoteRequest, which fails.

        restLICQuoteRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteRequest)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLICQuoteRequests() throws Exception {
        // Initialize the database
        insertedLICQuoteRequest = lICQuoteRequestRepository.saveAndFlush(lICQuoteRequest);

        // Get all the lICQuoteRequestList
        restLICQuoteRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICQuoteRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].theFunction").value(hasItem(DEFAULT_THE_FUNCTION)));
    }

    @Test
    @Transactional
    void getLICQuoteRequest() throws Exception {
        // Initialize the database
        insertedLICQuoteRequest = lICQuoteRequestRepository.saveAndFlush(lICQuoteRequest);

        // Get the lICQuoteRequest
        restLICQuoteRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, lICQuoteRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICQuoteRequest.getId().intValue()))
            .andExpect(jsonPath("$.theFunction").value(DEFAULT_THE_FUNCTION));
    }

    @Test
    @Transactional
    void getNonExistingLICQuoteRequest() throws Exception {
        // Get the lICQuoteRequest
        restLICQuoteRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICQuoteRequest() throws Exception {
        // Initialize the database
        insertedLICQuoteRequest = lICQuoteRequestRepository.saveAndFlush(lICQuoteRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteRequest
        LICQuoteRequest updatedLICQuoteRequest = lICQuoteRequestRepository.findById(lICQuoteRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLICQuoteRequest are not directly saved in db
        em.detach(updatedLICQuoteRequest);
        updatedLICQuoteRequest.theFunction(UPDATED_THE_FUNCTION);

        restLICQuoteRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICQuoteRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICQuoteRequest))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICQuoteRequestToMatchAllProperties(updatedLICQuoteRequest);
    }

    @Test
    @Transactional
    void putNonExistingLICQuoteRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICQuoteRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuoteRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICQuoteRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuoteRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICQuoteRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICQuoteRequestWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuoteRequest = lICQuoteRequestRepository.saveAndFlush(lICQuoteRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteRequest using partial update
        LICQuoteRequest partialUpdatedLICQuoteRequest = new LICQuoteRequest();
        partialUpdatedLICQuoteRequest.setId(lICQuoteRequest.getId());

        partialUpdatedLICQuoteRequest.theFunction(UPDATED_THE_FUNCTION);

        restLICQuoteRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuoteRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuoteRequest))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLICQuoteRequest, lICQuoteRequest),
            getPersistedLICQuoteRequest(lICQuoteRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateLICQuoteRequestWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuoteRequest = lICQuoteRequestRepository.saveAndFlush(lICQuoteRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteRequest using partial update
        LICQuoteRequest partialUpdatedLICQuoteRequest = new LICQuoteRequest();
        partialUpdatedLICQuoteRequest.setId(lICQuoteRequest.getId());

        partialUpdatedLICQuoteRequest.theFunction(UPDATED_THE_FUNCTION);

        restLICQuoteRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuoteRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuoteRequest))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteRequestUpdatableFieldsEquals(
            partialUpdatedLICQuoteRequest,
            getPersistedLICQuoteRequest(partialUpdatedLICQuoteRequest)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLICQuoteRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICQuoteRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuoteRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICQuoteRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuoteRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICQuoteRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICQuoteRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuoteRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICQuoteRequest() throws Exception {
        // Initialize the database
        insertedLICQuoteRequest = lICQuoteRequestRepository.saveAndFlush(lICQuoteRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICQuoteRequest
        restLICQuoteRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICQuoteRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICQuoteRequestRepository.count();
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

    protected LICQuoteRequest getPersistedLICQuoteRequest(LICQuoteRequest lICQuoteRequest) {
        return lICQuoteRequestRepository.findById(lICQuoteRequest.getId()).orElseThrow();
    }

    protected void assertPersistedLICQuoteRequestToMatchAllProperties(LICQuoteRequest expectedLICQuoteRequest) {
        assertLICQuoteRequestAllPropertiesEquals(expectedLICQuoteRequest, getPersistedLICQuoteRequest(expectedLICQuoteRequest));
    }

    protected void assertPersistedLICQuoteRequestToMatchUpdatableProperties(LICQuoteRequest expectedLICQuoteRequest) {
        assertLICQuoteRequestAllUpdatablePropertiesEquals(expectedLICQuoteRequest, getPersistedLICQuoteRequest(expectedLICQuoteRequest));
    }
}
