package com.xa.filteringtest2_api.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xa.filteringtest2_api.models.ResetPassword;
import com.xa.filteringtest2_api.repositories.ResetPasswordRepository;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordRepository resetPasswordRepo;

    @PostMapping("/saveresetpassword")
    public ResponseEntity<ResetPassword> saveResetPassword(@RequestBody ResetPassword resetPassword) {
        try {
            resetPassword.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            resetPassword.setResetFor("Lupa Password");
            String pass = resetPassword.getNewPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            resetPassword.setNewPassword(bytesToHex(encodehash));
            resetPassword.setOldPassword(resetPassword.getOldPassword());
            this.resetPasswordRepo.save(resetPassword);
            return new ResponseEntity<ResetPassword>(resetPassword, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResetPassword>(HttpStatus.NO_CONTENT);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResetPassword> insertResetPassword(@RequestBody ResetPassword resetPass) {
        try {
            // biodata.setCreatedBy((long) 1);
            this.resetPasswordRepo.save(resetPass);
            return new ResponseEntity<ResetPassword>(resetPass, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResetPassword>(HttpStatus.NO_CONTENT);
        }
    }

}
