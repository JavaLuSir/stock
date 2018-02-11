package com.luxinx.db;

import java.util.List;
import java.util.Map;

/**
 * 数据库持久层交互
 */
public interface IDao {

   List<Map<String,Object>> executeQuery(String sql);

   List<Map<String,Object>>  executeQuery(String sql,Object[] params);

   void execute(String sql);

   void executeUpdate(String sql,Object[] param);

}
