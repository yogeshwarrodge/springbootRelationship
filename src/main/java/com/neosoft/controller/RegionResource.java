package com.neosoft.controller;

import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.model.Region;
import com.neosoft.service.RegionService;


//import io.github.jhipster.web.util.HeaderUtil;
//import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.neosoft.domain.Region}.
 */
@RestController
@RequestMapping("/api/neosoft")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);

    private static final String ENTITY_NAME = "region";

    private String applicationName="SpringbootAngPOC";

    private final RegionService regionService;

    public RegionResource(RegionService regionService) {
        this.regionService = regionService;
    }

    
    @PostMapping("/regions")
    public ResponseEntity<Region> createRegion(@RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to save Region : {}", region);
        if (region.getId() != null) {
            try {
				throw new Exception("A new region cannot already have an ID exists");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Region result = regionService.save(region);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
         //   .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

   
    @PutMapping("/regions")
    public ResponseEntity<Region> updateRegion(@RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to update Region : {}", region);
        if (region.getId() == null) {
            try {
				throw new Exception("Invalid id null");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Region result = regionService.save(region);
        return ResponseEntity.ok()
           // .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, region.getId().toString()))
            .body(result);
    }

   
    @GetMapping("/regions")
    public List<Region> getAllRegions() {
        log.debug("REST request to get all Regions");
        return regionService.findAll();
    }

    
    @GetMapping("/regions/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable Long id)throws ResourceNotFoundException {
        log.debug("REST request to get Region : {}", id);
       Region region = regionService.findOne(id).orElseThrow(()->new ResourceNotFoundException("") );
        return ResponseEntity.ok().body(region);
                // .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, region.getId().toString()))
              //  .body(region);;
    }

    
    @DeleteMapping("/regions/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
