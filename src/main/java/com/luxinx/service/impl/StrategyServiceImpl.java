package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StrategyServiceImpl implements StrategyService {
    private static final Logger logger = LoggerFactory.getLogger(StrategyServiceImpl.class);
    @Autowired
    public IDao dao;

    @Override
    public Map<String, String> choice25avgStock(String code) {
        String sql25avg = "select closeprice,vol,tn.stockname from tb_stock_history th,tb_stock_name tn  where th.stockcode=tn.stockid and stockcode='" + code + "' order by datestr DESC limit 25";
        List<Map<String, Object>> listprice = dao.executeQuery(sql25avg);
        if (listprice == null || listprice.isEmpty()) {
            return new HashMap<>();
        }
        double vollast = 0;
        double volbeforelast = 0;
        double trend = 0;  //判断股票趋势方向
        String stockname = "";
        if (listprice != null && listprice.size() > 2) {
            vollast = Double.parseDouble(listprice.get(0).get("vol") + "");
            volbeforelast = Double.parseDouble(listprice.get(1).get("vol") + "");
            trend = (vollast / volbeforelast);
            stockname = listprice.get(0).get("stockname") + "";
        }

        final BigDecimal[] total = {new BigDecimal(0)};
        total[0].setScale(2);
        listprice.forEach((Map<String, Object> e) -> {
            total[0] = total[0].add(new BigDecimal(e.get("closeprice") + ""));
        });
        logger.info(stockname + ":25avgPrice: " + total[0].divide(new BigDecimal(listprice.size())));

        Map<String, String> result = new HashMap<>();
        //判断成交量比前一日放量才获取25日均线
        if (trend > 0) {
            //获取一只股票25日平均值
            String price25avg = total[0].divide(new BigDecimal(listprice.size())).doubleValue() + "";
            result.put(code, price25avg);
            String deletesql = "delete from tb_stock_focus where stockcode=" + code;
            dao.execute(deletesql);
            String insertfocus = "insert into tb_stock_focus (stockcode,stockname,destprice,updown,issend,datecreated)values('" + code + "','" + stockname + "','" + price25avg + "',1,0,NOW())";
            dao.execute(insertfocus);
            logger.info(insertfocus);
        }
        return result;
    }
}
