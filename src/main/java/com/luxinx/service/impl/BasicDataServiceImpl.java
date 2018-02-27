package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.BasicDataService;
import com.luxinx.task.Stock;
import com.luxinx.util.DateUtil;
import com.luxinx.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
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
    public void updateTodayStockPrice() {
        updateTodayStockPrice(DateUtil.getThisYear(), true);
    }

    @Override
    public void updateTodayStockPrice(String year, boolean istoday) {
        log.info("year:" + year);
        if (StringUtils.isEmpty(year)) {
            return;
        }
        if (Stock.STOCK_CODE_ALL.isEmpty()) {
            List<Map<String, Object>> liststock = dao.executeQuery("select * from tb_stock_name");

            liststock.forEach((Map<String,Object> m)->{
               String stockcode =  m.get("stockid")+"";
               String stockname =  m.get("stockname")+"";
               Stock.STOCK_CODE_ALL.put(stockcode,stockname);
            });
            if(Stock.STOCK_CODE_ALL.isEmpty()){
                return;
            }
        }
        Stock.STOCK_CODE_ALL.forEach((code, v) -> {
            String precode = "";
            precode = getString(code);

            String result = "";
            String url;
            try {
                url = "http://data.gtimg.cn/flashdata/hushen/daily/" + year + "/" + precode + code + ".js";
                log.info("requrl:" + url);
                result = HttpUtil.doGet(url);
            } catch (Exception e) {
                log.info("Http Exception...");
            }
            if (!"".equals(result)) {
                String[] resultarry = result.split("\\\\n\\\\");
                if (istoday) {

                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    String[] pricearry = resultarry[resultarry.length - 2].split(" ");
                    String openprice = pricearry[1];
                    String closeprice = pricearry[2];
                    String highprice = pricearry[3];
                    String lowprice = pricearry[4];
                    String volumn = pricearry[5];
                    String datestr = pricearry[0].replaceAll("\\n", "");
                    if ("sh000001".equals(precode + code)) {
                        code = precode + code;
                    }
                    String sql = "insert into tb_stock_history values(?,?,?,?,?,?,?,?)";
                    log.info(sql);
                    try {
                        dao.executeUpdate(sql, new Object[]{id, code, openprice, closeprice, highprice, lowprice, volumn, datestr});
                    } catch (Exception e) {
                        log.error("SQLException..."+datestr);
                    }

                } else {

                    for (int i = 1; i < resultarry.length - 1; i++) {
                        String id = UUID.randomUUID().toString().replaceAll("-", "");
                        String[] pricearry = resultarry[i].split(" ");
                        String openprice = pricearry[1];
                        String closeprice = pricearry[2];
                        String highprice = pricearry[3];
                        String lowprice = pricearry[4];
                        String volumn = pricearry[5];
                        String datestr = pricearry[0].replaceAll("\\n", "");
                        if ("sh000001".equals(precode + code)) {
                            code = precode + code;
                        }
                        String sql = "insert into tb_stock_history values(?,?,?,?,?,?,?,?)";
                        log.info(sql);
                        try {
                            dao.executeUpdate(sql, new Object[]{id, code, openprice, closeprice, highprice, lowprice, volumn, datestr});
                        } catch (Exception e) {
                            log.error("SQLException..."+datestr);
                        }
                    }

                }
            }
        });
    }

    static String getString(@NotNull String code) {
        String precode;
        if (code.startsWith("0")|| code.startsWith("3")) {
            precode = "sz";
        } else if(code.startsWith("6")){
            precode = "sh";
        } else{
            precode = "";
        }
        return precode;
    }

    @Override
    @Transactional
    public void updateAllStockName() {

        long start = System.currentTimeMillis();

        Set<String> stockcode = new HashSet<>();
        //生成上交所所有代码
        for (int i = 600001; i < 604000; i++) {
            stockcode.add("sh" + i);
        }
        //加入上交所大盘
        stockcode.add("sh000001");
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
        Stock.STOCK_CODE_ALL.clear();
        stockresult.forEach(m -> {
            m.forEach((stcode, stname) -> {
                try {
                    dao.executeUpdate("insert into tb_stock_name values(?,?)", new Object[]{stcode, stname});
                } catch (Exception e) {
                    dao.executeUpdate("update tb_stock_name set stockname=? where stockid=?", new Object[]{stname,stockcode});
                    log.info("SQL Exception...");
                } finally {
                    //清空后放入股票代码
                    Stock.STOCK_CODE_ALL.put(stcode, stname);
                }
            });
        });
        long end = System.currentTimeMillis();
        log.info("get sh stock spend time:" + (end - start) + "ms");
    }

    @Override
    public List<Map<String,Object>> getAllStockCodeName() {
        return dao.executeQuery("select * from tb_stock_name");
    }

    /**
     * request http://hq.sinajs.cn url and get stock and name put them to a set
     *
     * @param stockcodes http://hq.sinajs.cn/list=sh or list=sz ...
     */
    private Set<Map<String, String>> requestStock(Set<String> stockcodes) {
        Set<Map<String, String>> stockmap = new HashSet<>();

        stockcodes.forEach(code -> {
            String url = "http://hq.sinajs.cn/list=" + code.toString();
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
