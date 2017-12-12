package com.luxinx.snapapp;

import com.luxinx.db.DBConnection;
import com.luxinx.util.DateUtil;
import com.luxinx.util.HttpUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HistoryPrice {

	public static Logger log = Logger.getLogger(HistoryPrice.class);
	public List<Map<String,String>> getStockCode(){
		String sql = "select stockid from tb_stock_name";
		List<Map<String, String>> result = DBConnection.executeQuery(sql);
		return result;
	}
	
	public String getHistoryDailyPrice(){
		List<Map<String, String>> listcode = getStockCode();
		for(Map<String,String> map:listcode){
			 String code = map.get("stockid");
			 String precode = ""; 
			 if(code.startsWith("6")){
				 precode="sh";
			 }else{
				 precode="sz";
			 }
			 if("000001".equals(code)){
				 String shprecode="sh";
				 getYearHistory(code, shprecode);
			 }
			 getYearHistory(code, precode);
			
		}
		log.info("All history get successed!");
		return "";
	}
	
	private void getYearHistory(String code, String precode) {
		requestHistoryAndSave(code, precode, DateUtil.getThisYear());
	}

	public static void requestHistoryAndSave(String code, String precode, String year) {
		if(year==null||"".equals(year)){
			year=DateUtil.getThisYear();
		}
		String result="";
		String url ="";
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
					DBConnection.execute(sql);
				}catch(Exception e){
					log.error("SQLException...");
				}
		 }
		}
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException, ClassNotFoundException, SQLException, NamingException {
	//	new HistoryPrice().getHistoryPriceByCode();
	//	System.out.println(new HistoryPrice().getThisYear());
	}
}
