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

import com.xa.filteringtest2_api.models.Customer;
import com.xa.filteringtest2_api.repositories.CustomerRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomerController {
    
    @Autowired
    private CustomerRepository customerRepo;

    // @GetMapping("/customer")
    // public ResponseEntity<List<Map<String, Object>>> getAllCustomer() {
    //     try {
    //         List<Map<String, Object>> customer = this.customerRepo.getAllData();
    //         return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
    //         }
    //     catch (Exception e) {
    //            e.printStackTrace();
    //            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.OK);
    //         }        
    // }

    @GetMapping("/customer/{biodataId}")
    public ResponseEntity<List<Map<String, Object>>> getAllCustomer(@PathVariable("biodataId") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.getAllData(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/customer/{biodataId}/{id}") 
    public ResponseEntity<?> getCustomerById(@PathVariable("biodataId") Long biodataId, @PathVariable("id") Long id) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.getDataById(biodataId,id);
            if(customer !=null) {
                return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/customerbyname/{id}/{name}")
    public ResponseEntity<List<Map<String, Object>>> getCustomerByName(
        @PathVariable("id") Long id,
        @PathVariable("name") String name
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.getCustomerByName(id, name);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);   
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/customeragesortasc/{id}")
    public ResponseEntity<List<Map<String, Object>>> sortCustomerByAgeAsc(@PathVariable("id") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.sortByAgeAsc(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/customernamesortasc/{id}")
    public ResponseEntity<List<Map<String, Object>>> sortCustomerByNameAsc(@PathVariable("id") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.sortByNameAsc(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/customeragesortdesc/{id}")
    public ResponseEntity<List<Map<String, Object>>> sortCustomerByAgeDsc(@PathVariable("id") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.sortByAgeDesc(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/customernamesortdesc/{id}")
    public ResponseEntity<List<Map<String, Object>>> sortCustomerByNameDsc(@PathVariable("id") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.sortByNameDesc(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> insertCustomer(@RequestBody Customer customer) {
        try {      
            // customer.setCreatedBy((long) 1);
            this.customerRepo.save(customer);
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> editBloodType(
        @RequestBody Customer customer,
        @PathVariable("id") Long id
    ) {
        try {
            // customer.setModifiedBy((long) 1);
            customer.setId(id);
            this.customerRepo.save(customer);
            return new ResponseEntity<Customer>(customer,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<Customer> patchCustomer(
        @RequestBody Customer customer,
        @PathVariable("id") Long id
    ) {
        try {
            customer.setId(id);
            this.customerRepo.save(customer);
            return new ResponseEntity<Customer>(customer,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getchatdata/{id}")
    public ResponseEntity<List<Map<String, Object>>> getChatByCustId(
        @PathVariable("id") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.getChat(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);   
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getappointmentdata/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAppointmentByCustId(
        @PathVariable("id") Long id
    ) {
        try {
            List<Map<String, Object>> customer = this.customerRepo.getAppointment(id);
            return new ResponseEntity<List<Map<String, Object>>>(customer, HttpStatus.OK);   
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    
}
