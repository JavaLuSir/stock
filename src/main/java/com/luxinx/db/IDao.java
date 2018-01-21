package com.luxinx.db;

import java.util.List;
import java.util.Map;

public interface IDao {

   List<Map<String,Object>> executeQuery(String sql);

   List<Map<String,Object>>  executeQeuery(String sql,Map<String,Object> param);

   void execute(String sql);

   void execute(String sql,Map<String,Object> param);

}
