package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICResult;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICResult}.
 */
public interface LICResultService {
    /**
     * Save a lICResult.
     *
     * @param lICResult the entity to save.
     * @return the persisted entity.
     */
    LICResult save(LICResult lICResult);

    /**
     * Updates a lICResult.
     *
     * @param lICResult the entity to update.
     * @return the persisted entity.
     */
    LICResult update(LICResult lICResult);

    /**
     * Partially updates a lICResult.
     *
     * @param lICResult the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICResult> partialUpdate(LICResult lICResult);

    /**
     * Get all the lICResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICResult> findAll(Pageable pageable);

    /**
     * Get the "id" lICResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICResult> findOne(Long id);

    /**
     * Delete the "id" lICResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
