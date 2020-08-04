package com.neosoft.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.neosoft.model.JobHistory;


/**
 * Spring Data  repository for the JobHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {

}
