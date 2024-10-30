package org.zimnat.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zimnat.domain.SBU;

/**
 * Service Interface for managing {@link org.zimnat.domain.SBU}.
 */
public interface SBUService {
    /**
     * Save a sBU.
     *
     * @param sBU the entity to save.
     * @return the persisted entity.
     */
    SBU save(SBU sBU);

    /**
     * Updates a sBU.
     *
     * @param sBU the entity to update.
     * @return the persisted entity.
     */
    SBU update(SBU sBU);

    /**
     * Partially updates a sBU.
     *
     * @param sBU the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SBU> partialUpdate(SBU sBU);

    /**
     * Get all the sBUS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SBU> findAll(Pageable pageable);

    /**
     * Get the "id" sBU.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SBU> findOne(Long id);

    /**
     * Delete the "id" sBU.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
