package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICResultResponseAsserts.*;
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
import org.zimnat.domain.LICResultResponse;
import org.zimnat.repository.LICResultResponseRepository;

/**
 * Integration tests for the {@link LICResultResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICResultResponseResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lic-result-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICResultResponseRepository lICResultResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICResultResponseMockMvc;

    private LICResultResponse lICResultResponse;

    private LICResultResponse insertedLICResultResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICResultResponse createEntity(EntityManager em) {
        LICResultResponse lICResultResponse = new LICResultResponse()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .version(DEFAULT_VERSION);
        return lICResultResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICResultResponse createUpdatedEntity(EntityManager em) {
        LICResultResponse lICResultResponse = new LICResultResponse()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .version(UPDATED_VERSION);
        return lICResultResponse;
    }

    @BeforeEach
    public void initTest() {
        lICResultResponse = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICResultResponse != null) {
            lICResultResponseRepository.delete(insertedLICResultResponse);
            insertedLICResultResponse = null;
        }
    }

    @Test
    @Transactional
    void createLICResultResponse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICResultResponse
        var returnedLICResultResponse = om.readValue(
            restLICResultResponseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResultResponse)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICResultResponse.class
        );

        // Validate the LICResultResponse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICResultResponseUpdatableFieldsEquals(returnedLICResultResponse, getPersistedLICResultResponse(returnedLICResultResponse));

        insertedLICResultResponse = returnedLICResultResponse;
    }

    @Test
    @Transactional
    void createLICResultResponseWithExistingId() throws Exception {
        // Create the LICResultResponse with an existing ID
        lICResultResponse.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICResultResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResultResponse)))
            .andExpect(status().isBadRequest());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLICResultResponses() throws Exception {
        // Initialize the database
        insertedLICResultResponse = lICResultResponseRepository.saveAndFlush(lICResultResponse);

        // Get all the lICResultResponseList
        restLICResultResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICResultResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getLICResultResponse() throws Exception {
        // Initialize the database
        insertedLICResultResponse = lICResultResponseRepository.saveAndFlush(lICResultResponse);

        // Get the lICResultResponse
        restLICResultResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, lICResultResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICResultResponse.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNonExistingLICResultResponse() throws Exception {
        // Get the lICResultResponse
        restLICResultResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICResultResponse() throws Exception {
        // Initialize the database
        insertedLICResultResponse = lICResultResponseRepository.saveAndFlush(lICResultResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICResultResponse
        LICResultResponse updatedLICResultResponse = lICResultResponseRepository.findById(lICResultResponse.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLICResultResponse are not directly saved in db
        em.detach(updatedLICResultResponse);
        updatedLICResultResponse.partnerReference(UPDATED_PARTNER_REFERENCE).theDate(UPDATED_THE_DATE).version(UPDATED_VERSION);

        restLICResultResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICResultResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICResultResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICResultResponseToMatchAllProperties(updatedLICResultResponse);
    }

    @Test
    @Transactional
    void putNonExistingLICResultResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResultResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICResultResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICResultResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICResultResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICResultResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResultResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICResultResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICResultResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResultResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICResultResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICResultResponseWithPatch() throws Exception {
        // Initialize the database
        insertedLICResultResponse = lICResultResponseRepository.saveAndFlush(lICResultResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICResultResponse using partial update
        LICResultResponse partialUpdatedLICResultResponse = new LICResultResponse();
        partialUpdatedLICResultResponse.setId(lICResultResponse.getId());

        partialUpdatedLICResultResponse.theDate(UPDATED_THE_DATE);

        restLICResultResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICResultResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICResultResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICResultResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICResultResponseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLICResultResponse, lICResultResponse),
            getPersistedLICResultResponse(lICResultResponse)
        );
    }

    @Test
    @Transactional
    void fullUpdateLICResultResponseWithPatch() throws Exception {
        // Initialize the database
        insertedLICResultResponse = lICResultResponseRepository.saveAndFlush(lICResultResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICResultResponse using partial update
        LICResultResponse partialUpdatedLICResultResponse = new LICResultResponse();
        partialUpdatedLICResultResponse.setId(lICResultResponse.getId());

        partialUpdatedLICResultResponse.partnerReference(UPDATED_PARTNER_REFERENCE).theDate(UPDATED_THE_DATE).version(UPDATED_VERSION);

        restLICResultResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICResultResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICResultResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICResultResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICResultResponseUpdatableFieldsEquals(
            partialUpdatedLICResultResponse,
            getPersistedLICResultResponse(partialUpdatedLICResultResponse)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLICResultResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResultResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICResultResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICResultResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICResultResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICResultResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResultResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICResultResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICResultResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICResultResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICResultResponseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICResultResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICResultResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICResultResponse() throws Exception {
        // Initialize the database
        insertedLICResultResponse = lICResultResponseRepository.saveAndFlush(lICResultResponse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICResultResponse
        restLICResultResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICResultResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICResultResponseRepository.count();
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

    protected LICResultResponse getPersistedLICResultResponse(LICResultResponse lICResultResponse) {
        return lICResultResponseRepository.findById(lICResultResponse.getId()).orElseThrow();
    }

    protected void assertPersistedLICResultResponseToMatchAllProperties(LICResultResponse expectedLICResultResponse) {
        assertLICResultResponseAllPropertiesEquals(expectedLICResultResponse, getPersistedLICResultResponse(expectedLICResultResponse));
    }

    protected void assertPersistedLICResultResponseToMatchUpdatableProperties(LICResultResponse expectedLICResultResponse) {
        assertLICResultResponseAllUpdatablePropertiesEquals(
            expectedLICResultResponse,
            getPersistedLICResultResponse(expectedLICResultResponse)
        );
    }
}
