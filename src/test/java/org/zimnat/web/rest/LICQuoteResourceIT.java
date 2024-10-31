package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICQuoteAsserts.*;
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
import org.zimnat.domain.LICQuote;
import org.zimnat.repository.LICQuoteRepository;

/**
 * Integration tests for the {@link LICQuoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICQuoteResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TOKEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lic-quotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICQuoteRepository lICQuoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICQuoteMockMvc;

    private LICQuote lICQuote;

    private LICQuote insertedLICQuote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuote createEntity(EntityManager em) {
        LICQuote lICQuote = new LICQuote()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .theVersion(DEFAULT_THE_VERSION)
            .partnerToken(DEFAULT_PARTNER_TOKEN);
        return lICQuote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuote createUpdatedEntity(EntityManager em) {
        LICQuote lICQuote = new LICQuote()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);
        return lICQuote;
    }

    @BeforeEach
    public void initTest() {
        lICQuote = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICQuote != null) {
            lICQuoteRepository.delete(insertedLICQuote);
            insertedLICQuote = null;
        }
    }

    @Test
    @Transactional
    void createLICQuote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICQuote
        var returnedLICQuote = om.readValue(
            restLICQuoteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICQuote.class
        );

        // Validate the LICQuote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICQuoteUpdatableFieldsEquals(returnedLICQuote, getPersistedLICQuote(returnedLICQuote));

        insertedLICQuote = returnedLICQuote;
    }

    @Test
    @Transactional
    void createLICQuoteWithExistingId() throws Exception {
        // Create the LICQuote with an existing ID
        lICQuote.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isBadRequest());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartnerReferenceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuote.setPartnerReference(null);

        // Create the LICQuote, which fails.

        restLICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuote.setTheDate(null);

        // Create the LICQuote, which fails.

        restLICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuote.setTheVersion(null);

        // Create the LICQuote, which fails.

        restLICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartnerTokenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuote.setPartnerToken(null);

        // Create the LICQuote, which fails.

        restLICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLICQuotes() throws Exception {
        // Initialize the database
        insertedLICQuote = lICQuoteRepository.saveAndFlush(lICQuote);

        // Get all the lICQuoteList
        restLICQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].theVersion").value(hasItem(DEFAULT_THE_VERSION)))
            .andExpect(jsonPath("$.[*].partnerToken").value(hasItem(DEFAULT_PARTNER_TOKEN)));
    }

    @Test
    @Transactional
    void getLICQuote() throws Exception {
        // Initialize the database
        insertedLICQuote = lICQuoteRepository.saveAndFlush(lICQuote);

        // Get the lICQuote
        restLICQuoteMockMvc
            .perform(get(ENTITY_API_URL_ID, lICQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICQuote.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.theVersion").value(DEFAULT_THE_VERSION))
            .andExpect(jsonPath("$.partnerToken").value(DEFAULT_PARTNER_TOKEN));
    }

    @Test
    @Transactional
    void getNonExistingLICQuote() throws Exception {
        // Get the lICQuote
        restLICQuoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICQuote() throws Exception {
        // Initialize the database
        insertedLICQuote = lICQuoteRepository.saveAndFlush(lICQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuote
        LICQuote updatedLICQuote = lICQuoteRepository.findById(lICQuote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLICQuote are not directly saved in db
        em.detach(updatedLICQuote);
        updatedLICQuote
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);

        restLICQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICQuote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICQuote))
            )
            .andExpect(status().isOk());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICQuoteToMatchAllProperties(updatedLICQuote);
    }

    @Test
    @Transactional
    void putNonExistingLICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuote.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICQuote.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuote = lICQuoteRepository.saveAndFlush(lICQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuote using partial update
        LICQuote partialUpdatedLICQuote = new LICQuote();
        partialUpdatedLICQuote.setId(lICQuote.getId());

        partialUpdatedLICQuote
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);

        restLICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuote))
            )
            .andExpect(status().isOk());

        // Validate the LICQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLICQuote, lICQuote), getPersistedLICQuote(lICQuote));
    }

    @Test
    @Transactional
    void fullUpdateLICQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuote = lICQuoteRepository.saveAndFlush(lICQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuote using partial update
        LICQuote partialUpdatedLICQuote = new LICQuote();
        partialUpdatedLICQuote.setId(lICQuote.getId());

        partialUpdatedLICQuote
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);

        restLICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuote))
            )
            .andExpect(status().isOk());

        // Validate the LICQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteUpdatableFieldsEquals(partialUpdatedLICQuote, getPersistedLICQuote(partialUpdatedLICQuote));
    }

    @Test
    @Transactional
    void patchNonExistingLICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuote.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICQuote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICQuote() throws Exception {
        // Initialize the database
        insertedLICQuote = lICQuoteRepository.saveAndFlush(lICQuote);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICQuote
        restLICQuoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICQuote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICQuoteRepository.count();
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

    protected LICQuote getPersistedLICQuote(LICQuote lICQuote) {
        return lICQuoteRepository.findById(lICQuote.getId()).orElseThrow();
    }

    protected void assertPersistedLICQuoteToMatchAllProperties(LICQuote expectedLICQuote) {
        assertLICQuoteAllPropertiesEquals(expectedLICQuote, getPersistedLICQuote(expectedLICQuote));
    }

    protected void assertPersistedLICQuoteToMatchUpdatableProperties(LICQuote expectedLICQuote) {
        assertLICQuoteAllUpdatablePropertiesEquals(expectedLICQuote, getPersistedLICQuote(expectedLICQuote));
    }
}
