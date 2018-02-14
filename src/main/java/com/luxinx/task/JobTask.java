package com.luxinx.task;

import com.luxinx.db.IDao;
import com.luxinx.service.BasicDataService;
import com.luxinx.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 秒	 	0-59	 	, - * /
 * 分	 	0-59	 	, - * /
 * 小时	 	0-23	 	, - * /
 * 日期	 	1-31	 	, - * ? / L W C
 * 月份	 	1-12 或者 JAN-DEC	 	, - * /
 * 星期	 	1-7 或者 SUN-SAT	 	, - * ? / L C #
 * 年（可选）	 	留空, 1970-2099	 	, - * /
 */
@Component
public class JobTask {


    private static final Logger log = LoggerFactory.getLogger(JobTask.class);

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private BasicDataService basicDataService;

    @Autowired
    private IDao dao;

    @Scheduled(cron = "0 10 0 * * 1-5")
    public void saveStockCodeToDB() {
        log.info("[saveStockCodeToDB]");
        long start = System.currentTimeMillis();
        basicDataService.updateAllStockName();
        long end = System.currentTimeMillis();
        log.info((end - start) + "ms");
    }

    @Scheduled(cron = "0 0 20 * * 1-5")
    public void historyDailyPrice() {
        long start = System.currentTimeMillis();

        Stock.STOCK_CODE_FOCUS.clear();
        log.info("[getHistoryDailyPrice]");
        basicDataService.updateTodayStockPrice();
        dao.execute("truncate table tb_stock_focus");
        long end = System.currentTimeMillis();
        log.info((end - start) + "ms");
    }


    @Scheduled(cron = "0 30 21 * * 1-5")
    public void choiceavgStock() {
        log.info("[choiceavgStock]");
        long start = System.currentTimeMillis();

        Stock.STOCK_CODE_FOCUS.clear();
        Stock.HASSENDED = false;

        Set<Map<String, String>> stock25set = new HashSet<>();
        Set<Map<String, String>> stock60set = new HashSet<>();
        Set<Map<String, String>> stock120set = new HashSet<>();

        //获取超过25日均线的股票
        Stock.STOCK_CODE_ALL.forEach((k, v) -> {
            Map<String, String> stock25map = strategyService.choiceavgStock(k, "25");
            stock25set.add(stock25map);
        });
        //获取超过60日均线的股票
        stock25set.forEach((Map<String, String> s) -> {
            s.entrySet().forEach(e -> {
                Map<String, String> stock60map = strategyService.choiceavgStock(e.getKey(), "60");
                stock60set.add(stock60map);
            });
        });
        //获取超过120日均线的股票
        stock60set.forEach((Map<String, String> s) -> {
            s.entrySet().forEach(e -> {
                Map<String, String> stock120map = strategyService.choiceavgStock(e.getKey(), "120");
                stock120set.add(stock120map);
            });
        });

        stock120set.forEach((Map<String, String> s) -> {
            s.entrySet().forEach(e -> {
                String stockname = Stock.STOCK_CODE_ALL.get(e.getKey());
                String insertfocus = "insert into tb_stock_focus (stockcode,stockname,destprice,updown,issend,datecreated)values(?,?,?,1,0,NOW())";
                dao.executeUpdate(insertfocus, new Object[]{e.getKey(), stockname, e.getValue()});
                Map<String, String> smap = new HashMap<>();
                smap.put("updown", "1");
                smap.put("destprice", e.getValue());
                smap.put("stockcode", e.getKey());

                smap.put(e.getKey(), e.getValue());
                Stock.STOCK_CODE_FOCUS.add(smap);
            });
        });
        long end = System.currentTimeMillis();
        log.info((end - start) + "ms");

    }
}
