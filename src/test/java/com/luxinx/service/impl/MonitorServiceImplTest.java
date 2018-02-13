package com.luxinx.service.impl;

import com.luxinx.service.MonitorService;
import com.luxinx.task.DailyMonitorTask;
import com.luxinx.task.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MonitorServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(DailyMonitorTask.class);
    @Autowired
    private MonitorService monitorService;

    @Test
    public void monitorDailyPrice() {

        monitorService.monitorDailyPrice();
    }
}