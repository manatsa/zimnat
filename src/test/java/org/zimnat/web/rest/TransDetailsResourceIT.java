package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.TransDetailsAsserts.*;
import static org.zimnat.web.rest.TestUtil.createUpdateProxyForBean;
import static org.zimnat.web.rest.TestUtil.sameNumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.zimnat.domain.TransDetails;
import org.zimnat.domain.enumeration.BusType;
import org.zimnat.domain.enumeration.CurType;
import org.zimnat.domain.enumeration.PayType;
import org.zimnat.domain.enumeration.Status;
import org.zimnat.domain.enumeration.Status;
import org.zimnat.domain.enumeration.Status;
import org.zimnat.repository.TransDetailsRepository;

/**
 * Integration tests for the {@link TransDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransDetailsResourceIT {

    private static final String DEFAULT_POLICY_REF = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_REF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COVER_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COVER_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COVER_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COVER_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PREMIUM = new BigDecimal(0);
    private static final BigDecimal UPDATED_PREMIUM = new BigDecimal(1);

    private static final PayType DEFAULT_PAY_TYPE = PayType.Mobile;
    private static final PayType UPDATED_PAY_TYPE = PayType.Swipe;

    private static final BusType DEFAULT_BUS_TYPE = BusType.NEW;
    private static final BusType UPDATED_BUS_TYPE = BusType.RENEWAL;

    private static final Status DEFAULT_EXT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_EXT_STATUS = Status.FRESH;

    private static final Status DEFAULT_TRANS_STATUS = Status.ACTIVE;
    private static final Status UPDATED_TRANS_STATUS = Status.FRESH;

    private static final Status DEFAULT_SYNC_STATUS = Status.ACTIVE;
    private static final Status UPDATED_SYNC_STATUS = Status.FRESH;

    private static final CurType DEFAULT_CURRENCY = CurType.USD;
    private static final CurType UPDATED_CURRENCY = CurType.ZWG;

    private static final String ENTITY_API_URL = "/api/trans-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TransDetailsRepository transDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransDetailsMockMvc;

    private TransDetails transDetails;

    private TransDetails insertedTransDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransDetails createEntity(EntityManager em) {
        TransDetails transDetails = new TransDetails()
            .policyRef(DEFAULT_POLICY_REF)
            .coverStartDate(DEFAULT_COVER_START_DATE)
            .coverEndDate(DEFAULT_COVER_END_DATE)
            .premium(DEFAULT_PREMIUM)
            .payType(DEFAULT_PAY_TYPE)
            .busType(DEFAULT_BUS_TYPE)
            .extStatus(DEFAULT_EXT_STATUS)
            .transStatus(DEFAULT_TRANS_STATUS)
            .syncStatus(DEFAULT_SYNC_STATUS)
            .currency(DEFAULT_CURRENCY);
        return transDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransDetails createUpdatedEntity(EntityManager em) {
        TransDetails transDetails = new TransDetails()
            .policyRef(UPDATED_POLICY_REF)
            .coverStartDate(UPDATED_COVER_START_DATE)
            .coverEndDate(UPDATED_COVER_END_DATE)
            .premium(UPDATED_PREMIUM)
            .payType(UPDATED_PAY_TYPE)
            .busType(UPDATED_BUS_TYPE)
            .extStatus(UPDATED_EXT_STATUS)
            .transStatus(UPDATED_TRANS_STATUS)
            .syncStatus(UPDATED_SYNC_STATUS)
            .currency(UPDATED_CURRENCY);
        return transDetails;
    }

    @BeforeEach
    public void initTest() {
        transDetails = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTransDetails != null) {
            transDetailsRepository.delete(insertedTransDetails);
            insertedTransDetails = null;
        }
    }

    @Test
    @Transactional
    void createTransDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TransDetails
        var returnedTransDetails = om.readValue(
            restTransDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TransDetails.class
        );

        // Validate the TransDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTransDetailsUpdatableFieldsEquals(returnedTransDetails, getPersistedTransDetails(returnedTransDetails));

        insertedTransDetails = returnedTransDetails;
    }

    @Test
    @Transactional
    void createTransDetailsWithExistingId() throws Exception {
        // Create the TransDetails with an existing ID
        transDetails.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCoverStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setCoverStartDate(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoverEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setCoverEndDate(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPremiumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setPremium(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPayTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setPayType(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBusTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setBusType(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExtStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setExtStatus(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setTransStatus(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSyncStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setSyncStatus(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrencyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transDetails.setCurrency(null);

        // Create the TransDetails, which fails.

        restTransDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransDetails() throws Exception {
        // Initialize the database
        insertedTransDetails = transDetailsRepository.saveAndFlush(transDetails);

        // Get all the transDetailsList
        restTransDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyRef").value(hasItem(DEFAULT_POLICY_REF)))
            .andExpect(jsonPath("$.[*].coverStartDate").value(hasItem(DEFAULT_COVER_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].coverEndDate").value(hasItem(DEFAULT_COVER_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(sameNumber(DEFAULT_PREMIUM))))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].busType").value(hasItem(DEFAULT_BUS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].extStatus").value(hasItem(DEFAULT_EXT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].transStatus").value(hasItem(DEFAULT_TRANS_STATUS.toString())))
            .andExpect(jsonPath("$.[*].syncStatus").value(hasItem(DEFAULT_SYNC_STATUS.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }

    @Test
    @Transactional
    void getTransDetails() throws Exception {
        // Initialize the database
        insertedTransDetails = transDetailsRepository.saveAndFlush(transDetails);

        // Get the transDetails
        restTransDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, transDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transDetails.getId().intValue()))
            .andExpect(jsonPath("$.policyRef").value(DEFAULT_POLICY_REF))
            .andExpect(jsonPath("$.coverStartDate").value(DEFAULT_COVER_START_DATE.toString()))
            .andExpect(jsonPath("$.coverEndDate").value(DEFAULT_COVER_END_DATE.toString()))
            .andExpect(jsonPath("$.premium").value(sameNumber(DEFAULT_PREMIUM)))
            .andExpect(jsonPath("$.payType").value(DEFAULT_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.busType").value(DEFAULT_BUS_TYPE.toString()))
            .andExpect(jsonPath("$.extStatus").value(DEFAULT_EXT_STATUS.toString()))
            .andExpect(jsonPath("$.transStatus").value(DEFAULT_TRANS_STATUS.toString()))
            .andExpect(jsonPath("$.syncStatus").value(DEFAULT_SYNC_STATUS.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTransDetails() throws Exception {
        // Get the transDetails
        restTransDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransDetails() throws Exception {
        // Initialize the database
        insertedTransDetails = transDetailsRepository.saveAndFlush(transDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transDetails
        TransDetails updatedTransDetails = transDetailsRepository.findById(transDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTransDetails are not directly saved in db
        em.detach(updatedTransDetails);
        updatedTransDetails
            .policyRef(UPDATED_POLICY_REF)
            .coverStartDate(UPDATED_COVER_START_DATE)
            .coverEndDate(UPDATED_COVER_END_DATE)
            .premium(UPDATED_PREMIUM)
            .payType(UPDATED_PAY_TYPE)
            .busType(UPDATED_BUS_TYPE)
            .extStatus(UPDATED_EXT_STATUS)
            .transStatus(UPDATED_TRANS_STATUS)
            .syncStatus(UPDATED_SYNC_STATUS)
            .currency(UPDATED_CURRENCY);

        restTransDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTransDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTransDetails))
            )
            .andExpect(status().isOk());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTransDetailsToMatchAllProperties(updatedTransDetails);
    }

    @Test
    @Transactional
    void putNonExistingTransDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedTransDetails = transDetailsRepository.saveAndFlush(transDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transDetails using partial update
        TransDetails partialUpdatedTransDetails = new TransDetails();
        partialUpdatedTransDetails.setId(transDetails.getId());

        partialUpdatedTransDetails
            .policyRef(UPDATED_POLICY_REF)
            .coverStartDate(UPDATED_COVER_START_DATE)
            .premium(UPDATED_PREMIUM)
            .transStatus(UPDATED_TRANS_STATUS);

        restTransDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTransDetails))
            )
            .andExpect(status().isOk());

        // Validate the TransDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTransDetails, transDetails),
            getPersistedTransDetails(transDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateTransDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedTransDetails = transDetailsRepository.saveAndFlush(transDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transDetails using partial update
        TransDetails partialUpdatedTransDetails = new TransDetails();
        partialUpdatedTransDetails.setId(transDetails.getId());

        partialUpdatedTransDetails
            .policyRef(UPDATED_POLICY_REF)
            .coverStartDate(UPDATED_COVER_START_DATE)
            .coverEndDate(UPDATED_COVER_END_DATE)
            .premium(UPDATED_PREMIUM)
            .payType(UPDATED_PAY_TYPE)
            .busType(UPDATED_BUS_TYPE)
            .extStatus(UPDATED_EXT_STATUS)
            .transStatus(UPDATED_TRANS_STATUS)
            .syncStatus(UPDATED_SYNC_STATUS)
            .currency(UPDATED_CURRENCY);

        restTransDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTransDetails))
            )
            .andExpect(status().isOk());

        // Validate the TransDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransDetailsUpdatableFieldsEquals(partialUpdatedTransDetails, getPersistedTransDetails(partialUpdatedTransDetails));
    }

    @Test
    @Transactional
    void patchNonExistingTransDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(transDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(transDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransDetailsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(transDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransDetails() throws Exception {
        // Initialize the database
        insertedTransDetails = transDetailsRepository.saveAndFlush(transDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the transDetails
        restTransDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, transDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return transDetailsRepository.count();
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

    protected TransDetails getPersistedTransDetails(TransDetails transDetails) {
        return transDetailsRepository.findById(transDetails.getId()).orElseThrow();
    }

    protected void assertPersistedTransDetailsToMatchAllProperties(TransDetails expectedTransDetails) {
        assertTransDetailsAllPropertiesEquals(expectedTransDetails, getPersistedTransDetails(expectedTransDetails));
    }

    protected void assertPersistedTransDetailsToMatchUpdatableProperties(TransDetails expectedTransDetails) {
        assertTransDetailsAllUpdatablePropertiesEquals(expectedTransDetails, getPersistedTransDetails(expectedTransDetails));
    }
}
