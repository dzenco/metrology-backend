package com.demco.metrology_backend.service;


import com.demco.metrology_backend.security.CustomerUserDetailsService;
import com.demco.metrology_backend.security.JwtFilter;
import com.demco.metrology_backend.security.JwtUtil;
import com.demco.metrology_backend.config.ResponseConfig;
import com.demco.metrology_backend.entity.User;
import com.demco.metrology_backend.repository.UserRepository;
import com.demco.metrology_backend.utils.AppUtils;
import com.demco.metrology_backend.wrapper.UserWrapper;
//import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;


    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;


    private boolean validateSignUpMap(Map<String, String> requestMap) {

        if (requestMap.containsKey("firstName") && requestMap.containsKey("lastName") && requestMap.containsKey("email")
                && requestMap.containsKey("telephone") && requestMap.containsKey("password"))
        {
            return true;
        }

        else  return false;

    }


    private User getUserFromMap(Map<String,String> requestMap){


        User user = new User();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setTelephone(requestMap.get("telephone"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setStatus("flase");
        user.setRole("user");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return  user;

    }


    public ResponseEntity<String> singUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {

                User user = userRepository.findByEmailId(requestMap.get("email"));

                if (Objects.isNull(user)) {
                    userRepository.save(getUserFromMap(requestMap));
                    return AppUtils.getResponseEntity(ResponseConfig.REGISTRED_SUCESS, HttpStatus.OK);
                } else {
                    return AppUtils.getResponseEntity(ResponseConfig.EMAIL_EXIST, HttpStatus.BAD_REQUEST);
                }

            } else {

                return AppUtils.getResponseEntity(ResponseConfig.INVALID_DATA, HttpStatus.BAD_REQUEST);

            }

        }catch (Exception ex){

            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if (auth.isAuthenticated()) {

                if (customerUserDetailsService.getUserDetail() != null && customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    String token = jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(), customerUserDetailsService.getUserDetail().getRole());
                    return ResponseEntity.ok("{\"token\":\"" + token + "\",\"message\":\"" + ResponseConfig.GOOD_CREDENTIAL+ "\" }");

                } else {
                    return AppUtils.getResponseEntity(ResponseConfig.NOT_APPROUVE, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (AuthenticationException ex) {
            log.error("Authentication failed: {}", ex.getMessage());
            /* return ResponseEntity.badRequest().body("{\"message\":\"Bad Credentials.\"}");*/
            return AppUtils.getResponseEntity(ResponseConfig.BAD_CREDENTIAL, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("An error occurred during login: {}", ex.getMessage());
        }
        return AppUtils.getResponseEntity(ResponseConfig.LOGIN_ERROR, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userRepository.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){

                Optional<User> optional = userRepository.findById(Long.parseLong(requestMap.get("id")));
                if (!optional.isEmpty()){
                    userRepository.updateStatus(requestMap.get("status"),Timestamp.valueOf(LocalDateTime.now()) ,Long.parseLong(requestMap.get("id")));
                    return AppUtils.getResponseEntity(ResponseConfig.UPDATED_SUCESS,HttpStatus.OK);

                }else {
                    return AppUtils.getResponseEntity("L'identifiant de l'utilisateur n'existe pas",HttpStatus.OK);
                }
            }else {
                return AppUtils.getResponseEntity(ResponseConfig.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){

            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    public ResponseEntity<String> checkToken() {

        return AppUtils.getResponseEntity("true",HttpStatus.OK);
    }

    public ResponseEntity<String> changPassword(Map<String, String> requestMap) {
        try {
            User userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());

            if (userObj != null){
                if (passwordEncoder.matches(requestMap.get("oldPassword"), userObj.getPassword())) {
                    userObj.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userRepository.save(userObj);
                    return AppUtils.getResponseEntity(ResponseConfig.UPDATED_SUCESS,HttpStatus.OK);
                }
                return AppUtils.getResponseEntity(ResponseConfig.BAD_OLD_PASSWORD,HttpStatus.BAD_REQUEST);

            }else {
                return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception ex){

            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {

        return null;
    }


    /**  private String generateTemporaryPassword() {
        // Générer un mot de passe temporaire sécurisé, par exemple avec UUID
        return UUID.randomUUID().toString();
    }**/


}

