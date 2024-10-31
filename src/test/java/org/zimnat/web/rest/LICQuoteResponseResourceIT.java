package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICQuoteResponseAsserts.*;
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
import org.zimnat.domain.LICQuoteResponse;
import org.zimnat.repository.LICQuoteResponseRepository;

/**
 * Integration tests for the {@link LICQuoteResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICQuoteResponseResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THE_VERSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lic-quote-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICQuoteResponseRepository lICQuoteResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICQuoteResponseMockMvc;

    private LICQuoteResponse lICQuoteResponse;

    private LICQuoteResponse insertedLICQuoteResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuoteResponse createEntity(EntityManager em) {
        LICQuoteResponse lICQuoteResponse = new LICQuoteResponse()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .theVersion(DEFAULT_THE_VERSION);
        return lICQuoteResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuoteResponse createUpdatedEntity(EntityManager em) {
        LICQuoteResponse lICQuoteResponse = new LICQuoteResponse()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION);
        return lICQuoteResponse;
    }

    @BeforeEach
    public void initTest() {
        lICQuoteResponse = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICQuoteResponse != null) {
            lICQuoteResponseRepository.delete(insertedLICQuoteResponse);
            insertedLICQuoteResponse = null;
        }
    }

    @Test
    @Transactional
    void createLICQuoteResponse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICQuoteResponse
        var returnedLICQuoteResponse = om.readValue(
            restLICQuoteResponseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteResponse)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICQuoteResponse.class
        );

        // Validate the LICQuoteResponse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICQuoteResponseUpdatableFieldsEquals(returnedLICQuoteResponse, getPersistedLICQuoteResponse(returnedLICQuoteResponse));

        insertedLICQuoteResponse = returnedLICQuoteResponse;
    }

    @Test
    @Transactional
    void createLICQuoteResponseWithExistingId() throws Exception {
        // Create the LICQuoteResponse with an existing ID
        lICQuoteResponse.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICQuoteResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteResponse)))
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLICQuoteResponses() throws Exception {
        // Initialize the database
        insertedLICQuoteResponse = lICQuoteResponseRepository.saveAndFlush(lICQuoteResponse);

        // Get all the lICQuoteResponseList
        restLICQuoteResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICQuoteResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].theVersion").value(hasItem(DEFAULT_THE_VERSION)));
    }

    @Test
    @Transactional
    void getLICQuoteResponse() throws Exception {
        // Initialize the database
        insertedLICQuoteResponse = lICQuoteResponseRepository.saveAndFlush(lICQuoteResponse);

        // Get the lICQuoteResponse
        restLICQuoteResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, lICQuoteResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICQuoteResponse.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.theVersion").value(DEFAULT_THE_VERSION));
    }

    @Test
    @Transactional
    void getNonExistingLICQuoteResponse() throws Exception {
        // Get the lICQuoteResponse
        restLICQuoteResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICQuoteResponse() throws Exception {
        // Initialize the database
        insertedLICQuoteResponse = lICQuoteResponseRepository.saveAndFlush(lICQuoteResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteResponse
        LICQuoteResponse updatedLICQuoteResponse = lICQuoteResponseRepository.findById(lICQuoteResponse.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLICQuoteResponse are not directly saved in db
        em.detach(updatedLICQuoteResponse);
        updatedLICQuoteResponse.partnerReference(UPDATED_PARTNER_REFERENCE).theDate(UPDATED_THE_DATE).theVersion(UPDATED_THE_VERSION);

        restLICQuoteResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICQuoteResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICQuoteResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICQuoteResponseToMatchAllProperties(updatedLICQuoteResponse);
    }

    @Test
    @Transactional
    void putNonExistingLICQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICQuoteResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICQuoteResponseWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuoteResponse = lICQuoteResponseRepository.saveAndFlush(lICQuoteResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteResponse using partial update
        LICQuoteResponse partialUpdatedLICQuoteResponse = new LICQuoteResponse();
        partialUpdatedLICQuoteResponse.setId(lICQuoteResponse.getId());

        partialUpdatedLICQuoteResponse.theVersion(UPDATED_THE_VERSION);

        restLICQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuoteResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuoteResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteResponseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLICQuoteResponse, lICQuoteResponse),
            getPersistedLICQuoteResponse(lICQuoteResponse)
        );
    }

    @Test
    @Transactional
    void fullUpdateLICQuoteResponseWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuoteResponse = lICQuoteResponseRepository.saveAndFlush(lICQuoteResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteResponse using partial update
        LICQuoteResponse partialUpdatedLICQuoteResponse = new LICQuoteResponse();
        partialUpdatedLICQuoteResponse.setId(lICQuoteResponse.getId());

        partialUpdatedLICQuoteResponse
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION);

        restLICQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuoteResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuoteResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteResponseUpdatableFieldsEquals(
            partialUpdatedLICQuoteResponse,
            getPersistedLICQuoteResponse(partialUpdatedLICQuoteResponse)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLICQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICQuoteResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuoteResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICQuoteResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteResponseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICQuoteResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuoteResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICQuoteResponse() throws Exception {
        // Initialize the database
        insertedLICQuoteResponse = lICQuoteResponseRepository.saveAndFlush(lICQuoteResponse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICQuoteResponse
        restLICQuoteResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICQuoteResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICQuoteResponseRepository.count();
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

    protected LICQuoteResponse getPersistedLICQuoteResponse(LICQuoteResponse lICQuoteResponse) {
        return lICQuoteResponseRepository.findById(lICQuoteResponse.getId()).orElseThrow();
    }

    protected void assertPersistedLICQuoteResponseToMatchAllProperties(LICQuoteResponse expectedLICQuoteResponse) {
        assertLICQuoteResponseAllPropertiesEquals(expectedLICQuoteResponse, getPersistedLICQuoteResponse(expectedLICQuoteResponse));
    }

    protected void assertPersistedLICQuoteResponseToMatchUpdatableProperties(LICQuoteResponse expectedLICQuoteResponse) {
        assertLICQuoteResponseAllUpdatablePropertiesEquals(
            expectedLICQuoteResponse,
            getPersistedLICQuoteResponse(expectedLICQuoteResponse)
        );
    }
}
