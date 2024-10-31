package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICQuote;

/**
 * Spring Data JPA repository for the LICQuote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICQuoteRepository extends JpaRepository<LICQuote, Long> {}
