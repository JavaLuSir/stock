package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.BasicDataService;
import com.luxinx.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class BasicDataServiceImpl implements BasicDataService {

    private static final Logger log = LoggerFactory.getLogger(DisplayServiceImpl.class);

    @Autowired
    public IDao dao;

    @Override
    public BigDecimal getDayAvgPrice(String code, int days) {

        String sql = "select sum(closeprice)/" + days + " as price from " +
                "(select closeprice from tb_stock_history where stockcode='" + code + "'" +
                " order by datestr desc limit 0," + days + ") t";
        log.info(sql);
        List<Map<String, Object>> price = dao.executeQuery(sql);

        BigDecimal decimalprice = new BigDecimal(0.0);

        if (price != null && !price.isEmpty()) {
            double doubleprice = Double.parseDouble(price.get(0).get("price") + "");
            decimalprice = decimalprice.add(new BigDecimal(doubleprice));
            decimalprice = decimalprice.setScale(2, BigDecimal.ROUND_DOWN);

        }

        return decimalprice;
    }

    @Override
    public void updateTodayStockPrice(String code) {


    }

    @Override
    public void updateAllStockName() {

        long start = System.currentTimeMillis();

        Set<String> stockcode = new HashSet<>();
        //生成上交所所有代码
        for (int i = 600001; i < 604000; i++) {
            stockcode.add("sh" + i);
        }
        //加入深交所大盘
        stockcode.add("sz399001");
        //生成深交所所有代码
        for (int i = 1; i < 3000; i++) {
            String code = i + "";
            for (int j = 0; j < 5; j++) {
                if (code.length() == 6) {
                    break;
                }
                code = "0" + code;
            }
            stockcode.add("sz" + code);
        }

        Set<Map<String, String>> stockresult = requestStock(stockcode);
        stockresult.forEach(m->{
            m.forEach((stcode,stname)->{
                try {
                    dao.executeUpdate("insert into tb_stock_name values(?,?)",new Object[]{stcode,stname});
                } catch (Exception e) {
                    dao.executeUpdate("update tb_stock_name SET stockname=? where stockid=?",new Object[]{stname,stcode});
                }
            });
        });
        long end = System.currentTimeMillis();
        log.info("get sh stock spend time:" + (end - start) + "ms");
    }

    /**
     * request http://hq.sinajs.cn url and get stock and name put them to a set
     *
     * @param stockcodes http://hq.sinajs.cn/list=sh or list=sz ...
     */
    private Set<Map<String, String>> requestStock(Set<String> stockcodes) {
        Set<Map<String, String>> stockmap = new HashSet<>();

        stockcodes.forEach(code -> {
            String url = "http://hq.sinajs.cn/list="+code.toString();
            Map<String, String> stockobj = null;
            try {
                stockobj = HttpUtil.dealResponse(HttpUtil.doGet(url), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stockmap.add(stockobj);
        });
        return stockmap;
    }
}
