package com.xa.filteringtest2_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xa.filteringtest2_api.models.CustomerMember;
import com.xa.filteringtest2_api.repositories.CustomerMemberRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomerMemberController {
    
    @Autowired
    private CustomerMemberRepository custMemberRepo;

    @GetMapping("/custmember")
    public ResponseEntity<List<CustomerMember>> getCustMember() {
        try {
            
            List<CustomerMember> custMember = this.custMemberRepo.findAll();
            return new ResponseEntity<List<CustomerMember>>(custMember, HttpStatus.OK);
            }
        catch (Exception e) {
               e.printStackTrace();
               return new ResponseEntity<List<CustomerMember>>(HttpStatus.OK);
            }        
    }

    @GetMapping("/custmember/{id}") 
    public ResponseEntity<?> getCustMemberById(@PathVariable("id") Long id) {
        try {
            CustomerMember custMember = this.custMemberRepo.findById(id).orElse(null);
            if(custMember !=null) {
                return new ResponseEntity<CustomerMember>(custMember, HttpStatus.OK); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer member not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CustomerMember>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/custmember")
    public ResponseEntity<CustomerMember> insertCustMember(@RequestBody CustomerMember custMember) {
        try {
            // custMember.setCreatedBy((long) 1);
            this.custMemberRepo.save(custMember);
            return new ResponseEntity<CustomerMember>(custMember, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CustomerMember>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/custmember/{id}")
    public ResponseEntity<CustomerMember> editCustMember(
        @RequestBody CustomerMember custMember,
        @PathVariable("id") Long id
    ) {
        try {
            // custMember.setModifiedBy((long) 1);
            custMember.setId(id);
            this.custMemberRepo.save(custMember);
            return new ResponseEntity<CustomerMember>(custMember,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<CustomerMember>(HttpStatus.NO_CONTENT);
        }
    }
}
