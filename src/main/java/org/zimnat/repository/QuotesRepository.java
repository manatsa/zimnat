package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.Quotes;

/**
 * Spring Data JPA repository for the Quotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Long> {}
