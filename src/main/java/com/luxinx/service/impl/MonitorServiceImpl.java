package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.MonitorService;
import com.luxinx.task.Stock;
import com.luxinx.util.HttpUtil;
import com.luxinx.util.MailUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@Service
public class MonitorServiceImpl implements MonitorService {
    private static final Logger log =Logger.getLogger(MonitorServiceImpl.class);
    @Autowired
    public IDao dao;
    @Override
    public void monitorDailyPrice() {
        {
            log.info("============================start===================================");
            if(Stock.STOCK_CODE_FOCUS.isEmpty()){
                String sql = "select stockcode,stockname,destprice,updown,issend from tb_stock_focus";
                Stock.STOCK_CODE_FOCUS = dao.executeQuery(sql);
            }
            for(Map<String,Object> focus:Stock.STOCK_CODE_FOCUS){
                String code=focus.get("stockcode")+"";
                String precode = "";
                if(code.startsWith("0")||code.startsWith("3")){
                    precode="sz";
                }else if(code.startsWith("6")){
                    precode="sh";
                }else{
                    precode="";
                }
                try {
                    long st = System.currentTimeMillis();
                    String current= HttpUtil.doGet("http://hq.sinajs.cn/list="+precode+code);
                    String[] cuuarry = current.split(",");
				/*Map<String,String> daymp = new HashMap<>();
				daymp.put("id", code);
				daymp.put("o", cuuarry[1]);//今开
				daymp.put("c", cuuarry[2]);//昨收
				daymp.put("dq", cuuarry[3]);//当前
				daymp.put("d", cuuarry[30]+" "+cuuarry[31]);//日期*/
                    double destprice = 0.0;
                    if(focus.get("destprice")!=null&&!"".equals(focus.get("destprice"))){
                        destprice=Double.parseDouble(focus.get("destprice")+"");
                    }
                    double currprice = 0.0;
                    String strcurrprice = "";
                    if(cuuarry.length>3){
                        strcurrprice=cuuarry[3];
                    }
                    if(strcurrprice!=null&&!"".equals(strcurrprice)){
                        currprice=Double.parseDouble(strcurrprice);
                    }
                    double detaprice = currprice-destprice;
                    String updown=focus.get("updown")+"";
                    String issend = focus.get("issend")+"";
                    if("0".equals(issend)){
                        if("-1".equals(updown)){
                            if(detaprice<0){
                                String message = getPrecentStr(focus, destprice, currprice, detaprice, " 已经跌破");
                                EmailNotice(focus, message);
                            }
                        }
                        if("1".equals(updown)){
                            if(detaprice>0){
                                String message = getPrecentStr(focus, destprice, currprice, detaprice, " 已经涨过");
                                EmailNotice(focus, message);
                            }
                        }
                    }
                    long ed = System.currentTimeMillis();
                    log.info((ed-st)+"ms");
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            log.info("============================end===================================");

        }

    }

    /**
     * Send Email
     * @param focus the stock of focus
     * @param message email notice message
     */
    private void EmailNotice(Map<String, Object> focus, String message) {
        try {
            MailUtil.sendMessage("javalusir@163.com", message);
            focus.put("issend", "1");
            log.info("Email send...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /**
     * get stock Percent
     * @param focus
     * @param destprice
     * @param currprice
     * @param detaprice
     * @param s
     * @return
     */
    private String getPrecentStr(Map<String, Object> focus, double destprice, double currprice, double detaprice, String s) {
        String stockcode = focus.get("stockcode")+"";
        String stockname = focus.get("stockname")+"";
        String strprecent = ((detaprice / destprice) * 100) + "";
        if (strprecent.length() > 4) {
            strprecent = strprecent.substring(0, 4);
        }
        return "股票(" + stockcode + ") " + stockname + s + destprice + "元，当前价格为" + currprice + "元。距目标价振幅" + strprecent + "%";
    }
}
