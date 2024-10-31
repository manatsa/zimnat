package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.BatchStatus;

/**
 * Service Interface for managing {@link org.zimnat.domain.BatchStatus}.
 */
public interface BatchStatusService {
    /**
     * Save a batchStatus.
     *
     * @param batchStatus the entity to save.
     * @return the persisted entity.
     */
    BatchStatus save(BatchStatus batchStatus);

    /**
     * Updates a batchStatus.
     *
     * @param batchStatus the entity to update.
     * @return the persisted entity.
     */
    BatchStatus update(BatchStatus batchStatus);

    /**
     * Partially updates a batchStatus.
     *
     * @param batchStatus the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BatchStatus> partialUpdate(BatchStatus batchStatus);

    /**
     * Get all the batchStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BatchStatus> findAll(Pageable pageable);

    /**
     * Get the "id" batchStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BatchStatus> findOne(Long id);

    /**
     * Delete the "id" batchStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
