package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICQuoteResponse;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICQuoteResponse}.
 */
public interface LICQuoteResponseService {
    /**
     * Save a lICQuoteResponse.
     *
     * @param lICQuoteResponse the entity to save.
     * @return the persisted entity.
     */
    LICQuoteResponse save(LICQuoteResponse lICQuoteResponse);

    /**
     * Updates a lICQuoteResponse.
     *
     * @param lICQuoteResponse the entity to update.
     * @return the persisted entity.
     */
    LICQuoteResponse update(LICQuoteResponse lICQuoteResponse);

    /**
     * Partially updates a lICQuoteResponse.
     *
     * @param lICQuoteResponse the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICQuoteResponse> partialUpdate(LICQuoteResponse lICQuoteResponse);

    /**
     * Get all the lICQuoteResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICQuoteResponse> findAll(Pageable pageable);

    /**
     * Get the "id" lICQuoteResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICQuoteResponse> findOne(Long id);

    /**
     * Delete the "id" lICQuoteResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
