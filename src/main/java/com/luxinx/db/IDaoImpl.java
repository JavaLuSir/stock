package com.luxinx.db;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Resource
public class IDaoImpl implements IDao{
    @Resource
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> executeQuery(String sql) {

        return  jdbcTemplateObject.queryForList(sql);
    }
}
