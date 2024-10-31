package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.BatchStatus;

/**
 * Spring Data JPA repository for the BatchStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatchStatusRepository extends JpaRepository<BatchStatus, Long> {}
