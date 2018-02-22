package com.luxinx.task;

import com.luxinx.db.IDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JobTaskTest {

    private static Logger log = LoggerFactory.getLogger(JobTaskTest.class);

    @Autowired
    private JobTask jobTask;
    @Autowired
    private IDao dao;

    @Before
    public void init() {
        List<Map<String, Object>> result = dao.executeQuery("select * from tb_stock_name");
        result.forEach(e -> {
            Stock.STOCK_CODE_ALL.put(e.get("stockid") + "", e.get("stockname") + "");

        });

    }

    @Test
    public void saveStockCodeToDB() {
        long start = System.currentTimeMillis();
        jobTask.saveStockCodeToDB();
        long end = System.currentTimeMillis();
        log.info((end - start) + "ms");
    }

    @Test
    public void historyDailyPrice() {

        long start = System.currentTimeMillis();
        jobTask.historyDailyPrice();
        long end = System.currentTimeMillis();
        log.info((end - start) + "ms");

    }

    @Test
    public void choiceavgStock() {

        long start = System.currentTimeMillis();
        jobTask.choiceavgStock();
        long end = System.currentTimeMillis();
        log.info((end - start) + "ms");

    }
}