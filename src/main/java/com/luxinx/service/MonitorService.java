package com.luxinx.service;

import java.util.Map;

public interface MonitorService {

    void monitorDailyPrice();

    Map<String,String> choiceGoodStock(String code);

}
