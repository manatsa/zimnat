package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICResult;

/**
 * Spring Data JPA repository for the LICResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICResultRepository extends JpaRepository<LICResult, Long> {}
