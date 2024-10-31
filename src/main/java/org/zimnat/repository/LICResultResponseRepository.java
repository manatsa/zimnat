package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICResultResponse;

/**
 * Spring Data JPA repository for the LICResultResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICResultResponseRepository extends JpaRepository<LICResultResponse, Long> {}
