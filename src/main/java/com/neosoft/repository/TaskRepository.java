package com.neosoft.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.neosoft.model.Task;


/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
