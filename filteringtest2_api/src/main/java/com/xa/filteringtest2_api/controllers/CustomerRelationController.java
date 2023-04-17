package com.xa.filteringtest2_api.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xa.filteringtest2_api.models.CustomerRelation;
import com.xa.filteringtest2_api.repositories.CustomerRelationRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomerRelationController {
    
    @Autowired
    private CustomerRelationRepository custRelRepo;

    @GetMapping("/custrelation")
    public ResponseEntity<List<CustomerRelation>> getAllCustRelation() {
        try {
            
            List<CustomerRelation> custRelation = this.custRelRepo.findAll();
            return new ResponseEntity<List<CustomerRelation>>(custRelation, HttpStatus.OK);
            }
        catch (Exception e) {
               e.printStackTrace();
               return new ResponseEntity<List<CustomerRelation>>(HttpStatus.OK);
            }        
    }

    @GetMapping("/custrelation/{id}") 
    public ResponseEntity<?> getCustRelationById(@PathVariable("id") Long id) {
        try {
            CustomerRelation custRelation = this.custRelRepo.findById(id).orElse(null);
            if(custRelation !=null) {
                return new ResponseEntity<CustomerRelation>(custRelation, HttpStatus.OK); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relation not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CustomerRelation>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/custrelationbyname/{name}")
    public ResponseEntity<List<Map<String, Object>>> getCustRelationByName(
        @PathVariable("name") String name
    ) {
        try {
            List<Map<String, Object>> custRelation = this.custRelRepo.getRelationByName(name);
            return new ResponseEntity<List<Map<String, Object>>>(custRelation, HttpStatus.OK);   
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/custrelation")
    public ResponseEntity<CustomerRelation> insertCustRelation(@RequestBody CustomerRelation custRelation) {
        try {
            // custRelation.setCreatedBy((long) 1);
            this.custRelRepo.save(custRelation);
            return new ResponseEntity<CustomerRelation>(custRelation, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CustomerRelation>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/custrelation/{id}")
    public ResponseEntity<CustomerRelation> editCustRelation(
        @RequestBody CustomerRelation custRelation,
        @PathVariable("id") Long id
    ) {
        try {
            // custRelation.setModifiedBy((long) 1);
            custRelation.setId(id);
            this.custRelRepo.save(custRelation);
            return new ResponseEntity<CustomerRelation>(custRelation,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<CustomerRelation>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/custrelation/{id}")
    public ResponseEntity<CustomerRelation> patchCustRelation(
        @RequestBody CustomerRelation custRelation,
        @PathVariable("id") Long id
    ) {
        try {
            custRelation.setId(id);
            this.custRelRepo.save(custRelation);
            return new ResponseEntity<CustomerRelation>(custRelation,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<CustomerRelation>(HttpStatus.NO_CONTENT);
        }
    }


}
