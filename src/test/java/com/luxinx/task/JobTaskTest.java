package com.luxinx.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JobTaskTest {

    private static Logger log = LoggerFactory.getLogger(JobTaskTest.class);

    @Autowired
    private JobTask jobTask;
    @Test
    public void saveStockCodeToDB() {
    }

    @Test
    public void historyDailyPrice() {
    }

    @Test
    public void choiceavgStock() {
       // jobTask.choiceavgStock();
    }
}