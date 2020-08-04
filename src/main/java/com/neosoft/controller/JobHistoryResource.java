package com.neosoft.controller;

import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.model.JobHistory;
import com.neosoft.service.JobHistoryService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing {@link com.neosoft.domain.JobHistory}.
 */
@RestController
@RequestMapping("/api/neosoft")
public class JobHistoryResource {

    private final Logger log = LoggerFactory.getLogger(JobHistoryResource.class);

    private static final String ENTITY_NAME = "jobHistory";

    private String applicationName="SpringbootAngPOC";

    private final JobHistoryService jobHistoryService;

    public JobHistoryResource(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }

   
    @PostMapping("/job-histories")
    public ResponseEntity<JobHistory> createJobHistory(@RequestBody JobHistory jobHistory) throws URISyntaxException {
        log.debug("REST request to save JobHistory : {}", jobHistory);
        if (jobHistory.getId() != null) {
            try {
				throw new Exception("A new jobHistory cannot already have an ID");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        JobHistory result = jobHistoryService.save(jobHistory);
        return ResponseEntity.created(new URI("/api/job-histories/" + result.getId()))
        //    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

   
    @PutMapping("/job-histories")
    public ResponseEntity<JobHistory> updateJobHistory(@RequestBody JobHistory jobHistory) throws URISyntaxException {
        log.debug("REST request to update JobHistory : {}", jobHistory);
        if (jobHistory.getId() == null) {
            try {
				throw new Exception("Invalid id");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        JobHistory result = jobHistoryService.save(jobHistory);
        return ResponseEntity.ok()
          //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobHistory.getId().toString()))
            .body(result);
    }

    
    @GetMapping("/job-histories")
    public ResponseEntity<List<JobHistory>> getAllJobHistories(Pageable pageable) {
        log.debug("REST request to get a page of JobHistories");
        Page<JobHistory> page = jobHistoryService.findAll(pageable);
     //   HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(page.getContent());
    }

    
    @GetMapping("/job-histories/{id}")
    public ResponseEntity<JobHistory> getJobHistory(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get JobHistory : {}", id);
      JobHistory jobHistory = jobHistoryService.findOne(id).orElseThrow(()-> new ResourceNotFoundException(""));
        return ResponseEntity.ok().body(jobHistory);
    }

    
    @DeleteMapping("/job-histories/{id}")
    public ResponseEntity<Void> deleteJobHistory(@PathVariable Long id) {
        log.debug("REST request to delete JobHistory : {}", id);
        jobHistoryService.delete(id);
      //  return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        return ResponseEntity.noContent().build();
    }
}
