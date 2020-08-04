package com.neosoft.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.neosoft.model.Employee;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
