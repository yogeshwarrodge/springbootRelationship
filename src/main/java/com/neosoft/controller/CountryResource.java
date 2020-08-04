package com.neosoft.controller;


import com.neosoft.model.Country;
import com.neosoft.service.CountryService;



//import io.github.jhipster.web.util.HeaderUtil;
//import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import com.neosoft.exception.ResourceNotFoundException;;



@RestController
@RequestMapping("/api/neosoft")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "country";

   
    private String applicationName="SpringbootAngPOC";

    private final CountryService countryService;

    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

 
    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        if (country.getId() != null) {
            try {
				throw new Exception("A new country cannot already have an ID idexists");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Country result = countryService.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

  
    @PutMapping("/countries")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        if (country.getId() == null) {
        	  try {
				throw new Exception("A new country cannot already have an ID idexists");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Country result = countryService.save(country);
        return ResponseEntity.ok()
           // .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, country.getId().toString()))
            .body(result);
    }


    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        log.debug("REST request to get all Countries");
        return countryService.findAll();
    }

  
    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Country : {}", id);
        Country country = countryService.findOne(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok().body(country);
    }

 
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryService.delete(id);
        //return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        return ResponseEntity.noContent().build();
    }
}
