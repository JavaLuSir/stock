package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StrategyServiceImpl implements StrategyService {
    private static final Logger log = LoggerFactory.getLogger(StrategyServiceImpl.class);
    @Autowired
    public IDao dao;

    @Override
    public Map<String, String> choiceavgStock(String code,String days) {
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(days)){
            log.error("code or days can't be empty");
            return new HashMap<>();
        }
        Map<String, String> stockmap = new HashMap<>();
        try {
            String sqlavg = "select closeprice,vol,tn.stockname from tb_stock_history th,tb_stock_name tn  where th.stockcode=tn.stockid and stockcode=? order by datestr DESC limit "+days;
            List<Map<String, Object>> listprice = dao.executeQuery(sqlavg,new Object[]{code});
            if (listprice == null || listprice.isEmpty()||listprice.size()<Integer.parseInt(days)) {
                return new HashMap<>();
            }

            double vollast;
            double volbeforelast;

            double pricelast;
            double pricebeforelast;

            double voltrend;  //判断股票量比趋势方向
            double pricetrend;//判断股票价格比趋势方向

            double trend = 0;
            String stockname = "";
            double avgprice;
            double currprice = 0.0;
            if (listprice != null && listprice.size() > 2) {
                vollast = Double.parseDouble(listprice.get(0).get("vol") + "");
                volbeforelast = Double.parseDouble(listprice.get(1).get("vol") + "");
                //如果大于1增量
                voltrend = (vollast / volbeforelast);

                pricelast = Double.parseDouble(listprice.get(0).get("closeprice") + "");
                pricebeforelast = Double.parseDouble(listprice.get(1).get("closeprice") + "");
                pricetrend = (pricelast/pricebeforelast);
                //量和价格都为上涨 趋势为上涨
                trend = (voltrend>1&&pricetrend>1)?1:0;

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


            //判断成交量比前一日放量并且价格上涨才获取日均线
            if (trend > 0&&currprice>avgprice) {
                stockmap.put(code, avgprice+"");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return stockmap;
        }
    }

    @Override
    public Map<String, String> saveChoicedStock(String code, String stockname,String destprice) {
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(stockname)||StringUtils.isEmpty(destprice)){
            return new HashMap<>();
        }
        String insertfocus = "insert into tb_stock_focus (stockcode,stockname,destprice,updown,issend,datecreated)values(?,?,?,1,0,NOW())";
        dao.executeUpdate(insertfocus, new Object[]{code, stockname, destprice});
        Map<String, String> smap = new HashMap<>();
        smap.put("updown", "1");
        smap.put("destprice", destprice);
        smap.put("stockcode", code);
        smap.put(code, destprice);
        return smap;
    }
}
