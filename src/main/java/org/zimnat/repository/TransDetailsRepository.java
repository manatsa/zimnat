package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.TransDetails;

/**
 * Spring Data JPA repository for the TransDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransDetailsRepository extends JpaRepository<TransDetails, Long> {}
