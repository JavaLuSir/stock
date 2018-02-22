package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.BasicDataService;
import com.luxinx.service.DisplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class DisplayServiceImpl implements DisplayService {

    private static final Logger logger = LoggerFactory.getLogger(DisplayServiceImpl.class);

    @Autowired
    public IDao dao;

    @Override
    public List<Map<String, Object>> displayFocusStock(String param) {
        if(StringUtils.isEmpty(param)){
            return dao.executeQuery("select * from tb_stock_focus");
        }
        String sql = "select * from tb_stock_focus where stockcode like ? or stockname like ?";
        param=param+"%";
        return dao.executeQuery(sql,new Object[]{param,param});
    }
}
