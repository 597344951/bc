package com.baidu.ueditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.ueditor.define.ActionMap;

/**
 * 配置管理器
 * 
 * @author hancong03@baidu.com
 *
 */
public final class ConfigManager {

    private final String rootPath;
    private JSONObject jsonConfig = null;
    // 涂鸦上传filename定义
    private static final String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private static final String REMOTE_FILE_NAME = "remote";

    private static final String CONFIG_PATH = "ueditor/config.json";

    /*
     * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
     */
    private ConfigManager(String... rps) throws IOException {
        String rp = rps[0];
        rp = rp.replace("\\", "/");
        this.rootPath = rp;
        this.initEnv();
    }

    /**
     * 配置管理器构造工厂
     * 
     * @param rootPath 服务器根路径
     * @param contextPath 服务器所在项目路径
     * @param uri 当前访问的uri
     * @return 配置管理器实例或者null
     */
    public static ConfigManager getInstance(String arg1,String arg2,String arg3) {

        try {
            return new ConfigManager(arg1);
        } catch (Exception e) {
            return null;
        }

    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {

        return this.jsonConfig;

    }

    public Map<String, Object> getConfig(int type) throws JSONException {

        Map<String, Object> conf = new HashMap<>();
        String savePath = null;

        String isBase64 = "isBase64";
        String maxSize = "maxSize";
        String allowFiles = "allowFiles";
        String fieldName = "fieldName";
        switch (type) {
            case ActionMap.UPLOAD_FILE:
                conf.put(isBase64, "false");
                conf.put(maxSize, this.jsonConfig.getLong("fileMaxSize"));
                conf.put(allowFiles, this.getArray("fileAllowFiles"));
                conf.put(fieldName, this.jsonConfig.getString("fileFieldName"));
                savePath = this.jsonConfig.getString("filePathFormat");
                break;

            case ActionMap.UPLOAD_IMAGE:
                conf.put(isBase64, "false");
                conf.put(maxSize, this.jsonConfig.getLong("imageMaxSize"));
                conf.put(allowFiles, this.getArray("imageAllowFiles"));
                conf.put(fieldName, this.jsonConfig.getString("imageFieldName"));
                savePath = this.jsonConfig.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put(maxSize, this.jsonConfig.getLong("videoMaxSize"));
                conf.put(allowFiles, this.getArray("videoAllowFiles"));
                conf.put(fieldName, this.jsonConfig.getString("videoFieldName"));
                savePath = this.jsonConfig.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
                conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
                conf.put(maxSize, this.jsonConfig.getLong("scrawlMaxSize"));
                conf.put(fieldName, this.jsonConfig.getString("scrawlFieldName"));
                conf.put(isBase64, "true");
                savePath = this.jsonConfig.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
                conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put(maxSize, this.jsonConfig.getLong("catcherMaxSize"));
                conf.put(allowFiles, this.getArray("catcherAllowFiles"));
                conf.put(fieldName, this.jsonConfig.getString("catcherFieldName") + "[]");
                savePath = this.jsonConfig.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
                conf.put(allowFiles, this.getArray("imageManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
                conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
                break;

            case ActionMap.LIST_FILE:
                conf.put(allowFiles, this.getArray("fileManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
                conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
                break;
            default:
        }

        conf.put("savePath", savePath);
        conf.put("rootPath", this.rootPath);

        return conf;

    }

    private void initEnv() throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PATH);
        String configContent = this.readFile(in);

        try {
            this.jsonConfig = new JSONObject(configContent);
        } catch (Exception e) {
            this.jsonConfig = null;
        }

    }

    private String[] getArray(String key) throws JSONException {

        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];

        for (int i = 0, len = jsonArray.length(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;

    }

    private String readFile(InputStream in) throws IOException {

        StringBuilder builder = new StringBuilder();

        try {

            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bfReader = new BufferedReader(reader);

            String tmpContent = null;

            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }

            bfReader.close();

        } catch (UnsupportedEncodingException e) {
            // 忽略
        }

        return this.filter(builder.toString());

    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {

        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");

    }

}
