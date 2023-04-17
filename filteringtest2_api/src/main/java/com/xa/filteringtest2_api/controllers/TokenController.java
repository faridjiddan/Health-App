package com.xa.filteringtest2_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xa.filteringtest2_api.models.Token;
import com.xa.filteringtest2_api.repositories.TokenRepository;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepo;

    @PutMapping("/savetoken")
    public ResponseEntity<Token> changePassword(@RequestBody Token param) {
        try {
            Token token = tokenRepo.findById(param.getId()).get();
            token.setUserId(param.getUserId());
            token.setCreatedBy(param.getUserId());
            return new ResponseEntity<Token>(tokenRepo.save(token), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Token>(HttpStatus.BAD_REQUEST);
        }
    }

}
