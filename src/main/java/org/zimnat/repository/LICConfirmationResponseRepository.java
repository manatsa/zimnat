package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICConfirmationResponse;

/**
 * Spring Data JPA repository for the LICConfirmationResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICConfirmationResponseRepository extends JpaRepository<LICConfirmationResponse, Long> {}
