package com.demco.metrology_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {

    @Autowired
    private SiteService siteService;

    @Scheduled(cron = "0 10 0 * * MON")
    //@Scheduled(cron = "0 47 9 * * *")
    public void scheduledUpdate() {
        siteService.updateSitesBasedOnMesure();
    }
}
