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

import com.xa.filteringtest2_api.models.BloodType;
import com.xa.filteringtest2_api.repositories.BloodTypeRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BloodTypeController {

    @Autowired
    private BloodTypeRepository bloodTypeRepo;

    @GetMapping("/bloodtype")
    public ResponseEntity< List<Map<String, Object>>> getAllBlood() {
        try {
            
            List<Map<String, Object>> bloodType = this.bloodTypeRepo.getAllBloodData();
            return new ResponseEntity< List<Map<String, Object>>>(bloodType, HttpStatus.OK);
            }
        catch (Exception e) {
               e.printStackTrace();
               return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.OK);
            }        
    }

    @GetMapping("/bloodtype/{id}") 
    public ResponseEntity<?> getBloodTypeById(@PathVariable("id") Long id) {
        try {
            BloodType bloodType = this.bloodTypeRepo.findById(id).orElse(null);
            if(bloodType !=null) {
                return new ResponseEntity<BloodType>(bloodType, HttpStatus.OK); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blood type not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<BloodType>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/bloodtypebycode/{code}")
    public ResponseEntity<List<Map<String, Object>>> getBloodTypeByCode(
        @PathVariable("code") String code
    ) {
        try {
            List<Map<String, Object>> bloodType = this.bloodTypeRepo.getBloodTypeByCode(code);
            return new ResponseEntity<List<Map<String, Object>>>(bloodType, HttpStatus.OK);   
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/validationcode/{code}")
    public ResponseEntity<List<Map<String, Object>>> getValidtionBloodCode(
        @PathVariable("code") String code
    ) {
        try {
            List<Map<String, Object>> bloodType = this.bloodTypeRepo.getValidationCode(code);
            return new ResponseEntity<List<Map<String, Object>>>(bloodType, HttpStatus.OK);   
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/bloodtype")
    public ResponseEntity<BloodType> insertBloodType(@RequestBody BloodType bloodType) {
        try {
            // bloodType.setCreatedBy((long) 1);
            this.bloodTypeRepo.save(bloodType);
            return new ResponseEntity<BloodType>(bloodType, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<BloodType>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/bloodtype/{id}")
    public ResponseEntity<BloodType> editBloodType(
        @RequestBody BloodType bloodType,
        @PathVariable("id") Long id
    ) {
        try {
            // bloodType.setModifiedBy((long) 1);
            bloodType.setId(id);
            this.bloodTypeRepo.save(bloodType);
            return new ResponseEntity<BloodType>(bloodType,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<BloodType>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/bloodtype/{id}")
    public ResponseEntity<BloodType> patchBloodType(
        @RequestBody BloodType bloodType,
        @PathVariable("id") Long id
    ) {
        try {
            bloodType.setId(id);
            this.bloodTypeRepo.save(bloodType);
            return new ResponseEntity<BloodType>(bloodType,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<BloodType>(HttpStatus.NO_CONTENT);
        }
    }


    // @DeleteMapping("/bloodtype/{id}")
    // public ResponseEntity<?> deleteBloodType(@PathVariable("id") Long id) {
    //     try {
    //         BloodType bloodType = this.bloodTypeRepo.findById(id).orElse(null);
    //         if(bloodType !=null) {
    //             this.bloodTypeRepo.deleteById(id);
    //             return ResponseEntity.status(HttpStatus.OK).body("Blood type deleted!"); 
    //         } 
    //         else {
    //                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR). body("Deleted failed!");
    //             }
    //     } 
    //     catch (Exception e) {
    //       return new ResponseEntity<BloodType>(HttpStatus.NO_CONTENT);
    //     }
    // }

}
