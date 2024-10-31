package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.BatchStatusRequest;

/**
 * Spring Data JPA repository for the BatchStatusRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatchStatusRequestRepository extends JpaRepository<BatchStatusRequest, Long> {}
