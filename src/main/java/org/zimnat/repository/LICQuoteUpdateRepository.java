package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.LICQuoteUpdate;

/**
 * Spring Data JPA repository for the LICQuoteUpdate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LICQuoteUpdateRepository extends JpaRepository<LICQuoteUpdate, Long> {}
