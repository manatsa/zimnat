package org.zimnat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.zimnat.domain.ResponsesAsserts.*;
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
import org.zimnat.domain.Responses;
import org.zimnat.repository.ResponsesRepository;

/**
 * Integration tests for the {@link ResponsesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResponsesResourceIT {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResponsesRepository responsesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsesMockMvc;

    private Responses responses;

    private Responses insertedResponses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responses createEntity(EntityManager em) {
        Responses responses = new Responses().result(DEFAULT_RESULT).message(DEFAULT_MESSAGE);
        return responses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responses createUpdatedEntity(EntityManager em) {
        Responses responses = new Responses().result(UPDATED_RESULT).message(UPDATED_MESSAGE);
        return responses;
    }

    @BeforeEach
    public void initTest() {
        responses = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedResponses != null) {
            responsesRepository.delete(insertedResponses);
            insertedResponses = null;
        }
    }

    @Test
    @Transactional
    void createResponses() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Responses
        var returnedResponses = om.readValue(
            restResponsesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responses)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Responses.class
        );

        // Validate the Responses in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertResponsesUpdatableFieldsEquals(returnedResponses, getPersistedResponses(returnedResponses));

        insertedResponses = returnedResponses;
    }

    @Test
    @Transactional
    void createResponsesWithExistingId() throws Exception {
        // Create the Responses with an existing ID
        responses.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responses)))
            .andExpect(status().isBadRequest());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResponses() throws Exception {
        // Initialize the database
        insertedResponses = responsesRepository.saveAndFlush(responses);

        // Get all the responsesList
        restResponsesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responses.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getResponses() throws Exception {
        // Initialize the database
        insertedResponses = responsesRepository.saveAndFlush(responses);

        // Get the responses
        restResponsesMockMvc
            .perform(get(ENTITY_API_URL_ID, responses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responses.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingResponses() throws Exception {
        // Get the responses
        restResponsesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponses() throws Exception {
        // Initialize the database
        insertedResponses = responsesRepository.saveAndFlush(responses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the responses
        Responses updatedResponses = responsesRepository.findById(responses.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedResponses are not directly saved in db
        em.detach(updatedResponses);
        updatedResponses.result(UPDATED_RESULT).message(UPDATED_MESSAGE);

        restResponsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResponses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedResponses))
            )
            .andExpect(status().isOk());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedResponsesToMatchAllProperties(updatedResponses);
    }

    @Test
    @Transactional
    void putNonExistingResponses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responses.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responses.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responses.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(responses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responses.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responses)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsesWithPatch() throws Exception {
        // Initialize the database
        insertedResponses = responsesRepository.saveAndFlush(responses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the responses using partial update
        Responses partialUpdatedResponses = new Responses();
        partialUpdatedResponses.setId(responses.getId());

        partialUpdatedResponses.result(UPDATED_RESULT).message(UPDATED_MESSAGE);

        restResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResponses))
            )
            .andExpect(status().isOk());

        // Validate the Responses in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResponsesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedResponses, responses),
            getPersistedResponses(responses)
        );
    }

    @Test
    @Transactional
    void fullUpdateResponsesWithPatch() throws Exception {
        // Initialize the database
        insertedResponses = responsesRepository.saveAndFlush(responses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the responses using partial update
        Responses partialUpdatedResponses = new Responses();
        partialUpdatedResponses.setId(responses.getId());

        partialUpdatedResponses.result(UPDATED_RESULT).message(UPDATED_MESSAGE);

        restResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResponses))
            )
            .andExpect(status().isOk());

        // Validate the Responses in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResponsesUpdatableFieldsEquals(partialUpdatedResponses, getPersistedResponses(partialUpdatedResponses));
    }

    @Test
    @Transactional
    void patchNonExistingResponses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responses.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(responses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responses.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(responses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responses.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(responses)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Responses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponses() throws Exception {
        // Initialize the database
        insertedResponses = responsesRepository.saveAndFlush(responses);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the responses
        restResponsesMockMvc
            .perform(delete(ENTITY_API_URL_ID, responses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return responsesRepository.count();
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

    protected Responses getPersistedResponses(Responses responses) {
        return responsesRepository.findById(responses.getId()).orElseThrow();
    }

    protected void assertPersistedResponsesToMatchAllProperties(Responses expectedResponses) {
        assertResponsesAllPropertiesEquals(expectedResponses, getPersistedResponses(expectedResponses));
    }

    protected void assertPersistedResponsesToMatchUpdatableProperties(Responses expectedResponses) {
        assertResponsesAllUpdatablePropertiesEquals(expectedResponses, getPersistedResponses(expectedResponses));
    }
}
