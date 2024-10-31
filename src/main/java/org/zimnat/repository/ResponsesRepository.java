package org.zimnat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zimnat.domain.Responses;

/**
 * Spring Data JPA repository for the Responses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsesRepository extends JpaRepository<Responses, Long> {}
