package com.luxinx.service;

import java.util.Map;
import java.util.Set;

/**
 *  策略接口
 */
public interface StrategyService {

    /**
     * 选取突破日均线的股票
     * @param code 股票代码
     * @param days 平均天数
     * @return
     */
    Map<String,String> choiceavgStock(String code,String days);

    /**
     * 保存选择好的股票
     * @param code 股票代码
     * @param stockname 股票名称
     * @param destprice 股票价格
     * @return
     */
    Map<String,String> saveChoicedStock(String code, String stockname,String destprice);
}
