package org.zimnat.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICConfirmationResponse;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICConfirmationResponse}.
 */
public interface LICConfirmationResponseService {
    /**
     * Save a lICConfirmationResponse.
     *
     * @param lICConfirmationResponse the entity to save.
     * @return the persisted entity.
     */
    LICConfirmationResponse save(LICConfirmationResponse lICConfirmationResponse);

    /**
     * Updates a lICConfirmationResponse.
     *
     * @param lICConfirmationResponse the entity to update.
     * @return the persisted entity.
     */
    LICConfirmationResponse update(LICConfirmationResponse lICConfirmationResponse);

    /**
     * Partially updates a lICConfirmationResponse.
     *
     * @param lICConfirmationResponse the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICConfirmationResponse> partialUpdate(LICConfirmationResponse lICConfirmationResponse);

    /**
     * Get all the lICConfirmationResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICConfirmationResponse> findAll(Pageable pageable);

    /**
     * Get all the LICConfirmationResponse where LICResultResponse is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LICConfirmationResponse> findAllWhereLICResultResponseIsNull();

    /**
     * Get the "id" lICConfirmationResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICConfirmationResponse> findOne(Long id);

    /**
     * Delete the "id" lICConfirmationResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
