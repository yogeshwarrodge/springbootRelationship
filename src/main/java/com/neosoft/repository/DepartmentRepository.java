package com.neosoft.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.neosoft.model.Department;


/**
 * Spring Data  repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
