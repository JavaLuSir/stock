package com.luxinx.service.impl;

import com.luxinx.service.BasicDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BasicDataServiceImplTest {

    private static Logger log = LoggerFactory.getLogger(BasicDataServiceImplTest.class);

    @Autowired
    private BasicDataService basicDataService;

    @Test
    public void getDayAvgPrice() {
        BigDecimal avgprice = basicDataService.getDayAvgPrice("000627", 25);
        log.info(avgprice.doubleValue()+"");
    }

    @Test
    public void updateTodayStockPrice() {
    }

    @Test
    public void updateAllStockName() {
        basicDataService.updateAllStockName();
    }

    @Test
    public void getDayAvgPrice1() {
    }

    @Test
    public void updateTodayStockPrice1() {
    }

    @Test
    public void updateAllStockName1() {
    }
}