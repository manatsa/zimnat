package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.QuotesAsserts.*;
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
import org.zimnat.domain.Quotes;
import org.zimnat.repository.QuotesRepository;

/**
 * Integration tests for the {@link QuotesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotesResourceIT {

    private static final String DEFAULT_V_RN = "AAAAAAAAAA";
    private static final String UPDATED_V_RN = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_RESULT = 1;
    private static final Integer UPDATED_RESULT = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_I_D_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SUBURB_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUBURB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LIC_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_LIC_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_RADIO_TV_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_RADIO_TV_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_RADIO_TV_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_RADIO_TV_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_NETT_MASS = "AAAAAAAAAA";
    private static final String UPDATED_NETT_MASS = "BBBBBBBBBB";

    private static final String DEFAULT_LIC_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LIC_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_AMT = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_ARREARS_AMT = "AAAAAAAAAA";
    private static final String UPDATED_ARREARS_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_PENALTIES_AMT = "AAAAAAAAAA";
    private static final String UPDATED_PENALTIES_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_ADMINISTRATION_AMT = "AAAAAAAAAA";
    private static final String UPDATED_ADMINISTRATION_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_LIC_AMT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_LIC_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_RADIO_TV_AMT = "AAAAAAAAAA";
    private static final String UPDATED_RADIO_TV_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_RADIO_TV_ARREARS_AMT = "AAAAAAAAAA";
    private static final String UPDATED_RADIO_TV_ARREARS_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_RADIO_TV_AMT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_RADIO_TV_AMT = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AMOUNT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QuotesRepository quotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotesMockMvc;

    private Quotes quotes;

    private Quotes insertedQuotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotes createEntity(EntityManager em) {
        Quotes quotes = new Quotes()
            .vRN(DEFAULT_V_RN)
            .licenceID(DEFAULT_LICENCE_ID)
            .result(DEFAULT_RESULT)
            .message(DEFAULT_MESSAGE)
            .iDNumber(DEFAULT_I_D_NUMBER)
            .clientIDType(DEFAULT_CLIENT_ID_TYPE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .suburbID(DEFAULT_SUBURB_ID)
            .licFrequency(DEFAULT_LIC_FREQUENCY)
            .radioTVUsage(DEFAULT_RADIO_TV_USAGE)
            .radioTVFrequency(DEFAULT_RADIO_TV_FREQUENCY)
            .taxClass(DEFAULT_TAX_CLASS)
            .nettMass(DEFAULT_NETT_MASS)
            .licExpiryDate(DEFAULT_LIC_EXPIRY_DATE)
            .transactionAmt(DEFAULT_TRANSACTION_AMT)
            .arrearsAmt(DEFAULT_ARREARS_AMT)
            .penaltiesAmt(DEFAULT_PENALTIES_AMT)
            .administrationAmt(DEFAULT_ADMINISTRATION_AMT)
            .totalLicAmt(DEFAULT_TOTAL_LIC_AMT)
            .radioTVAmt(DEFAULT_RADIO_TV_AMT)
            .radioTVArrearsAmt(DEFAULT_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(DEFAULT_TOTAL_RADIO_TV_AMT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT);
        return quotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotes createUpdatedEntity(EntityManager em) {
        Quotes quotes = new Quotes()
            .vRN(UPDATED_V_RN)
            .licenceID(UPDATED_LICENCE_ID)
            .result(UPDATED_RESULT)
            .message(UPDATED_MESSAGE)
            .iDNumber(UPDATED_I_D_NUMBER)
            .clientIDType(UPDATED_CLIENT_ID_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburbID(UPDATED_SUBURB_ID)
            .licFrequency(UPDATED_LIC_FREQUENCY)
            .radioTVUsage(UPDATED_RADIO_TV_USAGE)
            .radioTVFrequency(UPDATED_RADIO_TV_FREQUENCY)
            .taxClass(UPDATED_TAX_CLASS)
            .nettMass(UPDATED_NETT_MASS)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(UPDATED_TOTAL_RADIO_TV_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);
        return quotes;
    }

    @BeforeEach
    public void initTest() {
        quotes = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedQuotes != null) {
            quotesRepository.delete(insertedQuotes);
            insertedQuotes = null;
        }
    }

    @Test
    @Transactional
    void createQuotes() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Quotes
        var returnedQuotes = om.readValue(
            restQuotesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotes)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Quotes.class
        );

        // Validate the Quotes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertQuotesUpdatableFieldsEquals(returnedQuotes, getPersistedQuotes(returnedQuotes));

        insertedQuotes = returnedQuotes;
    }

    @Test
    @Transactional
    void createQuotesWithExistingId() throws Exception {
        // Create the Quotes with an existing ID
        quotes.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotes)))
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuotes() throws Exception {
        // Initialize the database
        insertedQuotes = quotesRepository.saveAndFlush(quotes);

        // Get all the quotesList
        restQuotesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].vRN").value(hasItem(DEFAULT_V_RN)))
            .andExpect(jsonPath("$.[*].licenceID").value(hasItem(DEFAULT_LICENCE_ID)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].iDNumber").value(hasItem(DEFAULT_I_D_NUMBER)))
            .andExpect(jsonPath("$.[*].clientIDType").value(hasItem(DEFAULT_CLIENT_ID_TYPE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].suburbID").value(hasItem(DEFAULT_SUBURB_ID)))
            .andExpect(jsonPath("$.[*].licFrequency").value(hasItem(DEFAULT_LIC_FREQUENCY)))
            .andExpect(jsonPath("$.[*].radioTVUsage").value(hasItem(DEFAULT_RADIO_TV_USAGE)))
            .andExpect(jsonPath("$.[*].radioTVFrequency").value(hasItem(DEFAULT_RADIO_TV_FREQUENCY)))
            .andExpect(jsonPath("$.[*].taxClass").value(hasItem(DEFAULT_TAX_CLASS)))
            .andExpect(jsonPath("$.[*].nettMass").value(hasItem(DEFAULT_NETT_MASS)))
            .andExpect(jsonPath("$.[*].licExpiryDate").value(hasItem(DEFAULT_LIC_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].transactionAmt").value(hasItem(DEFAULT_TRANSACTION_AMT)))
            .andExpect(jsonPath("$.[*].arrearsAmt").value(hasItem(DEFAULT_ARREARS_AMT)))
            .andExpect(jsonPath("$.[*].penaltiesAmt").value(hasItem(DEFAULT_PENALTIES_AMT)))
            .andExpect(jsonPath("$.[*].administrationAmt").value(hasItem(DEFAULT_ADMINISTRATION_AMT)))
            .andExpect(jsonPath("$.[*].totalLicAmt").value(hasItem(DEFAULT_TOTAL_LIC_AMT)))
            .andExpect(jsonPath("$.[*].radioTVAmt").value(hasItem(DEFAULT_RADIO_TV_AMT)))
            .andExpect(jsonPath("$.[*].radioTVArrearsAmt").value(hasItem(DEFAULT_RADIO_TV_ARREARS_AMT)))
            .andExpect(jsonPath("$.[*].totalRadioTVAmt").value(hasItem(DEFAULT_TOTAL_RADIO_TV_AMT)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)));
    }

    @Test
    @Transactional
    void getQuotes() throws Exception {
        // Initialize the database
        insertedQuotes = quotesRepository.saveAndFlush(quotes);

        // Get the quotes
        restQuotesMockMvc
            .perform(get(ENTITY_API_URL_ID, quotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotes.getId().intValue()))
            .andExpect(jsonPath("$.vRN").value(DEFAULT_V_RN))
            .andExpect(jsonPath("$.licenceID").value(DEFAULT_LICENCE_ID))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.iDNumber").value(DEFAULT_I_D_NUMBER))
            .andExpect(jsonPath("$.clientIDType").value(DEFAULT_CLIENT_ID_TYPE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.suburbID").value(DEFAULT_SUBURB_ID))
            .andExpect(jsonPath("$.licFrequency").value(DEFAULT_LIC_FREQUENCY))
            .andExpect(jsonPath("$.radioTVUsage").value(DEFAULT_RADIO_TV_USAGE))
            .andExpect(jsonPath("$.radioTVFrequency").value(DEFAULT_RADIO_TV_FREQUENCY))
            .andExpect(jsonPath("$.taxClass").value(DEFAULT_TAX_CLASS))
            .andExpect(jsonPath("$.nettMass").value(DEFAULT_NETT_MASS))
            .andExpect(jsonPath("$.licExpiryDate").value(DEFAULT_LIC_EXPIRY_DATE))
            .andExpect(jsonPath("$.transactionAmt").value(DEFAULT_TRANSACTION_AMT))
            .andExpect(jsonPath("$.arrearsAmt").value(DEFAULT_ARREARS_AMT))
            .andExpect(jsonPath("$.penaltiesAmt").value(DEFAULT_PENALTIES_AMT))
            .andExpect(jsonPath("$.administrationAmt").value(DEFAULT_ADMINISTRATION_AMT))
            .andExpect(jsonPath("$.totalLicAmt").value(DEFAULT_TOTAL_LIC_AMT))
            .andExpect(jsonPath("$.radioTVAmt").value(DEFAULT_RADIO_TV_AMT))
            .andExpect(jsonPath("$.radioTVArrearsAmt").value(DEFAULT_RADIO_TV_ARREARS_AMT))
            .andExpect(jsonPath("$.totalRadioTVAmt").value(DEFAULT_TOTAL_RADIO_TV_AMT))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT));
    }

    @Test
    @Transactional
    void getNonExistingQuotes() throws Exception {
        // Get the quotes
        restQuotesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuotes() throws Exception {
        // Initialize the database
        insertedQuotes = quotesRepository.saveAndFlush(quotes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quotes
        Quotes updatedQuotes = quotesRepository.findById(quotes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQuotes are not directly saved in db
        em.detach(updatedQuotes);
        updatedQuotes
            .vRN(UPDATED_V_RN)
            .licenceID(UPDATED_LICENCE_ID)
            .result(UPDATED_RESULT)
            .message(UPDATED_MESSAGE)
            .iDNumber(UPDATED_I_D_NUMBER)
            .clientIDType(UPDATED_CLIENT_ID_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburbID(UPDATED_SUBURB_ID)
            .licFrequency(UPDATED_LIC_FREQUENCY)
            .radioTVUsage(UPDATED_RADIO_TV_USAGE)
            .radioTVFrequency(UPDATED_RADIO_TV_FREQUENCY)
            .taxClass(UPDATED_TAX_CLASS)
            .nettMass(UPDATED_NETT_MASS)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(UPDATED_TOTAL_RADIO_TV_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restQuotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedQuotes))
            )
            .andExpect(status().isOk());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQuotesToMatchAllProperties(updatedQuotes);
    }

    @Test
    @Transactional
    void putNonExistingQuotes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotesMockMvc
            .perform(put(ENTITY_API_URL_ID, quotes.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotes)))
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(quotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotesWithPatch() throws Exception {
        // Initialize the database
        insertedQuotes = quotesRepository.saveAndFlush(quotes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quotes using partial update
        Quotes partialUpdatedQuotes = new Quotes();
        partialUpdatedQuotes.setId(quotes.getId());

        partialUpdatedQuotes
            .licenceID(UPDATED_LICENCE_ID)
            .clientIDType(UPDATED_CLIENT_ID_TYPE)
            .address1(UPDATED_ADDRESS_1)
            .licFrequency(UPDATED_LIC_FREQUENCY)
            .radioTVFrequency(UPDATED_RADIO_TV_FREQUENCY)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT);

        restQuotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuotes))
            )
            .andExpect(status().isOk());

        // Validate the Quotes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuotesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedQuotes, quotes), getPersistedQuotes(quotes));
    }

    @Test
    @Transactional
    void fullUpdateQuotesWithPatch() throws Exception {
        // Initialize the database
        insertedQuotes = quotesRepository.saveAndFlush(quotes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quotes using partial update
        Quotes partialUpdatedQuotes = new Quotes();
        partialUpdatedQuotes.setId(quotes.getId());

        partialUpdatedQuotes
            .vRN(UPDATED_V_RN)
            .licenceID(UPDATED_LICENCE_ID)
            .result(UPDATED_RESULT)
            .message(UPDATED_MESSAGE)
            .iDNumber(UPDATED_I_D_NUMBER)
            .clientIDType(UPDATED_CLIENT_ID_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburbID(UPDATED_SUBURB_ID)
            .licFrequency(UPDATED_LIC_FREQUENCY)
            .radioTVUsage(UPDATED_RADIO_TV_USAGE)
            .radioTVFrequency(UPDATED_RADIO_TV_FREQUENCY)
            .taxClass(UPDATED_TAX_CLASS)
            .nettMass(UPDATED_NETT_MASS)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(UPDATED_TOTAL_RADIO_TV_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restQuotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuotes))
            )
            .andExpect(status().isOk());

        // Validate the Quotes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuotesUpdatableFieldsEquals(partialUpdatedQuotes, getPersistedQuotes(partialUpdatedQuotes));
    }

    @Test
    @Transactional
    void patchNonExistingQuotes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotes.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(quotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(quotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(quotes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotes() throws Exception {
        // Initialize the database
        insertedQuotes = quotesRepository.saveAndFlush(quotes);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the quotes
        restQuotesMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return quotesRepository.count();
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

    protected Quotes getPersistedQuotes(Quotes quotes) {
        return quotesRepository.findById(quotes.getId()).orElseThrow();
    }

    protected void assertPersistedQuotesToMatchAllProperties(Quotes expectedQuotes) {
        assertQuotesAllPropertiesEquals(expectedQuotes, getPersistedQuotes(expectedQuotes));
    }

    protected void assertPersistedQuotesToMatchUpdatableProperties(Quotes expectedQuotes) {
        assertQuotesAllUpdatablePropertiesEquals(expectedQuotes, getPersistedQuotes(expectedQuotes));
    }
}
