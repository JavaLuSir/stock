package com.luxinx.db;

import com.luxinx.snapapp.Constant;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DataBase Access Class
 * @author Administrator
 *
 */
public class DBConnection {
	public static ThreadLocal<Connection> threadlocal = new ThreadLocal<>();
	public static Connection getConnection() throws ClassNotFoundException, SQLException, NamingException{
		Class.forName(Constant.DBDRIVER);
		Connection	conn = DriverManager.getConnection(Constant.DBURL, Constant.DBUSER, Constant.DBPASSWORD);
		conn.createStatement().executeQuery("select 1 from dual");
		threadlocal.set(conn);
		return threadlocal.get();
	}
	
	public static List<Map<String,String>> executeQuery(String sql){
		List<Map<String,String>> result = new ArrayList<>();
		Connection conn=null;
		Statement stmt = null;
		try {
			conn= DBConnection.getConnection();
			stmt= conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			ResultSetMetaData meta = rs.getMetaData();

			while(rs.next()){
				Map<String,String> dbitem = new HashMap<String,String>();
				for(int i=1;i<=meta.getColumnCount();i++){
					String colname = meta.getColumnName(i);
					String colvalue = rs.getString(colname);
					dbitem.put(colname, colvalue);
				}
				result.add(dbitem);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static int execute(String sql) throws ClassNotFoundException, SQLException, NamingException{
		Connection conn = null;
		Statement stmt = null;
		int state = -1;
		try{
		conn = DBConnection.getConnection();
		
		stmt = conn.createStatement();
		
		state = stmt.executeUpdate(sql);
		}finally{
			try {
				if(stmt!=null)
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return state;
	}
	
	
	
}
