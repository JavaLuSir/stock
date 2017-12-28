package com.luxinx.stock;

import com.luxinx.db.DBConnection;
import com.luxinx.util.HttpUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * get stock info from http://hq.sinajs.cn/list=sh + code
 */
public class StockCodeName {

	private Logger log = Logger.getLogger(StockCodeName.class);

	/**
	 * get all shanghai stock code and name
	 * @return
	 */
	public Set<Map<String,String>> getStockShName(){
		long start = System.currentTimeMillis();
		Set<Map<String,String>> set = new HashSet<>();
		for(int i=600001;i<604000;i++){
			String url="http://hq.sinajs.cn/list=sh"+i;
			requestStock(set, url);
		}
		long end =System.currentTimeMillis();
		log.info("get sh stock spend time:"+(end-start)+"ms");
		return set;
	}

	/**
	 * request http://hq.sinajs.cn url and get stock and name put them to a set
	 * @param set resultset
	 * @param url http://hq.sinajs.cn/list=sh or list=sz ...
	 */
	private void requestStock(Set<Map<String, String>> set, String url) {
		try {
            String returnurl = HttpUtil.doGet(url);
            Map<String, String> mp = HttpUtil.dealResponse(returnurl,1);
            if(!mp.isEmpty()){
                set.add(mp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/**
	 * get all shenzhen(not contain 300xxx) stock code and name
	 * @return
	 */
	public Set<Map<String,String>> getStockSzName(){
		long start = System.currentTimeMillis();
		Set<Map<String,String>> set = new HashSet<>();
		for(int i=1;i<3000;i++){
			String code=i+"";
			for(int j=0;j<5;j++){
				if(code.length()==6){
					break;
				}
				code="0"+code;
			}
			String url="http://hq.sinajs.cn/list=sz"+code;
			requestStock(set, url);
		}
		try {
			String url="http://hq.sinajs.cn/list=sz399001";
			String returnurl = HttpUtil.doGet(url);
			Map<String, String> mp = HttpUtil.dealResponse(returnurl,1);
			set.add(mp);
		} catch (Exception e) {
			log.info("HTTPException ...");
		}
		long end =System.currentTimeMillis();
		log.info("get sz stock spend time:"+(end-start)+"ms");
		return set;
	}

	/**
	 * insert or update all stock code and name to database
	 */
	public void saveAllStocktoDB(){
		
		Set<Map<String, String>> shSet = getStockShName();
		Set<Map<String, String>> szSet = getStockSzName();
		shSet.addAll(szSet);
		for(Map<String,String> m:shSet){
			for(Entry<String, String> e:m.entrySet()){
				String sql ="insert into tb_stock_name values('"+e.getKey()+"','"+e.getValue()+"')";
				try {
					DBConnection.execute(sql);
				} catch (Exception e1) {
					String sqlupdate ="update tb_stock_name SET stockname='"+e.getValue()+"' where stockid='"+e.getKey()+"'";
					try {
						DBConnection.execute(sqlupdate);
						log.info(sqlupdate);
					} catch (Exception e2) {
						log.error("UPDATE SQLException...");
					}
					log.error("SQLException...");
				}
			}
		}
	}
	
}
