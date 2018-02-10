package com.luxinx.controller;

import com.alibaba.fastjson.JSONObject;
import com.luxinx.service.BasicDataService;
import com.luxinx.service.DisplayService;
import com.luxinx.stock.HistoryPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用于页面显示股票价格的接口
 */
@Controller
@RequestMapping(value = "display", produces = "application/json;charset=utf-8")
public class DisplayController {

    @Autowired
    public DisplayService displayService;

    @Autowired
    public HistoryPrice historyPrice;

    @Autowired
    public BasicDataService basicDataService;

    private static final Logger log = LoggerFactory.getLogger(DisplayController.class);

    /**
     * 获取250日均线
     *
     * @return
     */
    @RequestMapping(value = "price")
    @ResponseBody
    public String price() {
        log.info("[price]");
        List<Map<String, Object>> result = displayService.getYearAvgPrice("");
        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value="dayprice")
    @ResponseBody
    public String dayprice(@RequestParam(required = true) String code,@RequestParam(required = true) String days){
        log.info("[dayprice]");
        Integer.parseInt(days);
        BigDecimal avgprice = basicDataService.getDayAvgPrice(code, Integer.parseInt(days));

        return avgprice.toString();
    }


    /**
     * get year data need a param year from 10-this year.
     * @param year example 10\11\12... and so on.
     * @return
     */
    @RequestMapping(value = "history")
    @ResponseBody
    public String history(@RequestParam String year) {
        int intyear = Integer.parseInt(year);
        log.info("[history]");
        Thread t = new Thread(() -> {
            historyPrice.getHistoryDailyPrice(intyear);
        });
        t.start();


        return "get stock start";
    }
}

