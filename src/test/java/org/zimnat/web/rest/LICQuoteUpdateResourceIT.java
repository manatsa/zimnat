package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.LICQuoteUpdateAsserts.*;
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
import org.zimnat.domain.LICQuoteUpdate;
import org.zimnat.domain.enumeration.DeliveryMethod;
import org.zimnat.domain.enumeration.PayType;
import org.zimnat.repository.LICQuoteUpdateRepository;

/**
 * Integration tests for the {@link LICQuoteUpdateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LICQuoteUpdateResourceIT {

    private static final String DEFAULT_PARTNER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_THE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_THE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final PayType DEFAULT_PAYMENT_METHOD = PayType.Mobile;
    private static final PayType UPDATED_PAYMENT_METHOD = PayType.Swipe;

    private static final DeliveryMethod DEFAULT_DELIVERY_METHOD = DeliveryMethod.Cash;
    private static final DeliveryMethod UPDATED_DELIVERY_METHOD = DeliveryMethod.ICEcash;

    private static final String ENTITY_API_URL = "/api/lic-quote-updates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LICQuoteUpdateRepository lICQuoteUpdateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLICQuoteUpdateMockMvc;

    private LICQuoteUpdate lICQuoteUpdate;

    private LICQuoteUpdate insertedLICQuoteUpdate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuoteUpdate createEntity(EntityManager em) {
        LICQuoteUpdate lICQuoteUpdate = new LICQuoteUpdate()
            .partnerReference(DEFAULT_PARTNER_REFERENCE)
            .theDate(DEFAULT_THE_DATE)
            .theVersion(DEFAULT_THE_VERSION)
            .partnerToken(DEFAULT_PARTNER_TOKEN)
            .licenceID(DEFAULT_LICENCE_ID)
            .status(DEFAULT_STATUS)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .deliveryMethod(DEFAULT_DELIVERY_METHOD);
        return lICQuoteUpdate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LICQuoteUpdate createUpdatedEntity(EntityManager em) {
        LICQuoteUpdate lICQuoteUpdate = new LICQuoteUpdate()
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .licenceID(UPDATED_LICENCE_ID)
            .status(UPDATED_STATUS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .deliveryMethod(UPDATED_DELIVERY_METHOD);
        return lICQuoteUpdate;
    }

    @BeforeEach
    public void initTest() {
        lICQuoteUpdate = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedLICQuoteUpdate != null) {
            lICQuoteUpdateRepository.delete(insertedLICQuoteUpdate);
            insertedLICQuoteUpdate = null;
        }
    }

    @Test
    @Transactional
    void createLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LICQuoteUpdate
        var returnedLICQuoteUpdate = om.readValue(
            restLICQuoteUpdateMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LICQuoteUpdate.class
        );

        // Validate the LICQuoteUpdate in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLICQuoteUpdateUpdatableFieldsEquals(returnedLICQuoteUpdate, getPersistedLICQuoteUpdate(returnedLICQuoteUpdate));

        insertedLICQuoteUpdate = returnedLICQuoteUpdate;
    }

    @Test
    @Transactional
    void createLICQuoteUpdateWithExistingId() throws Exception {
        // Create the LICQuoteUpdate with an existing ID
        lICQuoteUpdate.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPartnerReferenceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setPartnerReference(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setTheDate(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setTheVersion(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartnerTokenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setPartnerToken(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLicenceIDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setLicenceID(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setStatus(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentMethodIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setPaymentMethod(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeliveryMethodIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        lICQuoteUpdate.setDeliveryMethod(null);

        // Create the LICQuoteUpdate, which fails.

        restLICQuoteUpdateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLICQuoteUpdates() throws Exception {
        // Initialize the database
        insertedLICQuoteUpdate = lICQuoteUpdateRepository.saveAndFlush(lICQuoteUpdate);

        // Get all the lICQuoteUpdateList
        restLICQuoteUpdateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lICQuoteUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerReference").value(hasItem(DEFAULT_PARTNER_REFERENCE)))
            .andExpect(jsonPath("$.[*].theDate").value(hasItem(DEFAULT_THE_DATE)))
            .andExpect(jsonPath("$.[*].theVersion").value(hasItem(DEFAULT_THE_VERSION)))
            .andExpect(jsonPath("$.[*].partnerToken").value(hasItem(DEFAULT_PARTNER_TOKEN)))
            .andExpect(jsonPath("$.[*].licenceID").value(hasItem(DEFAULT_LICENCE_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].deliveryMethod").value(hasItem(DEFAULT_DELIVERY_METHOD.toString())));
    }

    @Test
    @Transactional
    void getLICQuoteUpdate() throws Exception {
        // Initialize the database
        insertedLICQuoteUpdate = lICQuoteUpdateRepository.saveAndFlush(lICQuoteUpdate);

        // Get the lICQuoteUpdate
        restLICQuoteUpdateMockMvc
            .perform(get(ENTITY_API_URL_ID, lICQuoteUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lICQuoteUpdate.getId().intValue()))
            .andExpect(jsonPath("$.partnerReference").value(DEFAULT_PARTNER_REFERENCE))
            .andExpect(jsonPath("$.theDate").value(DEFAULT_THE_DATE))
            .andExpect(jsonPath("$.theVersion").value(DEFAULT_THE_VERSION))
            .andExpect(jsonPath("$.partnerToken").value(DEFAULT_PARTNER_TOKEN))
            .andExpect(jsonPath("$.licenceID").value(DEFAULT_LICENCE_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.deliveryMethod").value(DEFAULT_DELIVERY_METHOD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLICQuoteUpdate() throws Exception {
        // Get the lICQuoteUpdate
        restLICQuoteUpdateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLICQuoteUpdate() throws Exception {
        // Initialize the database
        insertedLICQuoteUpdate = lICQuoteUpdateRepository.saveAndFlush(lICQuoteUpdate);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteUpdate
        LICQuoteUpdate updatedLICQuoteUpdate = lICQuoteUpdateRepository.findById(lICQuoteUpdate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLICQuoteUpdate are not directly saved in db
        em.detach(updatedLICQuoteUpdate);
        updatedLICQuoteUpdate
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .licenceID(UPDATED_LICENCE_ID)
            .status(UPDATED_STATUS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .deliveryMethod(UPDATED_DELIVERY_METHOD);

        restLICQuoteUpdateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLICQuoteUpdate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLICQuoteUpdate))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLICQuoteUpdateToMatchAllProperties(updatedLICQuoteUpdate);
    }

    @Test
    @Transactional
    void putNonExistingLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteUpdate.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteUpdateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lICQuoteUpdate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuoteUpdate))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteUpdate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteUpdateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lICQuoteUpdate))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteUpdate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteUpdateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLICQuoteUpdateWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuoteUpdate = lICQuoteUpdateRepository.saveAndFlush(lICQuoteUpdate);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteUpdate using partial update
        LICQuoteUpdate partialUpdatedLICQuoteUpdate = new LICQuoteUpdate();
        partialUpdatedLICQuoteUpdate.setId(lICQuoteUpdate.getId());

        partialUpdatedLICQuoteUpdate.theVersion(UPDATED_THE_VERSION).licenceID(UPDATED_LICENCE_ID);

        restLICQuoteUpdateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuoteUpdate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuoteUpdate))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteUpdate in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteUpdateUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLICQuoteUpdate, lICQuoteUpdate),
            getPersistedLICQuoteUpdate(lICQuoteUpdate)
        );
    }

    @Test
    @Transactional
    void fullUpdateLICQuoteUpdateWithPatch() throws Exception {
        // Initialize the database
        insertedLICQuoteUpdate = lICQuoteUpdateRepository.saveAndFlush(lICQuoteUpdate);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lICQuoteUpdate using partial update
        LICQuoteUpdate partialUpdatedLICQuoteUpdate = new LICQuoteUpdate();
        partialUpdatedLICQuoteUpdate.setId(lICQuoteUpdate.getId());

        partialUpdatedLICQuoteUpdate
            .partnerReference(UPDATED_PARTNER_REFERENCE)
            .theDate(UPDATED_THE_DATE)
            .theVersion(UPDATED_THE_VERSION)
            .partnerToken(UPDATED_PARTNER_TOKEN)
            .licenceID(UPDATED_LICENCE_ID)
            .status(UPDATED_STATUS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .deliveryMethod(UPDATED_DELIVERY_METHOD);

        restLICQuoteUpdateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLICQuoteUpdate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLICQuoteUpdate))
            )
            .andExpect(status().isOk());

        // Validate the LICQuoteUpdate in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLICQuoteUpdateUpdatableFieldsEquals(partialUpdatedLICQuoteUpdate, getPersistedLICQuoteUpdate(partialUpdatedLICQuoteUpdate));
    }

    @Test
    @Transactional
    void patchNonExistingLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteUpdate.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLICQuoteUpdateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lICQuoteUpdate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuoteUpdate))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteUpdate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteUpdateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lICQuoteUpdate))
            )
            .andExpect(status().isBadRequest());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLICQuoteUpdate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lICQuoteUpdate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLICQuoteUpdateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lICQuoteUpdate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LICQuoteUpdate in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLICQuoteUpdate() throws Exception {
        // Initialize the database
        insertedLICQuoteUpdate = lICQuoteUpdateRepository.saveAndFlush(lICQuoteUpdate);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lICQuoteUpdate
        restLICQuoteUpdateMockMvc
            .perform(delete(ENTITY_API_URL_ID, lICQuoteUpdate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lICQuoteUpdateRepository.count();
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

    protected LICQuoteUpdate getPersistedLICQuoteUpdate(LICQuoteUpdate lICQuoteUpdate) {
        return lICQuoteUpdateRepository.findById(lICQuoteUpdate.getId()).orElseThrow();
    }

    protected void assertPersistedLICQuoteUpdateToMatchAllProperties(LICQuoteUpdate expectedLICQuoteUpdate) {
        assertLICQuoteUpdateAllPropertiesEquals(expectedLICQuoteUpdate, getPersistedLICQuoteUpdate(expectedLICQuoteUpdate));
    }

    protected void assertPersistedLICQuoteUpdateToMatchUpdatableProperties(LICQuoteUpdate expectedLICQuoteUpdate) {
        assertLICQuoteUpdateAllUpdatablePropertiesEquals(expectedLICQuoteUpdate, getPersistedLICQuoteUpdate(expectedLICQuoteUpdate));
    }
}
