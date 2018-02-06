package com.luxinx.task;

import com.luxinx.db.IDao;
import com.luxinx.service.MonitorService;
import com.luxinx.stock.HistoryPrice;
import com.luxinx.stock.StockCodeName;
import com.luxinx.stock.StockLowestPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


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
    private MonitorService monitorService;

    @Autowired
    private StockCodeName stockCodeName;
    @Autowired
    private StockLowestPrice stockLowestPrice;
    @Autowired
    private HistoryPrice historyPrice;

    @Autowired
    private IDao dao;

    @Scheduled(cron = "0 10 0 * * 1-5")
    public void saveStockToDB() {
        log.info("[saveStockToDB]");
        stockCodeName.saveAllStocktoDB();
    }

    @Scheduled(cron = "0 56 21 * * 1-5")
    public void updateAvgPrice() {
        log.info("[updateAvgPrice]");
        stockLowestPrice.updateLowestAndAvg();
    }

    @Scheduled(cron = "* * * * * 1-5")
    public void historyDailyPrice() {
       //Stock.STOCK_CODE_FOCUS.clear();
        log.info("[getHistoryDailyPrice]");
        historyPrice.getHistoryDailyPrice();
    }

    @Scheduled(cron = "0 30 21 * * 1-5")
    public void setStrategyPrice() {
        log.info("[setStrategyPrice]");
        List<Map<String, Object>> list = dao.executeQuery("select * from tb_stock_name");
        list.forEach((Map<String,Object> e)->{
            monitorService.choiceGoodStock(e.get("stockid") + "");
        });
      //  new Strategy7DaysAvg().setTradePrice();//现在只设置天茂的价格
    }
}
