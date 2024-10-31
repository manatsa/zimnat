package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICQuoteUpdate;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICQuoteUpdate}.
 */
public interface LICQuoteUpdateService {
    /**
     * Save a lICQuoteUpdate.
     *
     * @param lICQuoteUpdate the entity to save.
     * @return the persisted entity.
     */
    LICQuoteUpdate save(LICQuoteUpdate lICQuoteUpdate);

    /**
     * Updates a lICQuoteUpdate.
     *
     * @param lICQuoteUpdate the entity to update.
     * @return the persisted entity.
     */
    LICQuoteUpdate update(LICQuoteUpdate lICQuoteUpdate);

    /**
     * Partially updates a lICQuoteUpdate.
     *
     * @param lICQuoteUpdate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICQuoteUpdate> partialUpdate(LICQuoteUpdate lICQuoteUpdate);

    /**
     * Get all the lICQuoteUpdates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICQuoteUpdate> findAll(Pageable pageable);

    /**
     * Get the "id" lICQuoteUpdate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICQuoteUpdate> findOne(Long id);

    /**
     * Delete the "id" lICQuoteUpdate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
