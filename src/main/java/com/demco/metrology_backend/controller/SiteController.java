package com.demco.metrology_backend.controller;

import com.demco.metrology_backend.config.ResponseConfig;
import com.demco.metrology_backend.entity.Site;
import com.demco.metrology_backend.service.SiteService;
import com.demco.metrology_backend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    SiteService siteService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewSite(@RequestBody(required = true) Map<String, String> requestMap) {

        try {
            return siteService.addNewSite(requestMap);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/getall")
    ResponseEntity<List<Site>> getAllSite(@RequestParam(required = false) String filterValue) {
        try {
            return siteService.getAllSite(filterValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateSite(@RequestBody(required = true) Map<String, String> requestMap) {

        try {
            return siteService.updateSite(requestMap);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

   /* @GetMapping("/getalls")
    public ResponseEntity<List<Site>> getAllSites() {
        return siteService.getAllSites(); // Appelle la méthode modifiée du service
    }*/

}
