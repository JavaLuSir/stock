package com.luxinx.stock;

import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import java.util.ResourceBundle;

/**
 * use to init config.properties and crond
 */
public class InitConfig extends HttpServlet {

	private static final Logger log = Logger.getLogger(InitConfig.class);
	@Override
	public void init(ServletConfig config){
//	   ResourceBundle bundle = ResourceBundle.getBundle("config");
//	   Constant.DBDRIVER = bundle.getString("driver");
//	   Constant.DBURL = bundle.getString("url");
//	   Constant.DBUSER = bundle.getString("username");
//	   Constant.DBPASSWORD = bundle.getString("password");
//	   log.info(Constant.DBDRIVER);
//	   log.info(Constant.DBURL);
//	   log.info(Constant.DBUSER);
//	   log.info(Constant.DBPASSWORD);
//	   new CurrentDailyPrice();
	}
       
}
