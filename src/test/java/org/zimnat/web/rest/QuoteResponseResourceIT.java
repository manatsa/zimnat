package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.QuoteResponseAsserts.*;
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
import org.zimnat.domain.QuoteResponse;
import org.zimnat.repository.QuoteResponseRepository;

/**
 * Integration tests for the {@link QuoteResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuoteResponseResourceIT {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quote-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QuoteResponseRepository quoteResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuoteResponseMockMvc;

    private QuoteResponse quoteResponse;

    private QuoteResponse insertedQuoteResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuoteResponse createEntity(EntityManager em) {
        QuoteResponse quoteResponse = new QuoteResponse().result(DEFAULT_RESULT).message(DEFAULT_MESSAGE);
        return quoteResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuoteResponse createUpdatedEntity(EntityManager em) {
        QuoteResponse quoteResponse = new QuoteResponse().result(UPDATED_RESULT).message(UPDATED_MESSAGE);
        return quoteResponse;
    }

    @BeforeEach
    public void initTest() {
        quoteResponse = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedQuoteResponse != null) {
            quoteResponseRepository.delete(insertedQuoteResponse);
            insertedQuoteResponse = null;
        }
    }

    @Test
    @Transactional
    void createQuoteResponse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the QuoteResponse
        var returnedQuoteResponse = om.readValue(
            restQuoteResponseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quoteResponse)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            QuoteResponse.class
        );

        // Validate the QuoteResponse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertQuoteResponseUpdatableFieldsEquals(returnedQuoteResponse, getPersistedQuoteResponse(returnedQuoteResponse));

        insertedQuoteResponse = returnedQuoteResponse;
    }

    @Test
    @Transactional
    void createQuoteResponseWithExistingId() throws Exception {
        // Create the QuoteResponse with an existing ID
        quoteResponse.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quoteResponse)))
            .andExpect(status().isBadRequest());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuoteResponses() throws Exception {
        // Initialize the database
        insertedQuoteResponse = quoteResponseRepository.saveAndFlush(quoteResponse);

        // Get all the quoteResponseList
        restQuoteResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quoteResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getQuoteResponse() throws Exception {
        // Initialize the database
        insertedQuoteResponse = quoteResponseRepository.saveAndFlush(quoteResponse);

        // Get the quoteResponse
        restQuoteResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, quoteResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quoteResponse.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingQuoteResponse() throws Exception {
        // Get the quoteResponse
        restQuoteResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuoteResponse() throws Exception {
        // Initialize the database
        insertedQuoteResponse = quoteResponseRepository.saveAndFlush(quoteResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quoteResponse
        QuoteResponse updatedQuoteResponse = quoteResponseRepository.findById(quoteResponse.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQuoteResponse are not directly saved in db
        em.detach(updatedQuoteResponse);
        updatedQuoteResponse.result(UPDATED_RESULT).message(UPDATED_MESSAGE);

        restQuoteResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuoteResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedQuoteResponse))
            )
            .andExpect(status().isOk());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQuoteResponseToMatchAllProperties(updatedQuoteResponse);
    }

    @Test
    @Transactional
    void putNonExistingQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quoteResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quoteResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(quoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(quoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quoteResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuoteResponseWithPatch() throws Exception {
        // Initialize the database
        insertedQuoteResponse = quoteResponseRepository.saveAndFlush(quoteResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quoteResponse using partial update
        QuoteResponse partialUpdatedQuoteResponse = new QuoteResponse();
        partialUpdatedQuoteResponse.setId(quoteResponse.getId());

        partialUpdatedQuoteResponse.result(UPDATED_RESULT).message(UPDATED_MESSAGE);

        restQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuoteResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuoteResponse))
            )
            .andExpect(status().isOk());

        // Validate the QuoteResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuoteResponseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedQuoteResponse, quoteResponse),
            getPersistedQuoteResponse(quoteResponse)
        );
    }

    @Test
    @Transactional
    void fullUpdateQuoteResponseWithPatch() throws Exception {
        // Initialize the database
        insertedQuoteResponse = quoteResponseRepository.saveAndFlush(quoteResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quoteResponse using partial update
        QuoteResponse partialUpdatedQuoteResponse = new QuoteResponse();
        partialUpdatedQuoteResponse.setId(quoteResponse.getId());

        partialUpdatedQuoteResponse.result(UPDATED_RESULT).message(UPDATED_MESSAGE);

        restQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuoteResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuoteResponse))
            )
            .andExpect(status().isOk());

        // Validate the QuoteResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuoteResponseUpdatableFieldsEquals(partialUpdatedQuoteResponse, getPersistedQuoteResponse(partialUpdatedQuoteResponse));
    }

    @Test
    @Transactional
    void patchNonExistingQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quoteResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quoteResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(quoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(quoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteResponseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(quoteResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuoteResponse() throws Exception {
        // Initialize the database
        insertedQuoteResponse = quoteResponseRepository.saveAndFlush(quoteResponse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the quoteResponse
        restQuoteResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, quoteResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return quoteResponseRepository.count();
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

    protected QuoteResponse getPersistedQuoteResponse(QuoteResponse quoteResponse) {
        return quoteResponseRepository.findById(quoteResponse.getId()).orElseThrow();
    }

    protected void assertPersistedQuoteResponseToMatchAllProperties(QuoteResponse expectedQuoteResponse) {
        assertQuoteResponseAllPropertiesEquals(expectedQuoteResponse, getPersistedQuoteResponse(expectedQuoteResponse));
    }

    protected void assertPersistedQuoteResponseToMatchUpdatableProperties(QuoteResponse expectedQuoteResponse) {
        assertQuoteResponseAllUpdatablePropertiesEquals(expectedQuoteResponse, getPersistedQuoteResponse(expectedQuoteResponse));
    }
}
