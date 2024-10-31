package org.zimnat.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.BatchStatusRequest;

/**
 * Service Interface for managing {@link org.zimnat.domain.BatchStatusRequest}.
 */
public interface BatchStatusRequestService {
    /**
     * Save a batchStatusRequest.
     *
     * @param batchStatusRequest the entity to save.
     * @return the persisted entity.
     */
    BatchStatusRequest save(BatchStatusRequest batchStatusRequest);

    /**
     * Updates a batchStatusRequest.
     *
     * @param batchStatusRequest the entity to update.
     * @return the persisted entity.
     */
    BatchStatusRequest update(BatchStatusRequest batchStatusRequest);

    /**
     * Partially updates a batchStatusRequest.
     *
     * @param batchStatusRequest the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BatchStatusRequest> partialUpdate(BatchStatusRequest batchStatusRequest);

    /**
     * Get all the batchStatusRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BatchStatusRequest> findAll(Pageable pageable);

    /**
     * Get all the BatchStatusRequest where BatchStatus is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<BatchStatusRequest> findAllWhereBatchStatusIsNull();

    /**
     * Get the "id" batchStatusRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BatchStatusRequest> findOne(Long id);

    /**
     * Delete the "id" batchStatusRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
