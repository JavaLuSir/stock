package com.luxinx.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IDaoImpl implements IDao{

    private final JdbcTemplate jdbcTemplateObject;
    @Autowired
    public IDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }




    @Override
    public List<Map<String, Object>> executeQuery(String sql) {

        return  jdbcTemplateObject.queryForList(sql);
    }
}
