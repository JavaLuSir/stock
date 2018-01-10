package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DisplayServiceImpl implements DisplayService {
    @Autowired
    public IDao dao;
    @Override
    public List<Map<String, Object>> getYearAvgPrice(String stockparam) {
        String query = "";
        if(stockparam!=null&&!"".equals(stockparam)){
            if(stockparam.startsWith("0")||stockparam.startsWith("6")){
                query = "and stockcode like '"+stockparam+"%'";
            }else{
                query = "and stockname like '"+stockparam+"%'";
            }
        }

        String sql = "select t.stockcode ,t.currprice/t.low cl,t.currprice ,t.avgprice ,n.stockname stockname FROM tb_stock_lowest t,tb_stock_name n where t.stockcode=n.stockid and t.currprice/t.low > 0 "+query+" ORDER BY t.currprice/t.low ASC  limit 0,200";
        return dao.executeQuery(sql);
    }
}
