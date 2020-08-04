package com.neosoft.controller;

import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.model.Job;
import com.neosoft.repository.JobRepository;



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
 * REST controller for managing {@link com.neosoft.domain.Job}.
 */
@RestController
@RequestMapping("/api/neosoft")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);

    private static final String ENTITY_NAME = "job";

    private String applicationName="SpringbootAngPOC";

    private final JobRepository jobRepository;

    public JobResource(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

   
    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to save Job : {}", job);
        if (job.getId() != null) {
            try {
				throw new Exception("A new job cannot already have an ID idexists");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Job result = jobRepository.save(job);
        return ResponseEntity.created(new URI("/api/jobs/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    
    @PutMapping("/jobs")
    public ResponseEntity<Job> updateJob(@RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to update Job : {}", job);
        if (job.getId() == null) {
            try {
				throw new Exception("Invalid id null");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Job result = jobRepository.save(job);
        return ResponseEntity.ok()
            //.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, job.getId().toString()))
            .body(result);
    }

    
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Jobs");
        Page<Job> page;
        if (eagerload) {
            page = jobRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = jobRepository.findAll(pageable);
        }
      //  HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(page.getContent());
    }

    
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Job : {}", id);
        Job job = jobRepository.findOneWithEagerRelationships(id);//.orElseThrow(() -> new ResourceNotFoundException("Job not found for this id :: " + id));
		return ResponseEntity.ok().body(job);
        	//	return job;
    }

   
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        jobRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
