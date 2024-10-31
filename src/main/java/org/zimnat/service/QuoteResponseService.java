package org.zimnat.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.QuoteResponse;

/**
 * Service Interface for managing {@link org.zimnat.domain.QuoteResponse}.
 */
public interface QuoteResponseService {
    /**
     * Save a quoteResponse.
     *
     * @param quoteResponse the entity to save.
     * @return the persisted entity.
     */
    QuoteResponse save(QuoteResponse quoteResponse);

    /**
     * Updates a quoteResponse.
     *
     * @param quoteResponse the entity to update.
     * @return the persisted entity.
     */
    QuoteResponse update(QuoteResponse quoteResponse);

    /**
     * Partially updates a quoteResponse.
     *
     * @param quoteResponse the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuoteResponse> partialUpdate(QuoteResponse quoteResponse);

    /**
     * Get all the quoteResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuoteResponse> findAll(Pageable pageable);

    /**
     * Get all the QuoteResponse where LICQuoteResponse is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<QuoteResponse> findAllWhereLICQuoteResponseIsNull();

    /**
     * Get the "id" quoteResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuoteResponse> findOne(Long id);

    /**
     * Delete the "id" quoteResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
