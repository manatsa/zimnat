package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.TransDetails;

/**
 * Service Interface for managing {@link org.zimnat.domain.TransDetails}.
 */
public interface TransDetailsService {
    /**
     * Save a transDetails.
     *
     * @param transDetails the entity to save.
     * @return the persisted entity.
     */
    TransDetails save(TransDetails transDetails);

    /**
     * Updates a transDetails.
     *
     * @param transDetails the entity to update.
     * @return the persisted entity.
     */
    TransDetails update(TransDetails transDetails);

    /**
     * Partially updates a transDetails.
     *
     * @param transDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransDetails> partialUpdate(TransDetails transDetails);

    /**
     * Get all the transDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransDetails> findAll(Pageable pageable);

    /**
     * Get the "id" transDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransDetails> findOne(Long id);

    /**
     * Delete the "id" transDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
