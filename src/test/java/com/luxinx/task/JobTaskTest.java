package com.luxinx.task;

import com.luxinx.db.IDao;
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

    @Test
    public void saveStockCodeToDB() {
    }

    @Test
    public void historyDailyPrice() {
        List<Map<String, Object>> result = dao.executeQuery("select * from tb_stock_name");
        result.forEach(e->{
            Stock.STOCK_CODE_ALL.put(e.get("stockid")+"",e.get("stockname")+"");

        });
        jobTask.historyDailyPrice();
    }

    @Test
    public void choiceavgStock() {
        jobTask.choiceavgStock();
    }
}