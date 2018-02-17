package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StrategyServiceImpl implements StrategyService {
    private static final Logger log = LoggerFactory.getLogger(StrategyServiceImpl.class);
    @Autowired
    public IDao dao;

    @Override
    public Map<String, String> choiceavgStock(String code,String days) {
        Map<String, String> stockmap = new HashMap<>();
        try {
            String sqlavg = "select closeprice,vol,tn.stockname from tb_stock_history th,tb_stock_name tn  where th.stockcode=tn.stockid and stockcode=? order by datestr DESC limit ?";
            List<Map<String, Object>> listprice = dao.executeQuery(sqlavg,new Object[]{code,days});
            if (listprice == null || listprice.isEmpty()||listprice.size()<Integer.parseInt(days)) {
                return new HashMap<>();
            }
            double vollast;
            double volbeforelast;
            double trend = 0;  //判断股票趋势方向
            String stockname = "";
            double avgprice;
            double currprice = 0.0;
            if (listprice != null && listprice.size() > 2) {
                vollast = Double.parseDouble(listprice.get(0).get("vol") + "");
                volbeforelast = Double.parseDouble(listprice.get(1).get("vol") + "");
                trend = (vollast / volbeforelast);
                stockname = listprice.get(0).get("stockname") + "";
                currprice = ((BigDecimal)listprice.get(0).get("closeprice")).doubleValue();
            }

            final BigDecimal[] total = {new BigDecimal(0)};
            total[0].setScale(2);
            listprice.forEach((Map<String, Object> e) -> {
                total[0] = total[0].add(new BigDecimal(e.get("closeprice") + ""));
            });
            BigDecimal biglistsize = new BigDecimal(listprice.size());

            log.info("当前"+currprice+"："+stockname + ":"+days+"avgPrice: " + total[0].divide(biglistsize, 2, BigDecimal.ROUND_DOWN));
            avgprice = total[0].divide(biglistsize, 2, BigDecimal.ROUND_DOWN).doubleValue();


            //判断成交量比前一日放量才获取日均线
            if (trend > 0&&currprice>avgprice) {
                stockmap.put(code, avgprice+"");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return stockmap;
        }
    }
}
