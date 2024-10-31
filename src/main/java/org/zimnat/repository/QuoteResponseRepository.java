package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.QuoteResponse;

/**
 * Spring Data JPA repository for the QuoteResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteResponseRepository extends JpaRepository<QuoteResponse, Long> {}
