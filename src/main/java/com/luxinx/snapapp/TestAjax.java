package com.luxinx.snapapp;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class TestAjax
 */
@WebServlet("/testAjax")
public class TestAjax extends HttpServlet {
	 private static final long serialVersionUID = 1L;
     
	    // 上传文件存储目录
	    private static final String UPLOAD_DIRECTORY = "upload";
	 
	    // 上传配置
	    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		result.put("code", 0);
		result.put("msg", "resquest get successful!");
		out.println(JSONObject.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		result.put("code", 0);
		result.put("msg", "resquest post successful!");
		out.println(JSONObject.toJSONString(result));
	}
	
	

}
