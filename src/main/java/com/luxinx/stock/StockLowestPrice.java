package com.luxinx.stock;

import com.luxinx.db.DBConnection;
import com.luxinx.util.DateUtil;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;

public class StockLowestPrice {

	Logger log = Logger.getLogger(StockLowestPrice.class);
	public void updateLowestAndAvg(){
		String insert = "INSERT INTO tb_stock_lowest(low,stockcode) SELECT min(lowprice),stockcode FROM tb_stock_history GROUP BY stockcode";
		try {
			DBConnection.execute(insert);
			log.info(insert);
		} catch (Exception e) {
			String updatsql="UPDATE tb_stock_lowest tbl INNER JOIN (SELECT min(lowprice) low,stockcode FROM tb_stock_history GROUP BY stockcode) tbm ON tbl.stockcode=tbm.stockcode SET tbl.low=tbm.low";
			try {
				DBConnection.execute(updatsql);
				log.info(updatsql);
			} catch (Exception e1) {
				log.error("SQLUpdate Exception...");
			}
		}
		
		/*String update = "update tb_stock_lowest set low = s.low where (SELECT min(lowprice) low,stockcode FROM tb_stock_history GROUP BY stockcode) s";
		try {
			DBConnection.execute(insert);
			log.info(insert);
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			log.info("SQLException");
		}*/
		
		String strtoday = DateUtil.getCurrentStr("yyMMdd");
		String sql = "UPDATE tb_stock_lowest t,(SELECT s.stockcode,s.closeprice FROM tb_stock_history s WHERE datestr='"+strtoday+"') s set t.currprice=s.closeprice WHERE t.stockcode=s.stockcode";
		try {
			DBConnection.execute(sql);
			log.info(sql);
		} catch (ClassNotFoundException | SQLException e) {
			log.info("SQLException");
		}
		String lastyear = DateUtil.getYear(-1);
		String strlast = lastyear + strtoday.substring(2);
		String upavgsql = "UPDATE tb_stock_lowest t,(SELECT AVG(ss.closeprice) as avgprice,stockcode FROM tb_stock_history ss where datestr>'"+strlast+"' group BY stockcode) s set t.avgprice=s.avgprice WHERE t.stockcode=s.stockcode ";
		
		try {
			DBConnection.execute(upavgsql);
			log.info(upavgsql);
		} catch (ClassNotFoundException | SQLException e) {
			log.info("SQLException");
		}
	
	}

}
