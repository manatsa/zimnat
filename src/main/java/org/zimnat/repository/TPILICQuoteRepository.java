package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.TPILICQuote;

/**
 * Spring Data JPA repository for the TPILICQuote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPILICQuoteRepository extends JpaRepository<TPILICQuote, Long> {}
