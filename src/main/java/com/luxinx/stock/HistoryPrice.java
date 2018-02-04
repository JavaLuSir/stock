package com.luxinx.stock;

import com.luxinx.db.IDao;
import com.luxinx.util.DateUtil;
import com.luxinx.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Use to Deal Stock History Data infomation
 */
@Component
public class HistoryPrice {

	private static Logger log = LoggerFactory.getLogger(HistoryPrice.class);

	@Autowired
	private IDao dao;
	/**
	 * get all stock code and name from DB
	 * @return return listmap
	 */
	private List<Map<String, Object>> geAlltStockCode(){
		String sql = "select stockid from tb_stock_name";

		return dao.executeQuery(sql);
	}
	
	public String getHistoryDailyPrice(){
		getHistoryDailyPrice(Integer.parseInt(DateUtil.getThisYear()));
		log.info("All history get successed!");
		return "";
	}

	public String getHistoryDailyPrice(int year){
		List<Map<String, Object>> listcode = geAlltStockCode();
		for(Map<String,Object> map:listcode){
			String code = map.get("stockid")+"";
			String precode;
			if(code.startsWith("6")){
				precode="sh";
			}else{
				precode="sz";
			}
			if("000001".equals(code)){
				String shprecode="sh";
				requestHistoryAndSave(code, shprecode,year+"");
			}
			requestHistoryAndSave(code, precode,year+"");

		}
		log.info(year+" All history get successed!");
		return "";
	}
	
	private void getYearHistory(String code, String precode) {
		requestHistoryAndSave(code, precode, DateUtil.getThisYear());
	}

	private void requestHistoryAndSave(String code, String precode, String year) {
		if(year==null||"".equals(year)){
			year=DateUtil.getThisYear();
		}
		String result="";
		String url;
		try {
			url = "http://data.gtimg.cn/flashdata/hushen/daily/"+year+"/"+precode+code+".js";
			result = HttpUtil.doGet(url);
		} catch (Exception e){
			log.info("Http Exception...");
		}
		if(!"".equals(result)){
		 String[] resultarry = result.split("\\\\n\\\\");
		 for(int i=1;i<resultarry.length-1;i++){
			 	String id = UUID.randomUUID().toString().replaceAll("-","");
		    	String[] pricearry = resultarry[i].split(" ");
		    	String openprice = pricearry[1]; 
		    	String closeprice = pricearry[2]; 
		    	String highprice = pricearry[3];
		    	String lowprice = pricearry[4];
		    	String volumn = pricearry[5];
		    	String datestr = pricearry[0].replaceAll("\\n","");
		    	if("sh000001".equals(precode+code)){
		    		code=precode+code;
		    	}
		    	String sql ="insert into tb_stock_history values('"+id+"','"+code+"','"+openprice+"','"+closeprice+"','"+highprice+"','"+lowprice+"','"+volumn+"','"+datestr+"')";
		    	log.info(sql);
		    	try {
		    		dao.execute(sql);
				}catch(Exception e){
					log.error("SQLException...");
				}
		 }
		}
	}

}
