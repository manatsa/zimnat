package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.FailureResponse;

/**
 * Spring Data JPA repository for the FailureResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FailureResponseRepository extends JpaRepository<FailureResponse, Long> {}
