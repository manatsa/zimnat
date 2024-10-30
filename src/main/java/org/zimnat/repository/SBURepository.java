package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.SBU;

/**
 * Spring Data JPA repository for the SBU entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SBURepository extends JpaRepository<SBU, Long> {}
