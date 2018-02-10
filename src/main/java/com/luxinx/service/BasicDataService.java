package com.luxinx.service;

import java.math.BigDecimal;

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
}
