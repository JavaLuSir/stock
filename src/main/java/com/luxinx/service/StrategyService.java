package com.luxinx.service;

import java.util.Map;

/**
 *  策略接口
 */
public interface StrategyService {

    /**
     * 选取突破25日均线的股票
     * @param code
     * @return
     */
    Map<String,String> choice25avgStock(String code);
}
