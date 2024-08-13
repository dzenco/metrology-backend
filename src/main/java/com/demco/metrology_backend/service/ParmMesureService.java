package com.demco.metrology_backend.service;

import com.demco.metrology_backend.config.ResponseConfig;
import com.demco.metrology_backend.entity.ParmMesure;
import com.demco.metrology_backend.entity.Site;
import com.demco.metrology_backend.repository.ParmMesureRepository;
import com.demco.metrology_backend.repository.SiteRepository;
import com.demco.metrology_backend.security.JwtFilter;
import com.demco.metrology_backend.security.JwtUtil;
import com.demco.metrology_backend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class ParmMesureService {

    @Autowired
    ParmMesureRepository parmMesureRepository;

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    JwtUtil jwtUtil;


    private boolean validateParmMesureMap(Map<String, String> requestMap, Boolean validateId) {

        if (requestMap.containsKey("dateMesure") && requestMap.containsKey("temperature") && requestMap.containsKey("pression") &&
                requestMap.containsKey("debit") && requestMap.containsKey("site")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }

        }
        return false;
    }


    private ParmMesure getParmMesureFromMap(Map<String, String> requestMap, boolean isUpadte) {

        ParmMesure parmMesure = new ParmMesure();

        if (isUpadte) {
            parmMesure.setId(Long.parseLong(requestMap.get("id")));
        }
        parmMesure.setDateMesure(requestMap.get("dateMesure"));
        parmMesure.setTemperature(Float.parseFloat(requestMap.get("temperature")));
        parmMesure.setPression(Float.parseFloat(requestMap.get("pression")));
        parmMesure.setDebit(Float.parseFloat(requestMap.get("debit")));
        Long siteId = Long.parseLong(requestMap.get("site"));
        Site site = getSiteById(siteId);
        parmMesure.setSite(site);
        parmMesure.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        parmMesure.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return parmMesure;
    }

    private Site getSiteById(Long siteId) {
        // Utiliser le repository pour récupérer le Site
        return siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found with id: " + siteId));
    }


    public ResponseEntity<String> addNewParmMesure(Map<String, String> requestMap) {
        try {

                if (validateParmMesureMap(requestMap, false)) {

                    parmMesureRepository.save(getParmMesureFromMap(requestMap, false));
                    return AppUtils.getResponseEntity(ResponseConfig.REGISTRED_SUCESS, HttpStatus.OK);
                } else {
                    return AppUtils.getResponseEntity(ResponseConfig.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<List<ParmMesure>> getAllParmMesure(String filterValue) {
        try {

            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {

                return new ResponseEntity<List<ParmMesure>>(parmMesureRepository.getAllParmMesure(), HttpStatus.OK);
            }
            return new ResponseEntity<>(parmMesureRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<String> updateParmMesure(Map<String, String> requestMap) {

        try {
            if (jwtFilter.isAdmin()) {

                if (validateParmMesureMap(requestMap, true)) {
                    Optional optional = parmMesureRepository.findById(Long.parseLong(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        parmMesureRepository.save(getParmMesureFromMap(requestMap, true));
                        return AppUtils.getResponseEntity("Mesure mise a jour avec Succes", HttpStatus.OK);
                    } else {
                        return AppUtils.getResponseEntity(" id de la mesure n'existe pas ", HttpStatus.OK);
                    }
                }
                return AppUtils.getResponseEntity(ResponseConfig.INVALID_DATA, HttpStatus.BAD_REQUEST);

            } else {
                return AppUtils.getResponseEntity(ResponseConfig.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<String> DeleteParmMesure(Long id) {
        try {
            if (jwtFilter.isAdmin()) {

                Optional optional = parmMesureRepository.findById(id);
                if (!optional.isEmpty()) {
                    parmMesureRepository.deleteById(id);
                    return AppUtils.getResponseEntity("Mesure Supprime Succes", HttpStatus.OK);
                }
                return AppUtils.getResponseEntity(" id de la mesure n'existe pas ", HttpStatus.OK);
            } else {
                return AppUtils.getResponseEntity(ResponseConfig.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public  ResponseEntity<ParmMesure>getParmMesureById(Long id){

        try {
            if (jwtFilter.isAdmin()) {

                return new ResponseEntity<>(parmMesureRepository.getParmMesureById(id), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ParmMesure(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ParmMesure(), HttpStatus.INTERNAL_SERVER_ERROR);
    }






  /* public List<ParmMesure> getLatestReportings() {
        return parmMesureRepository.findLatestReportings();
    }*/


    public List<ParmMesure> getLatestReportings() {

        try {
            if (jwtFilter.isAdmin()) {

                return parmMesureRepository.findLatestReportings();

            }
            else {
                return (List<ParmMesure>) new ResponseEntity(ResponseConfig.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (List<ParmMesure>) new ResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
