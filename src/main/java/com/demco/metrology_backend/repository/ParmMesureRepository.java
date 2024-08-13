package com.demco.metrology_backend.repository;

import com.demco.metrology_backend.entity.ParmMesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParmMesureRepository extends JpaRepository<ParmMesure, Long> {

    List<ParmMesure> getAllParmMesure();
    ParmMesure getParmMesureById(@Param("id") Long id);

   //@Query("SELECT p FROM ParmMesure p WHERE p.createdAt = (SELECT MAX(p2.createdAt) FROM ParmMesure p2 WHERE p2.site.id = p.site.id)")
   @Query("""
        SELECT p
        FROM ParmMesure p
        WHERE p.createdAt = (
            SELECT MAX(p2.createdAt)
            FROM ParmMesure p2
            WHERE p2.site.id = p.site.id
        )
    """)
    List<ParmMesure> findLatestReportings();


   /* @Query("SELECT p FROM ParmMesure p WHERE p.site.id = :siteId ORDER BY p.createdAt DESC")
    Optional<ParmMesure> findLatestReportingForSite(@Param("siteId") Long siteId);*/

    @Query("""
        SELECT p 
        FROM ParmMesure p 
        WHERE p.createdAt = (
            SELECT MAX(p2.createdAt) 
            FROM ParmMesure p2 
            WHERE p2.site.id = p.site.id
        )
    """)
    List<ParmMesure> findLatestReportingForAllSites();


}
