package com.luxinx.snapapp;

import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ResourceBundle;

public class InitConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(InitConfig.class);
	@Override
	public void init(ServletConfig config) throws ServletException {
	   ResourceBundle bundle = ResourceBundle.getBundle("config");
	   Constant.DBDRIVER = bundle.getString("driver");
	   Constant.DBURL = bundle.getString("url");
	   Constant.DBUSER = bundle.getString("username");
	   Constant.DBPASSWORD = bundle.getString("password");
	   log.info(Constant.DBDRIVER);
	   log.info(Constant.DBURL);
	   log.info(Constant.DBUSER);
	   log.info(Constant.DBPASSWORD);
	   new CurrentDailyPrice();
	}
       
}
