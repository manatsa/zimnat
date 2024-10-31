package org.zimnat.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICQuoteRequest;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICQuoteRequest}.
 */
public interface LICQuoteRequestService {
    /**
     * Save a lICQuoteRequest.
     *
     * @param lICQuoteRequest the entity to save.
     * @return the persisted entity.
     */
    LICQuoteRequest save(LICQuoteRequest lICQuoteRequest);

    /**
     * Updates a lICQuoteRequest.
     *
     * @param lICQuoteRequest the entity to update.
     * @return the persisted entity.
     */
    LICQuoteRequest update(LICQuoteRequest lICQuoteRequest);

    /**
     * Partially updates a lICQuoteRequest.
     *
     * @param lICQuoteRequest the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICQuoteRequest> partialUpdate(LICQuoteRequest lICQuoteRequest);

    /**
     * Get all the lICQuoteRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICQuoteRequest> findAll(Pageable pageable);

    /**
     * Get all the LICQuoteRequest where LICQuote is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LICQuoteRequest> findAllWhereLICQuoteIsNull();

    /**
     * Get the "id" lICQuoteRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICQuoteRequest> findOne(Long id);

    /**
     * Delete the "id" lICQuoteRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
