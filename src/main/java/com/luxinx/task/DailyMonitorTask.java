package com.luxinx.task;

import com.luxinx.service.MonitorService;
import com.luxinx.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class DailyMonitorTask {
    private static final Logger log = LoggerFactory.getLogger(DailyMonitorTask.class);
    @Autowired
    private MonitorService monitorService;

    @Scheduled(cron = "* 30-59 9 * * 1-5")
    private void monitorFocusPriceams() {
        log.info("[monitorFocusPriceams]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* 0-30 11 * * 1-5")
    private void monitorFocusPriceamz() {
        log.info("[monitorFocusPriceamz]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* * 10,13-15 * * 1-5")
    private void monitorFocusPricepm() {
        log.info("[monitorFocusPricepm]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron="0 0 10 * * 1-5")
    private void monitorEmailSend(){
        log.info("[monitorEmailSend]");
       if(!Stock.EMAIL_QUEUE.isEmpty()){
           Stock.EMAIL_QUEUE.forEach(e->{
               try {
                   MailUtil.sendMessage("javalusir@163.com",e.toString());
               } catch (MessagingException e1) {
                   e1.printStackTrace();
               }
           });
       }

    }
}
