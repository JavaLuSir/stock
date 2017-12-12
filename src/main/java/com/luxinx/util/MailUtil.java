package com.luxinx.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

	public static void sendMessage(String athost,String message) throws AddressException, MessagingException{

        Properties props = new Properties();  
        // 开启debug调试  
        props.setProperty("mail.debug", "false");  
        // 发送服务器需要身份验证  
        props.setProperty("mail.smtp.auth", "true");  
        // 设置邮件服务器主机名  
        props.setProperty("mail.host", "mail.luxinx.com");  
        // 发送邮件协议名称  
        props.setProperty("mail.transport.protocol", "smtp");  
          
        // 设置环境信息  
        Session session = Session.getInstance(props);  
          
        // 创建邮件对象  
        Message msg = new MimeMessage(session);  
        msg.setSubject("股票预警");  
        // 设置邮件内容  
        
        msg.setText(message);  
        // 设置发件人  
        msg.setFrom(new InternetAddress("stock@luxinx.com"));  
          
        Transport transport = session.getTransport();  
        // 连接邮件服务器  
        transport.connect("stock@luxinx.com", "123456");  
        // 发送邮件  
        transport.sendMessage(msg, new Address[] {new InternetAddress(athost)});  
        // 关闭连接  
        transport.close();  
    
	}
	public static void main(String[] args) throws MessagingException {
		MailUtil.sendMessage("javalusir@163.com", "撒旦撒旦");
	} 
		
}
