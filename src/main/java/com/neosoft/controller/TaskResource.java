package com.neosoft.controller;

import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.model.Task;
import com.neosoft.service.TaskService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/neosoft")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "task";

    private String applicationName="SpringbootAngPOC";

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

  
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to save Task : {}", task);
        if (task.getId() != null) {
            try {
				throw new Exception("A new task cannot already have an ID exists");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Task result = taskService.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
          //  .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

   
    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to update Task : {}", task);
        if (task.getId() == null) {
            try {
				throw new Exception("Invalid id null");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Task result = taskService.save(task);
        return ResponseEntity.ok()
           // .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        log.debug("REST request to get all Tasks");
        return taskService.findAll();
    }

    
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id)throws ResourceNotFoundException {
        log.debug("REST request to get Task : {}", id);
       Task task = taskService.findOne(id).orElseThrow(()-> new ResourceNotFoundException("task not found"));
        return ResponseEntity.ok().body(task);
    }

    
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.debug("REST request to delete Task : {}", id);
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
