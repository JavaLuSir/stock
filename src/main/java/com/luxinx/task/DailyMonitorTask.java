package com.luxinx.task;

import com.luxinx.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyMonitorTask {

    @Autowired
    private MonitorService monitorService;
    @Scheduled(cron = "* * * 1-5 * ?")
    public void monitorFocusPrice(){
        monitorService.monitorDailyPrice();
    }
}
