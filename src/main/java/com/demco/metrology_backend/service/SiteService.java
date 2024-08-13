package com.demco.metrology_backend.service;

import com.demco.metrology_backend.config.ResponseConfig;
import com.demco.metrology_backend.entity.ParmMesure;
import com.demco.metrology_backend.entity.Site;
import com.demco.metrology_backend.repository.ParmMesureRepository;
import com.demco.metrology_backend.repository.SiteRepository;
import com.demco.metrology_backend.security.JwtFilter;
import com.demco.metrology_backend.utils.AppUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ParmMesureRepository parmMesureRepository;

    @Autowired
    JwtFilter jwtFilter;


    private boolean validateSiteMap(Map<String, String> requestMap, Boolean validateId) {

        if (requestMap.containsKey("numSite") && requestMap.containsKey("nameSite") && requestMap.containsKey("latitude") && requestMap.containsKey("longitude"))
        { if (requestMap.containsKey("id") && validateId){
            return true;
        } else if (!validateId) {
            return true;
        }

        }
        return false;
    }


    private Site getSiteFromMap(Map<String,String> requestMap,Boolean isUpadte){

        Site site = new Site();

        if (isUpadte){
            site.setId(Long.parseLong(requestMap.get("id")));
        }
        site.setNumSite(requestMap.get("numSite"));
        site.setNameSite(requestMap.get("nameSite"));
        site.setLatitude(Double.parseDouble(requestMap.get("latitude")));
        site.setLongitude(Double.parseDouble(requestMap.get("longitude")));
        site.setHasReported(false);
        site.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        site.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return  site;

    }



    public ResponseEntity<String> addNewSite(Map<String, String> requestMap) {
        log.info("Inside addNewSite {}", requestMap);
        try {
            if (jwtFilter.isAdmin()){
                if (validateSiteMap(requestMap,false)) {

                    siteRepository.save(getSiteFromMap(requestMap,false));
                    return AppUtils.getResponseEntity(ResponseConfig.REGISTRED_SUCESS, HttpStatus.OK);
                }
                else {
                    return AppUtils.getResponseEntity(ResponseConfig.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }
            else {

                return AppUtils.getResponseEntity(ResponseConfig.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);

            }

        }catch (Exception ex){

            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public  ResponseEntity<List<Site>> getAllSite(String filterValue){
        try {

            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info(filterValue);
                return new ResponseEntity<List<Site>>(siteRepository.getAllSite(),HttpStatus.OK);
            }
            return new ResponseEntity<>(siteRepository.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateSite(Map<String, String> requestMap) {

        try {
            if (jwtFilter.isAdmin()){

                if (validateSiteMap(requestMap,true)){
                    Optional optional = siteRepository.findById(Long.parseLong(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        siteRepository.save(getSiteFromMap(requestMap,true));
                        return AppUtils.getResponseEntity("Site mise a jour avec succes",HttpStatus.OK);
                    }else {
                        return AppUtils.getResponseEntity("id du site n' exist pas",HttpStatus.OK);
                    }
                }
                return AppUtils.getResponseEntity(ResponseConfig.INVALID_DATA,HttpStatus.BAD_REQUEST);

            }else {
                return AppUtils.getResponseEntity(ResponseConfig.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return AppUtils.getResponseEntity(ResponseConfig.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }




   /* public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = siteRepository.findAllWithLatestReporting();
        if (sites.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne un statut 204 si aucun site trouvé
        }
        return ResponseEntity.ok(sites); // Retourne un statut 200 avec la liste des sites
    }*/

    public void updateSitesBasedOnMesure() {
        List<ParmMesure> latestReportings = parmMesureRepository.findLatestReportingForAllSites();

        for (ParmMesure latestMesure : latestReportings) {
            Site site = latestMesure.getSite();  // Assuming that ParmMesure has a reference to Site

            LocalDateTime lastReportedDate = latestMesure.getCreatedAt().toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            long daysBetween = ChronoUnit.DAYS.between(lastReportedDate, now);

            if (daysBetween > 7) {
                site.setHasReported(false);
            } else {
                site.setHasReported(true);
            }
            site.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            siteRepository.save(site);
        }
    }
}