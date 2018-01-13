package com.luxinx.task;

import com.luxinx.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyMonitorTask {

    @Autowired
    private MonitorService monitorService;
    @Scheduled(cron = "* 30-59 9 1-5 * ?")
    public void monitorFocusPriceams(){
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* 0-30 11 1-5 * ?")
    public void monitorFocusPriceamz(){
        monitorService.monitorDailyPrice();
    }
    @Scheduled(cron = "* * 10,13-15 1-5 * ?")
    public void monitorFocusPricepm(){
        monitorService.monitorDailyPrice();
    }
}
