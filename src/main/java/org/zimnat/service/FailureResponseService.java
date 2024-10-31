package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.FailureResponse;

/**
 * Service Interface for managing {@link org.zimnat.domain.FailureResponse}.
 */
public interface FailureResponseService {
    /**
     * Save a failureResponse.
     *
     * @param failureResponse the entity to save.
     * @return the persisted entity.
     */
    FailureResponse save(FailureResponse failureResponse);

    /**
     * Updates a failureResponse.
     *
     * @param failureResponse the entity to update.
     * @return the persisted entity.
     */
    FailureResponse update(FailureResponse failureResponse);

    /**
     * Partially updates a failureResponse.
     *
     * @param failureResponse the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FailureResponse> partialUpdate(FailureResponse failureResponse);

    /**
     * Get all the failureResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FailureResponse> findAll(Pageable pageable);

    /**
     * Get the "id" failureResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FailureResponse> findOne(Long id);

    /**
     * Delete the "id" failureResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
