package com.demco.metrology_backend.repository;

import com.demco.metrology_backend.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> getAllSite();
    Site getSiteById(@Param("id")Long id);

  //  @Query("SELECT s FROM Site s LEFT JOIN FETCH s.reports r WHERE r.createdAt = (SELECT MAX(r2.createdAt) FROM ParmMesure r2 WHERE r2.site = s)")
  //  List<Site> findAllWithLatestReporting();
}
