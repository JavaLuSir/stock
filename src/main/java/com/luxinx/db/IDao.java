package com.luxinx.db;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


public interface IDao {

   List<Map<String,Object>> executeQuery(String sql);
}
