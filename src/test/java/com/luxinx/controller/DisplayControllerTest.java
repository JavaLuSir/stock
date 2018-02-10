package com.luxinx.controller;

import com.luxinx.service.BasicDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DisplayControllerTest {

    private static Logger log = LoggerFactory.getLogger(DisplayControllerTest.class);
    @Autowired
    public BasicDataService basicDataService;

    @Test
    public void dayprice() {
        BigDecimal price = basicDataService.getDayAvgPrice("002410", 250);

        log.info("dayprice :"+price.doubleValue()+"");
    }
}