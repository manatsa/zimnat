package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICConfirmationResponseAsserts.*;
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
import org.zimnat.domain.LICConfirmationResponse;
import org.zimnat.repository.LICConfirmationResponseRepository;

/**
 * Integration tests for the {@link LICConfirmationResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICConfirmationResponseResourceIT {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_V_RN = "AAAAAAAAAA";
    private static final String UPDATED_V_RN = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_LOADED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LOADED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LOADED_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LOADED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVED_DATE = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_I_D_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_I_D_NUMBER = "BBBBBBBBBB";

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

    private static final String DEFAULT_TAX_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_NETT_MASS = "AAAAAAAAAA";
    private static final String UPDATED_NETT_MASS = "BBBBBBBBBB";

    private static final String DEFAULT_LIC_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LIC_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_RADIO_TV_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RADIO_TV_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lic-confirmation-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICConfirmationResponseRepository lICConfirmationResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICConfirmationResponseMockMvc;

    private LICConfirmationResponse lICConfirmationResponse;

    private LICConfirmationResponse insertedLICConfirmationResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICConfirmationResponse createEntity(EntityManager em) {
        LICConfirmationResponse lICConfirmationResponse = new LICConfirmationResponse()
            .result(DEFAULT_RESULT)
            .message(DEFAULT_MESSAGE)
            .licenceID(DEFAULT_LICENCE_ID)
            .receiptID(DEFAULT_RECEIPT_ID)
            .vRN(DEFAULT_V_RN)
            .status(DEFAULT_STATUS)
            .loadedBy(DEFAULT_LOADED_BY)
            .loadedDate(DEFAULT_LOADED_DATE)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvedDate(DEFAULT_APPROVED_DATE)
            .iDNumber(DEFAULT_I_D_NUMBER)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .suburbID(DEFAULT_SUBURB_ID)
            .transactionAmt(DEFAULT_TRANSACTION_AMT)
            .arrearsAmt(DEFAULT_ARREARS_AMT)
            .penaltiesAmt(DEFAULT_PENALTIES_AMT)
            .administrationAmt(DEFAULT_ADMINISTRATION_AMT)
            .totalLicAmt(DEFAULT_TOTAL_LIC_AMT)
            .radioTVAmt(DEFAULT_RADIO_TV_AMT)
            .radioTVArrearsAmt(DEFAULT_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(DEFAULT_TOTAL_RADIO_TV_AMT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .taxClass(DEFAULT_TAX_CLASS)
            .nettMass(DEFAULT_NETT_MASS)
            .licExpiryDate(DEFAULT_LIC_EXPIRY_DATE)
            .radioTVExpiryDate(DEFAULT_RADIO_TV_EXPIRY_DATE);
        return lICConfirmationResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICConfirmationResponse createUpdatedEntity(EntityManager em) {
        LICConfirmationResponse lICConfirmationResponse = new LICConfirmationResponse()
            .result(UPDATED_RESULT)
            .message(UPDATED_MESSAGE)
            .licenceID(UPDATED_LICENCE_ID)
            .receiptID(UPDATED_RECEIPT_ID)
            .vRN(UPDATED_V_RN)
            .status(UPDATED_STATUS)
            .loadedBy(UPDATED_LOADED_BY)
            .loadedDate(UPDATED_LOADED_DATE)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedDate(UPDATED_APPROVED_DATE)
            .iDNumber(UPDATED_I_D_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburbID(UPDATED_SUBURB_ID)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(UPDATED_TOTAL_RADIO_TV_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .taxClass(UPDATED_TAX_CLASS)
            .nettMass(UPDATED_NETT_MASS)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .radioTVExpiryDate(UPDATED_RADIO_TV_EXPIRY_DATE);
        return lICConfirmationResponse;
    }

    @BeforeEach
    public void initTest() {
        lICConfirmationResponse = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICConfirmationResponse != null) {
            lICConfirmationResponseRepository.delete(insertedLICConfirmationResponse);
            insertedLICConfirmationResponse = null;
        }
    }

    @Test
    @Transactional
    void createLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICConfirmationResponse
        var returnedLICConfirmationResponse = om.readValue(
            restLICConfirmationResponseMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICConfirmationResponse))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICConfirmationResponse.class
        );

        // Validate the LICConfirmationResponse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICConfirmationResponseUpdatableFieldsEquals(
            returnedLICConfirmationResponse,
            getPersistedLICConfirmationResponse(returnedLICConfirmationResponse)
        );

        insertedLICConfirmationResponse = returnedLICConfirmationResponse;
    }

    @Test
    @Transactional
    void createLICConfirmationResponseWithExistingId() throws Exception {
        // Create the LICConfirmationResponse with an existing ID
        lICConfirmationResponse.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICConfirmationResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICConfirmationResponse)))
            .andExpect(status().isBadRequest());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLICConfirmationResponses() throws Exception {
        // Initialize the database
        insertedLICConfirmationResponse = lICConfirmationResponseRepository.saveAndFlush(lICConfirmationResponse);

        // Get all the lICConfirmationResponseList
        restLICConfirmationResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICConfirmationResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].licenceID").value(hasItem(DEFAULT_LICENCE_ID)))
            .andExpect(jsonPath("$.[*].receiptID").value(hasItem(DEFAULT_RECEIPT_ID)))
            .andExpect(jsonPath("$.[*].vRN").value(hasItem(DEFAULT_V_RN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].loadedBy").value(hasItem(DEFAULT_LOADED_BY)))
            .andExpect(jsonPath("$.[*].loadedDate").value(hasItem(DEFAULT_LOADED_DATE)))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(DEFAULT_APPROVED_DATE)))
            .andExpect(jsonPath("$.[*].iDNumber").value(hasItem(DEFAULT_I_D_NUMBER)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].suburbID").value(hasItem(DEFAULT_SUBURB_ID)))
            .andExpect(jsonPath("$.[*].transactionAmt").value(hasItem(DEFAULT_TRANSACTION_AMT)))
            .andExpect(jsonPath("$.[*].arrearsAmt").value(hasItem(DEFAULT_ARREARS_AMT)))
            .andExpect(jsonPath("$.[*].penaltiesAmt").value(hasItem(DEFAULT_PENALTIES_AMT)))
            .andExpect(jsonPath("$.[*].administrationAmt").value(hasItem(DEFAULT_ADMINISTRATION_AMT)))
            .andExpect(jsonPath("$.[*].totalLicAmt").value(hasItem(DEFAULT_TOTAL_LIC_AMT)))
            .andExpect(jsonPath("$.[*].radioTVAmt").value(hasItem(DEFAULT_RADIO_TV_AMT)))
            .andExpect(jsonPath("$.[*].radioTVArrearsAmt").value(hasItem(DEFAULT_RADIO_TV_ARREARS_AMT)))
            .andExpect(jsonPath("$.[*].totalRadioTVAmt").value(hasItem(DEFAULT_TOTAL_RADIO_TV_AMT)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.[*].taxClass").value(hasItem(DEFAULT_TAX_CLASS)))
            .andExpect(jsonPath("$.[*].nettMass").value(hasItem(DEFAULT_NETT_MASS)))
            .andExpect(jsonPath("$.[*].licExpiryDate").value(hasItem(DEFAULT_LIC_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].radioTVExpiryDate").value(hasItem(DEFAULT_RADIO_TV_EXPIRY_DATE)));
    }

    @Test
    @Transactional
    void getLICConfirmationResponse() throws Exception {
        // Initialize the database
        insertedLICConfirmationResponse = lICConfirmationResponseRepository.saveAndFlush(lICConfirmationResponse);

        // Get the lICConfirmationResponse
        restLICConfirmationResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, lICConfirmationResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICConfirmationResponse.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.licenceID").value(DEFAULT_LICENCE_ID))
            .andExpect(jsonPath("$.receiptID").value(DEFAULT_RECEIPT_ID))
            .andExpect(jsonPath("$.vRN").value(DEFAULT_V_RN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.loadedBy").value(DEFAULT_LOADED_BY))
            .andExpect(jsonPath("$.loadedDate").value(DEFAULT_LOADED_DATE))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY))
            .andExpect(jsonPath("$.approvedDate").value(DEFAULT_APPROVED_DATE))
            .andExpect(jsonPath("$.iDNumber").value(DEFAULT_I_D_NUMBER))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.suburbID").value(DEFAULT_SUBURB_ID))
            .andExpect(jsonPath("$.transactionAmt").value(DEFAULT_TRANSACTION_AMT))
            .andExpect(jsonPath("$.arrearsAmt").value(DEFAULT_ARREARS_AMT))
            .andExpect(jsonPath("$.penaltiesAmt").value(DEFAULT_PENALTIES_AMT))
            .andExpect(jsonPath("$.administrationAmt").value(DEFAULT_ADMINISTRATION_AMT))
            .andExpect(jsonPath("$.totalLicAmt").value(DEFAULT_TOTAL_LIC_AMT))
            .andExpect(jsonPath("$.radioTVAmt").value(DEFAULT_RADIO_TV_AMT))
            .andExpect(jsonPath("$.radioTVArrearsAmt").value(DEFAULT_RADIO_TV_ARREARS_AMT))
            .andExpect(jsonPath("$.totalRadioTVAmt").value(DEFAULT_TOTAL_RADIO_TV_AMT))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT))
            .andExpect(jsonPath("$.taxClass").value(DEFAULT_TAX_CLASS))
            .andExpect(jsonPath("$.nettMass").value(DEFAULT_NETT_MASS))
            .andExpect(jsonPath("$.licExpiryDate").value(DEFAULT_LIC_EXPIRY_DATE))
            .andExpect(jsonPath("$.radioTVExpiryDate").value(DEFAULT_RADIO_TV_EXPIRY_DATE));
    }

    @Test
    @Transactional
    void getNonExistingLICConfirmationResponse() throws Exception {
        // Get the lICConfirmationResponse
        restLICConfirmationResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICConfirmationResponse() throws Exception {
        // Initialize the database
        insertedLICConfirmationResponse = lICConfirmationResponseRepository.saveAndFlush(lICConfirmationResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICConfirmationResponse
        LICConfirmationResponse updatedLICConfirmationResponse = lICConfirmationResponseRepository
            .findById(lICConfirmationResponse.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedLICConfirmationResponse are not directly saved in db
        em.detach(updatedLICConfirmationResponse);
        updatedLICConfirmationResponse
            .result(UPDATED_RESULT)
            .message(UPDATED_MESSAGE)
            .licenceID(UPDATED_LICENCE_ID)
            .receiptID(UPDATED_RECEIPT_ID)
            .vRN(UPDATED_V_RN)
            .status(UPDATED_STATUS)
            .loadedBy(UPDATED_LOADED_BY)
            .loadedDate(UPDATED_LOADED_DATE)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedDate(UPDATED_APPROVED_DATE)
            .iDNumber(UPDATED_I_D_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburbID(UPDATED_SUBURB_ID)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(UPDATED_TOTAL_RADIO_TV_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .taxClass(UPDATED_TAX_CLASS)
            .nettMass(UPDATED_NETT_MASS)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .radioTVExpiryDate(UPDATED_RADIO_TV_EXPIRY_DATE);

        restLICConfirmationResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICConfirmationResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICConfirmationResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICConfirmationResponseToMatchAllProperties(updatedLICConfirmationResponse);
    }

    @Test
    @Transactional
    void putNonExistingLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICConfirmationResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICConfirmationResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICConfirmationResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICConfirmationResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICConfirmationResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICConfirmationResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICConfirmationResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICConfirmationResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICConfirmationResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICConfirmationResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICConfirmationResponseWithPatch() throws Exception {
        // Initialize the database
        insertedLICConfirmationResponse = lICConfirmationResponseRepository.saveAndFlush(lICConfirmationResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICConfirmationResponse using partial update
        LICConfirmationResponse partialUpdatedLICConfirmationResponse = new LICConfirmationResponse();
        partialUpdatedLICConfirmationResponse.setId(lICConfirmationResponse.getId());

        partialUpdatedLICConfirmationResponse
            .licenceID(UPDATED_LICENCE_ID)
            .receiptID(UPDATED_RECEIPT_ID)
            .vRN(UPDATED_V_RN)
            .status(UPDATED_STATUS)
            .loadedBy(UPDATED_LOADED_BY)
            .loadedDate(UPDATED_LOADED_DATE)
            .approvedBy(UPDATED_APPROVED_BY)
            .iDNumber(UPDATED_I_D_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .taxClass(UPDATED_TAX_CLASS);

        restLICConfirmationResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICConfirmationResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICConfirmationResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICConfirmationResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICConfirmationResponseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLICConfirmationResponse, lICConfirmationResponse),
            getPersistedLICConfirmationResponse(lICConfirmationResponse)
        );
    }

    @Test
    @Transactional
    void fullUpdateLICConfirmationResponseWithPatch() throws Exception {
        // Initialize the database
        insertedLICConfirmationResponse = lICConfirmationResponseRepository.saveAndFlush(lICConfirmationResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICConfirmationResponse using partial update
        LICConfirmationResponse partialUpdatedLICConfirmationResponse = new LICConfirmationResponse();
        partialUpdatedLICConfirmationResponse.setId(lICConfirmationResponse.getId());

        partialUpdatedLICConfirmationResponse
            .result(UPDATED_RESULT)
            .message(UPDATED_MESSAGE)
            .licenceID(UPDATED_LICENCE_ID)
            .receiptID(UPDATED_RECEIPT_ID)
            .vRN(UPDATED_V_RN)
            .status(UPDATED_STATUS)
            .loadedBy(UPDATED_LOADED_BY)
            .loadedDate(UPDATED_LOADED_DATE)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedDate(UPDATED_APPROVED_DATE)
            .iDNumber(UPDATED_I_D_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburbID(UPDATED_SUBURB_ID)
            .transactionAmt(UPDATED_TRANSACTION_AMT)
            .arrearsAmt(UPDATED_ARREARS_AMT)
            .penaltiesAmt(UPDATED_PENALTIES_AMT)
            .administrationAmt(UPDATED_ADMINISTRATION_AMT)
            .totalLicAmt(UPDATED_TOTAL_LIC_AMT)
            .radioTVAmt(UPDATED_RADIO_TV_AMT)
            .radioTVArrearsAmt(UPDATED_RADIO_TV_ARREARS_AMT)
            .totalRadioTVAmt(UPDATED_TOTAL_RADIO_TV_AMT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .taxClass(UPDATED_TAX_CLASS)
            .nettMass(UPDATED_NETT_MASS)
            .licExpiryDate(UPDATED_LIC_EXPIRY_DATE)
            .radioTVExpiryDate(UPDATED_RADIO_TV_EXPIRY_DATE);

        restLICConfirmationResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICConfirmationResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICConfirmationResponse))
            )
            .andExpect(status().isOk());

        // Validate the LICConfirmationResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICConfirmationResponseUpdatableFieldsEquals(
            partialUpdatedLICConfirmationResponse,
            getPersistedLICConfirmationResponse(partialUpdatedLICConfirmationResponse)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICConfirmationResponse.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICConfirmationResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICConfirmationResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICConfirmationResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICConfirmationResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICConfirmationResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICConfirmationResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICConfirmationResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICConfirmationResponse.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICConfirmationResponseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICConfirmationResponse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICConfirmationResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICConfirmationResponse() throws Exception {
        // Initialize the database
        insertedLICConfirmationResponse = lICConfirmationResponseRepository.saveAndFlush(lICConfirmationResponse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICConfirmationResponse
        restLICConfirmationResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICConfirmationResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICConfirmationResponseRepository.count();
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

    protected LICConfirmationResponse getPersistedLICConfirmationResponse(LICConfirmationResponse lICConfirmationResponse) {
        return lICConfirmationResponseRepository.findById(lICConfirmationResponse.getId()).orElseThrow();
    }

    protected void assertPersistedLICConfirmationResponseToMatchAllProperties(LICConfirmationResponse expectedLICConfirmationResponse) {
        assertLICConfirmationResponseAllPropertiesEquals(
            expectedLICConfirmationResponse,
            getPersistedLICConfirmationResponse(expectedLICConfirmationResponse)
        );
    }

    protected void assertPersistedLICConfirmationResponseToMatchUpdatableProperties(
        LICConfirmationResponse expectedLICConfirmationResponse
    ) {
        assertLICConfirmationResponseAllUpdatablePropertiesEquals(
            expectedLICConfirmationResponse,
            getPersistedLICConfirmationResponse(expectedLICConfirmationResponse)
        );
    }
}
