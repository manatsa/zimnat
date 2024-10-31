package org.zimnat.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.Responses;

/**
 * Service Interface for managing {@link org.zimnat.domain.Responses}.
 */
public interface ResponsesService {
    /**
     * Save a responses.
     *
     * @param responses the entity to save.
     * @return the persisted entity.
     */
    Responses save(Responses responses);

    /**
     * Updates a responses.
     *
     * @param responses the entity to update.
     * @return the persisted entity.
     */
    Responses update(Responses responses);

    /**
     * Partially updates a responses.
     *
     * @param responses the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Responses> partialUpdate(Responses responses);

    /**
     * Get all the responses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Responses> findAll(Pageable pageable);

    /**
     * Get all the Responses where FailureResponse is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Responses> findAllWhereFailureResponseIsNull();

    /**
     * Get the "id" responses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Responses> findOne(Long id);

    /**
     * Delete the "id" responses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
