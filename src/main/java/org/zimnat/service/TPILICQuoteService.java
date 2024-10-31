package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.TPILICQuote;

/**
 * Service Interface for managing {@link org.zimnat.domain.TPILICQuote}.
 */
public interface TPILICQuoteService {
    /**
     * Save a tPILICQuote.
     *
     * @param tPILICQuote the entity to save.
     * @return the persisted entity.
     */
    TPILICQuote save(TPILICQuote tPILICQuote);

    /**
     * Updates a tPILICQuote.
     *
     * @param tPILICQuote the entity to update.
     * @return the persisted entity.
     */
    TPILICQuote update(TPILICQuote tPILICQuote);

    /**
     * Partially updates a tPILICQuote.
     *
     * @param tPILICQuote the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TPILICQuote> partialUpdate(TPILICQuote tPILICQuote);

    /**
     * Get all the tPILICQuotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TPILICQuote> findAll(Pageable pageable);

    /**
     * Get the "id" tPILICQuote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TPILICQuote> findOne(Long id);

    /**
     * Delete the "id" tPILICQuote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
