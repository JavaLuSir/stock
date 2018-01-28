package com.luxinx.strategy;

import com.luxinx.db.IDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

public class Strategy7DaysAvg {

	private Logger log = Logger.getLogger(Strategy7DaysAvg.class);

	@Autowired
	private IDao dao;

	public void setTradePrice(){
		BigDecimal total = new BigDecimal(0);
		String sql="select openprice from tb_stock_history where stockcode='000627' order by datestr desc  limit 7";

		List<Map<String, Object>> list = dao.executeQuery(sql);
		for(Map<String, Object> mp:list){
			total=total.add(new BigDecimal(mp.get("openprice")+""));
		}

		BigDecimal avgprice = total.divide(new BigDecimal(7),BigDecimal.ROUND_HALF_DOWN);

		avgprice = avgprice.setScale(2, BigDecimal.ROUND_DOWN);

		BigDecimal buyprice = avgprice.multiply(new BigDecimal(0.98)).setScale(2, BigDecimal.ROUND_DOWN);
		String updatebuy="update tb_stock_focus set destprice='"+buyprice.doubleValue()+"',datecreated=NOW() where id=5 " ;
		dao.execute(updatebuy);

		BigDecimal sellprice =buyprice.multiply(new BigDecimal(1.05)).setScale(2,BigDecimal.ROUND_DOWN);

		String updatesell="update tb_stock_focus set destprice='"+sellprice.doubleValue()+"',datecreated=NOW() where id=9 " ;
		dao.execute(updatesell);

		log.info("buyprice is"+buyprice+";sellprice is:"+sellprice);

	}

}
