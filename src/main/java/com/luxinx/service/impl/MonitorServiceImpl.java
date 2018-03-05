package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.MonitorService;
import com.luxinx.task.Stock;
import com.luxinx.util.HttpUtil;
import com.luxinx.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@Service
public class MonitorServiceImpl implements MonitorService {
    private static final Logger log = LoggerFactory.getLogger(MonitorServiceImpl.class);
    @Autowired
    public IDao dao;

    @Override
    public void monitorDailyPrice() {
        Stock.HASSENDED = true;
        log.info("============================start===================================");
        Stock.STOCK_CODE_FOCUS.forEach((Map<String, String> focus) -> {
            String code = focus.get("stockcode") + "";
            String precode = BasicDataServiceImpl.getString(code);
            try {
                long st = System.currentTimeMillis();
                String current = HttpUtil.doGet("http://hq.sinajs.cn/list=" + precode + code);
                String[] cuuarry = current.split(",");
				/*Map<String,String> daymp = new HashMap<>();
				daymp.put("id", code);
				daymp.put("o", cuuarry[1]);//今开
				daymp.put("c", cuuarry[2]);//昨收
				daymp.put("dq", cuuarry[3]);//当前
				daymp.put("d", cuuarry[30]+" "+cuuarry[31]);//日期*/
                double destprice = 0.0;
                if (focus.get("destprice") != null && !"".equals(focus.get("destprice"))) {
                    destprice = Double.parseDouble(focus.get("destprice") + "");
                }
                double currprice = 0.0;
                String strcurrprice = "";
                if (cuuarry.length > 3) {
                    strcurrprice = cuuarry[3];
                }
                if (strcurrprice != null && !"".equals(strcurrprice)) {
                    currprice = Double.parseDouble(strcurrprice);
                }
                double detaprice = currprice - destprice;
                String updown = focus.get("updown");
                if (!Stock.HASSENDED) {

                    if ("-1".equals(updown)) {
                        if (detaprice < 0) {
                            String message = getPrecentStr(focus, destprice, currprice, detaprice, " 已经跌破");
                            log.info("add message "+message);
                            Stock.EMAIL_QUEUE.add(message);
                        }
                    }
                    if (currprice / destprice > 1.05) {
                        if ("1".equals(updown)) {
                            if (detaprice > 0) {
                                String message = getPrecentStr(focus, destprice, currprice, detaprice, " 已经涨过");
                                log.info("add message "+message);
                                Stock.EMAIL_QUEUE.add(message);
                            }
                        }
                    }
                }


                long ed = System.currentTimeMillis();
                log.info((ed - st) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        log.info("============================end===================================");

    }


    /**
     * get stock Percent
     *
     * @param focus
     * @param destprice
     * @param currprice
     * @param detaprice
     * @param s
     * @return
     */
    private String getPrecentStr(Map<String, String> focus, double destprice, double currprice, double detaprice, String s) {
        String stockcode = focus.get("stockcode");
        String stockname = focus.get("stockname");
        String strprecent = ((detaprice / destprice) * 100) + "";
        if (strprecent.length() > 4) {
            strprecent = strprecent.substring(0, 4);
        }
        return "股票(" + stockcode + ") " + stockname + s + destprice + "元，当前价格为" + currprice + "元。距目标价振幅" + strprecent + "%\n";
    }
}
