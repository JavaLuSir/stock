package com.luxinx.task;

import com.luxinx.service.MonitorService;
import com.luxinx.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

/**
 * 每日监控股票价格
 */
@Component
public class DailyMonitorTask {
    private static final Logger log = LoggerFactory.getLogger(DailyMonitorTask.class);
    @Autowired
    private MonitorService monitorService;

    @Scheduled(cron = "* 30-59 9 * * MON-FRI")
    private void monitorFocusPriceams() {
        log.info("[monitorFocusPriceams]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* 0-30 11 * * MON-FRI")
    private void monitorFocusPriceamz() {
        log.info("[monitorFocusPriceamz]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron = "* * 10,13-14 * * MON-FRI")
    private void monitorFocusPricepm() {
        log.info("[monitorFocusPricepm]");
        monitorService.monitorDailyPrice();
    }

    @Scheduled(cron="0 0 10 * * MON-FRI")
    private void monitorEmailSend(){
        log.info("[monitorEmailSend]");
        StringBuffer dn = new StringBuffer();

       if(!Stock.EMAIL_QUEUE.isEmpty()){
           Stock.EMAIL_QUEUE.forEach(e->{
               dn.append(e.toString());
           });
       }
        try {
            log.info(dn.toString());
            MailUtil.sendMessage("javalusir@163.com",dn.toString());
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }

    }

}
