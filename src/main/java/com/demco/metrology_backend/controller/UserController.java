package com.demco.metrology_backend.controller;

import com.demco.metrology_backend.config.ResponseConfig;
import com.demco.metrology_backend.service.UserService;
import com.demco.metrology_backend.utils.AppUtils;
import com.demco.metrology_backend.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
public class UserController  {

    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap){

        try{
            return  userService.singUp(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true)Map<String, String> requestMap){

        try{
            return  userService.login(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserWrapper>> getAllUser(){
        try {
            return userService.getAllUser();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody(required = true)Map<String, String> requestMap){

        try{
            return  userService.update(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(){

        try{
            return  userService.checkToken();
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody(required = true)Map<String, String> requestMap){

        try{
            return  userService.changPassword(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody(required = true)Map<String, String> requestMap){

        try{
            return  userService.forgotPassword(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}