package com.neosoft.controller;

import com.neosoft.model.Department;
import com.neosoft.service.DepartmentService;


import com.neosoft.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/neosoft")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    private String applicationName="SpringbootAngPOC";

    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to save Department : {}", department);
        if (department.getId() != null) {
            try {
				throw new Exception("A new department cannot already have an ID");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Department result = departmentService.save(department);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
        //    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

   
    @PutMapping("/departments")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to update Department : {}", department);
        if (department.getId() == null) {
            try {
				throw new Exception("Invalid id");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Department result = departmentService.save(department);
        return ResponseEntity.ok()
          //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, department.getId().toString()))
            .body(result);
    }

    
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        log.debug("REST request to get all Departments");
        return departmentService.findAll();
    }

    
    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Department : {}", id);
        Department department = departmentService.findOne(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok().body(department);
    }

  
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
       // return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        return ResponseEntity.noContent().build();
    }
}
