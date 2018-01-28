package com.luxinx.strategy;

import com.luxinx.db.IDao;
import com.luxinx.stock.HistoryPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("strategy")
@Controller
public class ChoiceStockStrategy {

    private final Logger logger = LoggerFactory.getLogger(ChoiceStockStrategy.class);
    @Autowired
    private IDao dao;

    @RequestMapping("avg")
    public Map<String,Double> get25AvgPrice(@RequestParam String code){
        String sql25avg = "select closeprice,vol from tb_stock_history where stockcode='"+code+"' order by datestr DESC limit 25";
        List<Map<String, Object>> listprice = dao.executeQuery(sql25avg);
        double vollast = Double.parseDouble(listprice.get(0).get("vol") + "");
        double volbeforelast = Double.parseDouble(listprice.get(1).get("vol") + "");
        double trend = (vollast/volbeforelast);
        final BigDecimal[] total = {new BigDecimal(0)};
        total[0].setScale(2);
        listprice.forEach((Map<String, Object> e) ->{
            logger.info(e.get("closeprice")+"");
            total[0] = total[0].add(new BigDecimal(e.get("closeprice")+""));
        });
        logger.info("25avgPrice: "+ total[0].divide(new BigDecimal(listprice.size())));
       
        Map<String,Double> result = new HashMap<>();
        //判断成交量比前一日放量才获取25日均线
        if(trend>0){
            //获取一只股票25日平均值
            result.put(code,total[0].divide(new BigDecimal(listprice.size())).doubleValue());
        }
        return result;
    }
}
