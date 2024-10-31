package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICQuoteRequest;

/**
 * Spring Data JPA repository for the LICQuoteRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICQuoteRequestRepository extends JpaRepository<LICQuoteRequest, Long> {}
