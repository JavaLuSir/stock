package com.luxinx.db.impl;

import com.luxinx.db.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IDaoImpl implements IDao {

    private final JdbcTemplate jdbcTemplateObject;
    @Autowired
    public IDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public List<Map<String, Object>> executeQuery(String sql) {

        return  jdbcTemplateObject.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> executeQeuery(String sql, Map<String, Object> param) {
        return null;
    }

    @Override
    public void execute(String sql) {

        jdbcTemplateObject.execute(sql);
    }

    @Override
    public void execute(String sql, Map<String, Object> param) {

    }
}
