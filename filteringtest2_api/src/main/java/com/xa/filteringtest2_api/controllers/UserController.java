package com.xa.filteringtest2_api.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xa.filteringtest2_api.models.Biodata;
import com.xa.filteringtest2_api.models.Role;
import com.xa.filteringtest2_api.models.Token;
import com.xa.filteringtest2_api.models.User;
import com.xa.filteringtest2_api.repositories.BiodataRepository;
import com.xa.filteringtest2_api.repositories.RoleRepository;
import com.xa.filteringtest2_api.repositories.TokenRepository;
import com.xa.filteringtest2_api.repositories.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BiodataRepository biodataRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private TokenRepository tokenRepo;

    @PostMapping("/cekemail")
    public ResponseEntity<?> cekEmail(@RequestBody String email) {
        try {
            // Boolean emailExists = userRepo.emailExists(email);
            // if (emailExists) {
            //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email sudah digunakan");
            // } else {
                String otp = generateOtp();
                sendmail(email, otp);
                Token token = new Token();
                token.setEmail(email);
                token.setToken(otp);
                LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
                Timestamp expirationTimestamp = Timestamp.valueOf(expirationTime);
                token.setExpiredOn(expirationTimestamp);
                token.setUsedFor("Register");
                token.setCreatedOn(new Timestamp(System.currentTimeMillis()));
                this.tokenRepo.save(token);
                return ResponseEntity.status(HttpStatus.OK).body(otp);
            // }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/checkotp")
    public ResponseEntity<Token> getToken(Token token) {
        try {
            LocalDateTime timeNow = LocalDateTime.now();
            Timestamp timeNowTimestamp = Timestamp.valueOf(timeNow);
            List<Token> getToken = this.tokenRepo.findToken(token.getToken());

            if (getToken.size() == 0) {
                return new ResponseEntity<Token>(HttpStatus.NOT_FOUND);//otp salah
            }
            Token dataToken = getToken.get(0);
            if (timeNowTimestamp.compareTo(dataToken.getExpiredOn()) < 0 && dataToken.isExpired() == false) {
                dataToken.setExpired(true);
                this.tokenRepo.save(dataToken);
                return new ResponseEntity<Token>(dataToken, HttpStatus.OK);
            } else {
                dataToken.setExpired(true);
                this.tokenRepo.save(dataToken);
                return new ResponseEntity<Token>(HttpStatus.BAD_REQUEST); //otp kadaluarsa
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Token>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void sendmail(String address, String otp) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(address);
            message.setSubject("Verifikasi E-mail");
            message.setText("Masukkan kode OTP berikut : " + otp);

            javaMailSender.send(message);
        } catch (Exception e) {
        }
    }

    // @PostMapping("/cekemail")
    // public ResponseEntity<?> cekEmail(@RequestBody String email, @RequestParam(name = "OTP") String OTP){
    //     try {
    //         sendmail(email, OTP);
    //         return ResponseEntity.status(HttpStatus.OK).body("email masuk");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email ada");
            
    //     }
    // }
    
    // @Autowired
    // private JavaMailSender javamailsender;

    // public void sendmail(String address, String OTP) throws Exception{
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setTo(address);
    //     message.setSubject("Verifikasi E-mail");
    //     message.setText("Masukkan kode OTP berikut : " + otp);
    //     javaMailSender.send(message);
    // }

    public static String generateOtp() {
        String NUMBERS = "0123456789";
        int OTP_LENGTH = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int randomIndex = random.nextInt(NUMBERS.length());
            otp.append(NUMBERS.charAt(randomIndex));
        }

        return otp.toString();
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRole() {
        try {
            List<Role> role = this.roleRepo.findAll();
            return new ResponseEntity<List<Role>>(role, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Role>>(HttpStatus.NO_CONTENT);
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
    
    @GetMapping("/user")
    public ResponseEntity<List<Map<String, Object>>> getAllUser(
    ) {
        try {
            List<Map<String, Object>> user = this.userRepo.getAllData();
            return new ResponseEntity<List<Map<String, Object>>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userbyemail")
    public ResponseEntity<List<Map<String, Object>>> getAllUserEmail(
    ) {
        try {
            List<Map<String, Object>> user = this.userRepo.getAllEmail();
            return new ResponseEntity<List<Map<String, Object>>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/{id}") 
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            List<Map<String, Object>> user = this.userRepo.getDataById(id);
            if(user !=null) {
                return new ResponseEntity<List<Map<String, Object>>>(user, HttpStatus.OK); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        try {
            String pass = user.getPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            user.setPassword(bytesToHex(encodehash));
            user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            // user.setIsDeleted(false);
            user.setIsLocked(false);
            user.setLoginAttempt(0);
            this.userRepo.save(user);
            user.setCreatedBy(user.getId());
            this.userRepo.save(user);
            Biodata biodata = biodataRepo.findById(user.getBiodataId()).get();
            biodata.setCreatedBy(user.getId());
            this.biodataRepo.save(biodata);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getuserbyemail")
    public ResponseEntity<List<User>> getUserByEmail(String email) {
        try {
            List<User> user = this.userRepo.findUserByEmail(email);
            return new ResponseEntity<List<User>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> editUser(
        @RequestBody User user,
        @PathVariable("id") Long id
    ) {
        try {
            // customer.setModifiedBy((long) 1);
            user.setId(id);
            this.userRepo.save(user);
            return new ResponseEntity<User>(user,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> patchUser(
        @RequestBody User user,
        @PathVariable("id") Long id
    ) {
        try {
            user.setId(id);
            this.userRepo.save(user);
            return new ResponseEntity<User>(user,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpSession session) {
        try {
            User userData = userRepo.findByEmail(user.getEmail());
            String pass = user.getPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            user.setPassword(bytesToHex(encodehash));
            if (userData == null) {
                return new ResponseEntity<User>(userData, HttpStatus.NOT_FOUND);
            }

            // if (userData.getLoginAttempt() >= 20) {
            //     userData.setIsLocked(true);
            //     this.userRepo.save(userData);
            // }

            if (userData.getIsLocked() == true) {
                return new ResponseEntity<User>(userData, HttpStatus.FORBIDDEN);
            } else {
                Integer loginAttempt = userData.getLoginAttempt();
                if (!userData.getPassword().equals(user.getPassword())) {
                    loginAttempt += 1;
                    userData.setLastLogin(new Timestamp(System.currentTimeMillis()));
                    userData.setLoginAttempt(loginAttempt);
                    userRepo.save(userData);
                    return new ResponseEntity<User>(userData, HttpStatus.UNAUTHORIZED);
                }

                Biodata biodata = userData.getBiodata();
                // login berhasil, simpan data ke session
                session.setAttribute("userId", userData.getId());
                session.setAttribute("email", userData.getEmail());
                session.setAttribute("roleId", userData.getRoleId());
                session.setAttribute("fullname", biodata.getFullName());

                System.out.println(session.getAttribute("userId"));
                return new ResponseEntity<User>(userData, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/changepassword")
    public ResponseEntity<User> changePassword(@RequestBody User param) {
        try {
            String pass = param.getPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            param.setPassword(bytesToHex(encodehash));
            User user = userRepo.findById(param.getId()).get();
            user.setPassword(param.getPassword());
            user.setModifiedBy(param.getId());
            user.setModifiedOn(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<User>(userRepo.save(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cekemailforgot")
    public ResponseEntity<?> cekEmailForgot(String email) throws Exception {
        try {
            Boolean emailExists = userRepo.emailExists(email);
            if (!emailExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email tidak terdaftar");
            } else {
                String otp = generateOtp();
                sendmail(email, otp);
                Token token = new Token();
                token.setEmail(email);
                token.setToken(otp);
                LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
                Timestamp expirationTimestamp = Timestamp.valueOf(expirationTime);
                token.setExpiredOn(expirationTimestamp);
                token.setUsedFor("Lupa Password");
                token.setCreatedOn(new Timestamp(System.currentTimeMillis()));
                this.tokenRepo.save(token);
                return ResponseEntity.status(HttpStatus.OK).body(otp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    // Untuk mendapatkan siapa yang login terakhir kali

    @GetMapping("/userlastlogin") 
    public ResponseEntity<List<Map<String, Object>>> getUserLastLogin() {
        try {
            List<Map<String, Object>> user = this.userRepo.getLastLogin();
            return new ResponseEntity<List<Map<String, Object>>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    // Untuk mendapatkan user-customer last login
    @GetMapping("/custlastlogin") 
    public ResponseEntity<List<Map<String, Object>>> getCustLastLogin() {
        try {
            List<Map<String, Object>> user = this.userRepo.getUserCustLastLogin();
            return new ResponseEntity<List<Map<String, Object>>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }


    @PostMapping("/ceklogin")
    public ResponseEntity<List<User>> login(@RequestBody User user) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
            List<User> userData = userRepo.login(user.getEmail(), bytesToHex(encodedhash));
            return new ResponseEntity<List<User>>(userData, HttpStatus.OK);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
    }

    // @GetMapping("/user/{id}")
    // public ModelAndView getuser(@PathVariable("id") Long id) {
    //     ModelAndView view = new ModelAndView("/user/showimage");
    //     Optional<User> getuser = this.userRepo.findById(id);
    //     view.addObject("getuser", getuser);
    //     return view;
    // }


    @GetMapping("/extractyear/{id}") 
    public ResponseEntity<List<Map<String, Object>>> getJoinedYear(@PathVariable("id") Long id) {
        try {
            List<Map<String, Object>> user = this.userRepo.getYear(id);
            return new ResponseEntity<List<Map<String, Object>>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NO_CONTENT);
        }
    }

    
}
