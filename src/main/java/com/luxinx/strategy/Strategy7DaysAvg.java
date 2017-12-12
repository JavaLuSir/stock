package com.luxinx.strategy;

import com.luxinx.db.DBConnection;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Strategy7DaysAvg {

	private Logger log = Logger.getLogger(Strategy7DaysAvg.class);
	public void setTradePrice() throws ClassNotFoundException, SQLException, NamingException{
		BigDecimal total = new BigDecimal(0);
		String sql="select openprice from tb_stock_history where stockcode='000627' order by datestr desc  limit 7";
		List<Map<String, String>> list = DBConnection.executeQuery(sql);
		for(Map<String, String> mp:list){
			total=total.add(new BigDecimal(mp.get("openprice")));
		}

		BigDecimal avgprice = total.divide(new BigDecimal(7),BigDecimal.ROUND_HALF_DOWN);
		
		avgprice.setScale(2,BigDecimal.ROUND_DOWN);
		
		BigDecimal buyprice = avgprice.multiply(new BigDecimal(0.98)).setScale(2, BigDecimal.ROUND_DOWN);
		String updatebuy="update tb_stock_focus set destprice='"+buyprice.doubleValue()+"',datecreated=NOW() where id=5 " ;
		DBConnection.execute(updatebuy);

		
		BigDecimal sellprice =buyprice.multiply(new BigDecimal(1.05)).setScale(2,BigDecimal.ROUND_DOWN);

		String updatesell="update tb_stock_focus set destprice='"+sellprice.doubleValue()+"',datecreated=NOW() where id=9 " ;
		
		DBConnection.execute(updatesell);
		
		log.info("buyprice is"+buyprice+";sellprice is:"+sellprice);

	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NamingException {
		new Strategy7DaysAvg().setTradePrice();
	}

}
