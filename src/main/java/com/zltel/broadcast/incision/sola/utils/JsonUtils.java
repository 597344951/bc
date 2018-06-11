package com.zltel.broadcast.incision.sola.utils;

import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonUtils {
    public  static String serialization(Map<String,Object> param){
        String  json=JsonUtils.jsonParams(param);
        String  data ="";
        try {
            data = new String(json.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return  EncodeUtils.urlEncode(data,"UTF-8");
    }
    public  static  String deserialization(String resourceData) {
        String data = EncodeUtils.urlDecode(resourceData);
        String result="";
        try {
             result = new String(EncodeUtils.base64Decode(data), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public  static  String jsonParams(Map<String,Object> map){
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(map);
        String  data ="";
        try {
            data = new String(json.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result= null;
        try {
            result = EncodeUtils.base64Encode2String(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return  result;
    }
}
