package com.luxinx.controller;

import com.luxinx.service.BasicDataService;
import com.luxinx.service.DisplayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DisplayControllerTest {

    private static Logger log = LoggerFactory.getLogger(DisplayControllerTest.class);
    @Autowired
    public BasicDataService basicDataService;
    @Autowired
    public DisplayService displayService;

    @Test
    public void dayprice() {
        BigDecimal price = basicDataService.getDayAvgPrice("002258", 25);

        log.info("dayprice :"+price.doubleValue()+"");
    }
    @Test
    public void display(){
        List<Map<String, Object>> result = displayService.displayFocusStock();
        log.info(result.toString());
    }
}