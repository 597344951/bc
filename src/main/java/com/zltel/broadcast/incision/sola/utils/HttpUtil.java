package com.zltel.broadcast.incision.sola.utils;

import java.io.*;
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

    /**
     * 上传文件
     * 
     * @param url URL
     * @param formName 表单名称
     * @param file 文件路径
     * @return
     */
    public static Result postFile(String url, String formName, String file) {
        return postFile(url, formName, new File(file));
    }

    /**
     * 上传文件
     * 
     * @param url URL
     * @param formName 表单名
     * @param file 文件
     * @return
     */
    public static Result postFile(String url, String formName, File file) {
        Result result;
        try {
            result = postFile(url, formName, new FileInputStream(file), file.getName());
        } catch (FileNotFoundException e) {
            result = new Result(500, "上传失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 上传文件
     * 
     * @param url 上传URL
     * @param formName 表单名
     * @param inputStream 文件流
     * @param fileName 文件名
     * @return
     */
    public static Result postFile(String url, String formName, InputStream inputStream, String fileName) {
        String twoHyphens = "--";
        // request头和上传文件内容的分隔符
        String boundary = "******" + System.currentTimeMillis() + "******";
        String lineBreak = "\r\n";
        // 返回结果
        Result result;
        try {
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            // header
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("Connection", "keep-alive");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // 连接输入流
            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            // 写入文件描述信息
            dataOutputStream.writeBytes(twoHyphens + boundary + lineBreak);
            dataOutputStream.writeBytes("Content-Disposition: form-data; " + "name=\"" + formName + "\";filename=\""
                    + fileName + "\"" + lineBreak);
            dataOutputStream.writeBytes(lineBreak);
            // 写入文件内容
            byte[] buffer = new byte[512];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, length);
            }
            dataOutputStream.writeBytes(lineBreak);
            // 写入结束标志
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineBreak);
            // 文件写入完成, 关闭数据上传流
            dataOutputStream.flush();
            dataOutputStream.close();
            // 关闭图片文件流
            inputStream.close();

            // 获取返回结果
            int code = urlConnection.getResponseCode();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            // 关闭返回结果流
            bufferedReader.close();
            result = new Result(code, stringBuffer.toString());
        } catch (Exception e) {
            result = new Result(500, "上传失败：" + e.getMessage());
        }
        return result;
    }

    static class Result {
        private int code;
        private String msg;
        private String content;

        public Result() {}

        public Result(int code, String msg, String content) {
            this.code = code;
            this.msg = msg;
            this.content = content;
        }

        public Result(int code, String resultStr) {
            this.code = code;

            this.content = resultStr;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Result{" + "code=" + code + ", msg='" + msg + '\'' + ", content='" + content + '\'' + '}';
        }
    }
}
