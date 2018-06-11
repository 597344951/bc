package com.zltel.broadcast.incision.sola.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求工具
 * HttpUtil class
 *
 * @author Touss
 * @date 2018/5/17
 */
public class HttpUtil {

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
            StringBuffer stringBuffer = null;
            String line = null;
            if(code == HttpURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                stringBuffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                return JsonUtils.deserialization(stringBuffer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
