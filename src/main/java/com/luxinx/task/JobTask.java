package com.luxinx.task;

import com.luxinx.stock.HistoryPrice;
import com.luxinx.stock.StockCodeName;
import com.luxinx.stock.StockLowestPrice;
import com.luxinx.strategy.Strategy7DaysAvg;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


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
@EnableScheduling
public class JobTask {

    private static final Logger log = Logger.getLogger(JobTask.class);

    @Scheduled(cron = "0 10 0 * * 1-5")
    public void saveStockToDB() {
        new StockCodeName().saveAllStocktoDB();
    }

    @Scheduled(cron = "0 56 21 * * 1-5")
    public void updateAvgPrice() {
        new StockLowestPrice().updateLowestAndAvg();
    }

    @Scheduled(cron = "0 0 20 * * 1-5")
    public void historyDailyPrice() {
       Stock.STOCK_CODE_FOCUS.clear();
       new HistoryPrice().getHistoryDailyPrice();
    }

    @Scheduled(cron = "0 0 23 * * 1-5")
    public void setStrategyPrice() {
      //  new Strategy7DaysAvg().setTradePrice();//现在只设置天茂的价格
    }
}
