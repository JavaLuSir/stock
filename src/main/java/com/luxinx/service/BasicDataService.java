package com.luxinx.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 获取基本数据信息
 */
public interface BasicDataService {

    /**
     * 获取股票指定天数的平均值
     * @param code 股票代码
     * @param days 相应天数
     * @return BigDecima 类型的平均值
     */
    BigDecimal getDayAvgPrice(String code,int days);

    /**
     * 更新当年股票价格信息
     */
    void updateTodayStockPrice();

    /**
     * 更新当年股票价格信息
     * @param year 两位年份
     * @param istoday 是否只获取今天 true获取今天；false获取年份
     */
    void updateTodayStockPrice(String year,boolean istoday);

    /**
     * 更新现在所有的股票代码以及名称
     */
    void updateAllStockName();

    /**
     * 获取现在所有的股票代码以及名称
     */
    List<Map<String,Object>> getAllStockCodeName();
}
