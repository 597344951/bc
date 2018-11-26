package com.zltel.broadcast.wechat_pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * http请求工具类
 * @author 张毅
 * @since jdk 1.8.0_191_b12
 * date: 2018.10.30
 */
public class Http_Util {
	private final static int CONNECT_TIMEOUT = 5000;	//连接超时的时间（毫秒）
    private final static String DEFAULT_ENCODING = "UTF-8";  //字符串编码
    
    public static String post_request(String urlStr, String data){  
        return post_request(urlStr, data, null);  
    } 
    
    /**
     * post数据请求
     * @param urlStr
     * @param data
     * @param contentType
     * @return
     */
    public static String post_request(String urlStr, String data, String contentType) {
    	BufferedReader reader = null;
    	OutputStreamWriter writer = null;
        try {    
            URL url = new URL(urlStr);    
            URLConnection conn = url.openConnection();    
            conn.setDoOutput(true);    
            conn.setConnectTimeout(CONNECT_TIMEOUT);    
            conn.setReadTimeout(CONNECT_TIMEOUT);    
            if(contentType != null) conn.setRequestProperty("content-type", contentType);    
            writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            writer.write(data == null ? "" : data);     
            writer.flush(); 

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));    
            StringBuilder sb = new StringBuilder();    
            String line = null;    
            while ((line = reader.readLine()) != null) {    
                sb.append(line);    
                sb.append("\r\n");    
            }    
            return sb.toString();    
        } catch (IOException e) {    
        	
        } finally {    
            try {    
            	if (writer != null) writer.close();
                if (reader != null) reader.close();    
            } catch (IOException e) {   
            	
            }    
        }    
        return null;
    }
}
