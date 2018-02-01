package com.luxinx.controller;

import com.alibaba.fastjson.JSONObject;
import com.luxinx.service.DisplayService;
import com.luxinx.stock.HistoryPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="display",produces = "application/json;charset=utf-8")
public class DisplayController {

    @Autowired
    public DisplayService displayService;

    @Autowired
    public HistoryPrice historyPrice;
    private static final Logger log =LoggerFactory.getLogger(DisplayController.class);

    @RequestMapping(value="price")
    @ResponseBody
    public String price(){
        log.info("[price]");
        List<Map<String, Object>> result = displayService.getYearAvgPrice("");
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value="stockname")
    @ResponseBody
    public String stockname(){
        log.info("[stockname]");
        List<Map<String, Object>> result = displayService.getYearAvgPrice("");
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value="history")
    @ResponseBody
    public String history(){
        log.info("[history]");
        Thread t = new Thread(()->{
            for (int i=10;i<19;i++){
                historyPrice.getHistoryDailyPrice(i);
            }
        });
        t.start();


        return "get all stock";
    }
}
