package com.luxinx.controller;

import com.alibaba.fastjson.JSONObject;
import com.luxinx.service.DisplayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/display")
public class DisplayController {
    @Autowired
    public DisplayService displayService;
    private static final Logger log =Logger.getLogger(DisplayController.class);

    @RequestMapping(value="price",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String price(){
        List<Map<String, Object>> result = displayService.getYearAvgPrice("");
        log.info(JSONObject.toJSONString(result));
        return JSONObject.toJSONString(result);
    }
}
