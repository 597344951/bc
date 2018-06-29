package com.zltel.broadcast.incision.sola.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.broadcast.common.exception.RRException;

/**
 * Http请求工具 HttpUtil class
 *
 * @author Touss
 * @date 2018/5/17
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {}

    public static String post(String url, String postData) {
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        try {
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("Keep-Alive", String.valueOf(false));
            urlConnection.setRequestProperty("Charsert", "UTF-8");

            outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(postData);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            int code = urlConnection.getResponseCode();
            StringBuilder stringBuffer = null;
            String line = null;
            if (code == HttpURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                stringBuffer = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                return JsonUtils.deserialization(stringBuffer.toString());
            } else {
                String em = "请求失败：" + code;
                RRException.makeThrow(em);
            }
        } catch (IOException e) {
            log.error("请求失败,url:{} , postData: {}", url, postData, e);
            String em = "请求失败:" + e.getMessage();
            RRException.makeThrow(em);
        } finally {
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(bufferedReader);
        }
        
        RRException.makeThrow("没有返回数据");
        return null;
    }
}
