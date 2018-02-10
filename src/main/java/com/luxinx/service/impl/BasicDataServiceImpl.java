package com.luxinx.service.impl;

import com.luxinx.db.IDao;
import com.luxinx.service.BasicDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BasicDataServiceImpl implements BasicDataService{

    private static final Logger log = LoggerFactory.getLogger(DisplayServiceImpl.class);

    @Autowired
    public IDao dao;
    @Override
    public BigDecimal getDayAvgPrice(String code, int days) {

        String sql="select sum(closeprice)/"+days+" as price from " +
                "(select closeprice from tb_stock_history where stockcode='"+code+"'" +
                " order by datestr desc limit 0,"+days+") t";
        log.info(sql);
        List<Map<String, Object>> price = dao.executeQuery(sql);

        BigDecimal decimalprice = new BigDecimal(0.0);

        if(price!=null&&!price.isEmpty()){
            double doubleprice = Double.parseDouble(price.get(0).get("price") + "");
            decimalprice=decimalprice.add(new BigDecimal(doubleprice));
            decimalprice=decimalprice.setScale(2,BigDecimal.ROUND_DOWN);

        }

        return decimalprice;
    }
}
