package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.LICResultResponse;

/**
 * Service Interface for managing {@link org.zimnat.domain.LICResultResponse}.
 */
public interface LICResultResponseService {
    /**
     * Save a lICResultResponse.
     *
     * @param lICResultResponse the entity to save.
     * @return the persisted entity.
     */
    LICResultResponse save(LICResultResponse lICResultResponse);

    /**
     * Updates a lICResultResponse.
     *
     * @param lICResultResponse the entity to update.
     * @return the persisted entity.
     */
    LICResultResponse update(LICResultResponse lICResultResponse);

    /**
     * Partially updates a lICResultResponse.
     *
     * @param lICResultResponse the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LICResultResponse> partialUpdate(LICResultResponse lICResultResponse);

    /**
     * Get all the lICResultResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LICResultResponse> findAll(Pageable pageable);

    /**
     * Get the "id" lICResultResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LICResultResponse> findOne(Long id);

    /**
     * Delete the "id" lICResultResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
