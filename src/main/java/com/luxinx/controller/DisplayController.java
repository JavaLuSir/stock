package com.luxinx.controller;

import com.alibaba.fastjson.JSONObject;
import com.luxinx.service.DisplayService;
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

    @ResponseBody
    @RequestMapping(value="price")
    public String price(){
        List<Map<String, Object>> result = displayService.getYearAvgPrice("");
        return JSONObject.toJSONString(result);
    }
}
