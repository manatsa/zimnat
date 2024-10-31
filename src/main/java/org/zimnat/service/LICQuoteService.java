package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICQuote;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICQuote}.
 */
public interface LICQuoteService {
    /**
     * Save a lICQuote.
     *
     * @param lICQuote the entity to save.
     * @return the persisted entity.
     */
    LICQuote save(LICQuote lICQuote);

    /**
     * Updates a lICQuote.
     *
     * @param lICQuote the entity to update.
     * @return the persisted entity.
     */
    LICQuote update(LICQuote lICQuote);

    /**
     * Partially updates a lICQuote.
     *
     * @param lICQuote the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICQuote> partialUpdate(LICQuote lICQuote);

    /**
     * Get all the lICQuotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICQuote> findAll(Pageable pageable);

    /**
     * Get the "id" lICQuote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICQuote> findOne(Long id);

    /**
     * Delete the "id" lICQuote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
