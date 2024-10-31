package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.Quotes;

/**
 * Service Interface for managing {@link org.zimnat.domain.Quotes}.
 */
public interface QuotesService {
    /**
     * Save a quotes.
     *
     * @param quotes the entity to save.
     * @return the persisted entity.
     */
    Quotes save(Quotes quotes);

    /**
     * Updates a quotes.
     *
     * @param quotes the entity to update.
     * @return the persisted entity.
     */
    Quotes update(Quotes quotes);

    /**
     * Partially updates a quotes.
     *
     * @param quotes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Quotes> partialUpdate(Quotes quotes);

    /**
     * Get all the quotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Quotes> findAll(Pageable pageable);

    /**
     * Get the "id" quotes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Quotes> findOne(Long id);

    /**
     * Delete the "id" quotes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
