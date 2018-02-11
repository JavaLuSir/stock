package com.luxinx.db;

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
public class IDaoTest {


    private static final Logger log = LoggerFactory.getLogger(IDaoTest.class);
    @Autowired
    private IDao dao;

    @Test
    public void executeQuery() {
        String sql="select * from tb_stock_name limit 10";
        List<Map<String, Object>> result = dao.executeQuery(sql);
        log.info(result.toString());
    }

    @Test
    public void executeQueryWithParams() {
        String sql="select * from tb_stock_name where stockid=?";
        List<Map<String, Object>> result = dao.executeQuery(sql,new Object[]{"601866"});
        log.info(result.toString());
    }

    @Test
    public void execute() {
        String sql="delete from tb_lock where id='1'";
        dao.execute(sql);
    }

    @Test
    public void executeUpdate() {

        String sql="insert into tb_lock values(?,?,?)";
        dao.executeUpdate(sql,new Object[]{"2","3","2017-12-1"});
    }
}