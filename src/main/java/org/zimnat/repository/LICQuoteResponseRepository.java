package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICQuoteResponse;

/**
 * Spring Data JPA repository for the LICQuoteResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICQuoteResponseRepository extends JpaRepository<LICQuoteResponse, Long> {}
