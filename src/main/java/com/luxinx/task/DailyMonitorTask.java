package com.luxinx.task;

import com.luxinx.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyMonitorTask {
    private static final Logger log = LoggerFactory.getLogger(DailyMonitorTask.class);
    @Autowired
    private MonitorService monitorService;

    //@Scheduled(cron = "* 30-59 9 * * 1-5")
    @Scheduled(cron = "* 30-59 9 * * 1-5")
    public void monitorFocusPriceams() {
        log.info("[monitorFocusPriceams]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* 0-30 11 * * 1-5")
    public void monitorFocusPriceamz() {
        log.info("[monitorFocusPriceamz]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* * 10,13-15 * * 1-5")
    public void monitorFocusPricepm() {
        log.info("[monitorFocusPricepm]");
        monitorService.monitorDailyPrice();
    }
}
