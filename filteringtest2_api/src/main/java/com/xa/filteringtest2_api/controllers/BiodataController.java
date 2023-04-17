package com.xa.filteringtest2_api.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xa.filteringtest2_api.models.Biodata;
import com.xa.filteringtest2_api.repositories.BiodataRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BiodataController {
    
    @Autowired
    private BiodataRepository biodataRepo;

    @GetMapping("/biodata")
    public ResponseEntity<List<Biodata>> getAllBiodata() {
        try {
            
            List<Biodata> biodata = this.biodataRepo.findAll();
            return new ResponseEntity<List<Biodata>>(biodata, HttpStatus.OK);
            }
        catch (Exception e) {
               e.printStackTrace();
               return new ResponseEntity<List<Biodata>>(HttpStatus.OK);
            }        
    }

    @GetMapping("/biodata/{id}") 
    public ResponseEntity<?> getBiodataById(@PathVariable("id") Long id) {
        try {
            Biodata biodata = this.biodataRepo.findById(id).orElse(null);
            if(biodata !=null) {
                return new ResponseEntity<Biodata>(biodata, HttpStatus.OK); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Biodata not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Biodata>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/biodata")
    public ResponseEntity<Biodata> insertBiodata(@RequestBody Biodata biodata) {
        try {
            // biodata.setCreatedBy((long) 1);
            this.biodataRepo.save(biodata);
            return new ResponseEntity<Biodata>(biodata, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Biodata>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/biodata/{id}")
    public ResponseEntity<Biodata> editBiodata(
        @RequestBody Biodata biodata,
        @PathVariable("id") Long id
    ) {
        try {
            // biodata.setModifiedBy((Long) 1);
            // biodata.setModifiedBy((long) 1);
            biodata.setId(id);
            this.biodataRepo.save(biodata);
            return new ResponseEntity<Biodata>(biodata,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Biodata>(HttpStatus.NO_CONTENT);
        }
    }

    private static String UPLOADED_FOLDER = "C://Users//Jiddan//Pictures//";

    @PatchMapping("/upload/{id}")
        public ResponseEntity<Biodata> uploadFile(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file, @RequestBody Biodata biodata) {
            try {
                biodata.setId(id);
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER+file.getOriginalFilename());
                Files.write(path, bytes);
                biodata.setImagePath(UPLOADED_FOLDER+file.getOriginalFilename());
                return new ResponseEntity<Biodata>(biodata,HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<Biodata>(HttpStatus.NO_CONTENT);
            }
        }


    
}
