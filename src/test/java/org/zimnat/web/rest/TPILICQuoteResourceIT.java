package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.TPILICQuoteAsserts.*;
import static org.zimnat.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.UUID;
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
import org.zimnat.domain.TPILICQuote;
import org.zimnat.repository.TPILICQuoteRepository;

/**
 * Integration tests for the {@link TPILICQuoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TPILICQuoteResourceIT {

    private static final UUID DEFAULT_PARTNER_REFERENCE = UUID.randomUUID();
    private static final UUID UPDATED_PARTNER_REFERENCE = UUID.randomUUID();

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tpilic-quotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TPILICQuoteRepository tPILICQuoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTPILICQuoteMockMvc;

    private TPILICQuote tPILICQuote;

    private TPILICQuote insertedTPILICQuote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPILICQuote createEntity(EntityManager em) {
        TPILICQuote tPILICQuote = new TPILICQuote()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .theVersion(DEFAULT_THE_VERSION)
            .partnerToken(DEFAULT_PARTNER_TOKEN)
            .function(DEFAULT_FUNCTION);
        return tPILICQuote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TPILICQuote createUpdatedEntity(EntityManager em) {
        TPILICQuote tPILICQuote = new TPILICQuote()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .function(UPDATED_FUNCTION);
        return tPILICQuote;
    }

    @BeforeEach
    public void initTest() {
        tPILICQuote = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTPILICQuote != null) {
            tPILICQuoteRepository.delete(insertedTPILICQuote);
            insertedTPILICQuote = null;
        }
    }

    @Test
    @Transactional
    void createTPILICQuote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TPILICQuote
        var returnedTPILICQuote = om.readValue(
            restTPILICQuoteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TPILICQuote.class
        );

        // Validate the TPILICQuote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTPILICQuoteUpdatableFieldsEquals(returnedTPILICQuote, getPersistedTPILICQuote(returnedTPILICQuote));

        insertedTPILICQuote = returnedTPILICQuote;
    }

    @Test
    @Transactional
    void createTPILICQuoteWithExistingId() throws Exception {
        // Create the TPILICQuote with an existing ID
        tPILICQuote.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPILICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isBadRequest());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartnerReferenceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tPILICQuote.setPartnerReference(null);

        // Create the TPILICQuote, which fails.

        restTPILICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tPILICQuote.setTheDate(null);

        // Create the TPILICQuote, which fails.

        restTPILICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tPILICQuote.setTheVersion(null);

        // Create the TPILICQuote, which fails.

        restTPILICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartnerTokenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tPILICQuote.setPartnerToken(null);

        // Create the TPILICQuote, which fails.

        restTPILICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tPILICQuote.setFunction(null);

        // Create the TPILICQuote, which fails.

        restTPILICQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTPILICQuotes() throws Exception {
        // Initialize the database
        insertedTPILICQuote = tPILICQuoteRepository.saveAndFlush(tPILICQuote);

        // Get all the tPILICQuoteList
        restTPILICQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPILICQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].theVersion").value(hasItem(DEFAULT_THE_VERSION)))
            .andExpect(jsonPath("$.[*].partnerToken").value(hasItem(DEFAULT_PARTNER_TOKEN)))
            .andExpect(jsonPath("$.[*].function").value(hasItem(DEFAULT_FUNCTION)));
    }

    @Test
    @Transactional
    void getTPILICQuote() throws Exception {
        // Initialize the database
        insertedTPILICQuote = tPILICQuoteRepository.saveAndFlush(tPILICQuote);

        // Get the tPILICQuote
        restTPILICQuoteMockMvc
            .perform(get(ENTITY_API_URL_ID, tPILICQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tPILICQuote.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE.toString()))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.theVersion").value(DEFAULT_THE_VERSION))
            .andExpect(jsonPath("$.partnerToken").value(DEFAULT_PARTNER_TOKEN))
            .andExpect(jsonPath("$.function").value(DEFAULT_FUNCTION));
    }

    @Test
    @Transactional
    void getNonExistingTPILICQuote() throws Exception {
        // Get the tPILICQuote
        restTPILICQuoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTPILICQuote() throws Exception {
        // Initialize the database
        insertedTPILICQuote = tPILICQuoteRepository.saveAndFlush(tPILICQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tPILICQuote
        TPILICQuote updatedTPILICQuote = tPILICQuoteRepository.findById(tPILICQuote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTPILICQuote are not directly saved in db
        em.detach(updatedTPILICQuote);
        updatedTPILICQuote
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .function(UPDATED_FUNCTION);

        restTPILICQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTPILICQuote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTPILICQuote))
            )
            .andExpect(status().isOk());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTPILICQuoteToMatchAllProperties(updatedTPILICQuote);
    }

    @Test
    @Transactional
    void putNonExistingTPILICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tPILICQuote.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPILICQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tPILICQuote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tPILICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTPILICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tPILICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTPILICQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tPILICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTPILICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tPILICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTPILICQuoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTPILICQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedTPILICQuote = tPILICQuoteRepository.saveAndFlush(tPILICQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tPILICQuote using partial update
        TPILICQuote partialUpdatedTPILICQuote = new TPILICQuote();
        partialUpdatedTPILICQuote.setId(tPILICQuote.getId());

        partialUpdatedTPILICQuote
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN);

        restTPILICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTPILICQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTPILICQuote))
            )
            .andExpect(status().isOk());

        // Validate the TPILICQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTPILICQuoteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTPILICQuote, tPILICQuote),
            getPersistedTPILICQuote(tPILICQuote)
        );
    }

    @Test
    @Transactional
    void fullUpdateTPILICQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedTPILICQuote = tPILICQuoteRepository.saveAndFlush(tPILICQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tPILICQuote using partial update
        TPILICQuote partialUpdatedTPILICQuote = new TPILICQuote();
        partialUpdatedTPILICQuote.setId(tPILICQuote.getId());

        partialUpdatedTPILICQuote
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .function(UPDATED_FUNCTION);

        restTPILICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTPILICQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTPILICQuote))
            )
            .andExpect(status().isOk());

        // Validate the TPILICQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTPILICQuoteUpdatableFieldsEquals(partialUpdatedTPILICQuote, getPersistedTPILICQuote(partialUpdatedTPILICQuote));
    }

    @Test
    @Transactional
    void patchNonExistingTPILICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tPILICQuote.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPILICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tPILICQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tPILICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTPILICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tPILICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTPILICQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tPILICQuote))
            )
            .andExpect(status().isBadRequest());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTPILICQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tPILICQuote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTPILICQuoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tPILICQuote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TPILICQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTPILICQuote() throws Exception {
        // Initialize the database
        insertedTPILICQuote = tPILICQuoteRepository.saveAndFlush(tPILICQuote);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tPILICQuote
        restTPILICQuoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tPILICQuote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tPILICQuoteRepository.count();
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

    protected TPILICQuote getPersistedTPILICQuote(TPILICQuote tPILICQuote) {
        return tPILICQuoteRepository.findById(tPILICQuote.getId()).orElseThrow();
    }

    protected void assertPersistedTPILICQuoteToMatchAllProperties(TPILICQuote expectedTPILICQuote) {
        assertTPILICQuoteAllPropertiesEquals(expectedTPILICQuote, getPersistedTPILICQuote(expectedTPILICQuote));
    }

    protected void assertPersistedTPILICQuoteToMatchUpdatableProperties(TPILICQuote expectedTPILICQuote) {
        assertTPILICQuoteAllUpdatablePropertiesEquals(expectedTPILICQuote, getPersistedTPILICQuote(expectedTPILICQuote));
    }
}
