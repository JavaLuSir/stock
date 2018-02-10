package com.luxinx.stock;

import com.luxinx.db.IDao;
import com.luxinx.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockLowestPrice {

	private Logger log = LoggerFactory.getLogger(StockLowestPrice.class);

	@Autowired
	private IDao dao;
	public void updateLowestAndAvg(){

		String insert = "INSERT INTO tb_stock_lowest(low,stockcode) SELECT min(lowprice),stockcode FROM tb_stock_history GROUP BY stockcode";
		try {
			dao.execute(insert);
			log.info(insert);
		} catch (Exception e) {
			String updatsql="UPDATE tb_stock_lowest tbl INNER JOIN (SELECT min(lowprice) low,stockcode FROM tb_stock_history GROUP BY stockcode) tbm ON tbl.stockcode=tbm.stockcode SET tbl.low=tbm.low";
			try {
				dao.execute(updatsql);
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
		
		String strtoday = DateUtil.getCurrentDateStr("yyMMdd");
		String sql = "UPDATE tb_stock_lowest t,(SELECT s.stockcode,s.closeprice FROM tb_stock_history s WHERE datestr='"+strtoday+"') s set t.currprice=s.closeprice WHERE t.stockcode=s.stockcode";
		try {

			dao.execute(sql);
			log.info(sql);
			String lastyear = DateUtil.getYear(-1);
			String strlast = lastyear + strtoday.substring(2);
			String upavgsql = "UPDATE tb_stock_lowest t,(SELECT AVG(ss.closeprice) as avgprice,stockcode FROM tb_stock_history ss where datestr>'"+strlast+"' group BY stockcode) s set t.avgprice=s.avgprice WHERE t.stockcode=s.stockcode ";
			dao.execute(upavgsql);
			log.info(upavgsql);

		} catch (Exception e) {
			log.info("SQLException");
		}
	
	}

}
