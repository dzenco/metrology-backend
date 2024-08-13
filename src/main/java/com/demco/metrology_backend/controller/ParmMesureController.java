package com.demco.metrology_backend.controller;

import com.demco.metrology_backend.config.ResponseConfig;
import com.demco.metrology_backend.entity.ParmMesure;
import com.demco.metrology_backend.repository.ParmMesureRepository;
import com.demco.metrology_backend.service.ParmMesureService;
import com.demco.metrology_backend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequestMapping("/api/mesure")
@RestController
public class ParmMesureController {

    @Autowired
    ParmMesureService parmMesureService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewParmMesure(@RequestBody(required = true) Map<String, String> requestMap){

        try{
            return  parmMesureService.addNewParmMesure(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/getall")
    ResponseEntity<List<ParmMesure>> getAllParmMesure(@RequestParam(required = false) String filterValue){
        try {
            return parmMesureService.getAllParmMesure(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateParmMesure(@RequestBody(required = true) Map<String, String> requestMap){

        try{
            return parmMesureService.updateParmMesure(requestMap);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/delete/{id}")
    public  ResponseEntity<String>DeleteParmMesure(@PathVariable Long id){

        try {
            return parmMesureService.DeleteParmMesure(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/getById/{id}")
    public  ResponseEntity<ParmMesure>getParmMesureBytId(@PathVariable Long id){

        try {
            return parmMesureService.getParmMesureById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ParmMesure(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/getlasts")
    public ResponseEntity<List<ParmMesure>> getLatestReportings() {
        List<ParmMesure> reportings = parmMesureService.getLatestReportings();
        return ResponseEntity.ok(reportings);
    }

 /*   public ResponseEntity<List<ParmMesure>> getLatestReportings() {
        List<ParmMesure> reportings =parmMesureService.getLatestReportings();
        return ResponseEntity.ok(reportings);
    }*/

}
