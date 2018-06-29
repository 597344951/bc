package com.zltel.broadcast.incision.sola.utils;

import com.google.gson.GsonBuilder;
import com.zltel.broadcast.common.exception.RRException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonUtils {
    private JsonUtils() {}

    private static final String UTF_8 = "UTF-8";

    public static String serialization(Map<String, Object> param) {
        String json = JsonUtils.jsonParams(param);
        String data = "";
        try {
            data = new String(json.getBytes(UTF_8), UTF_8);
        } catch (UnsupportedEncodingException e) {
            RRException.makeThrow(e.getMessage());
        }

        return EncodeUtils.urlEncode(data, UTF_8);
    }

    public static String deserialization(String resourceData) {
        String data = EncodeUtils.urlDecode(resourceData);
        String result = "";
        try {
            result = new String(EncodeUtils.base64Decode(data), UTF_8);
        } catch (UnsupportedEncodingException e) {
            RRException.makeThrow(e.getMessage());
        }
        return result;
    }

    public static String jsonParams(Map<String, Object> map) {
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(map);
        String data = "";
        try {
            data = new String(json.getBytes(UTF_8), UTF_8);
        } catch (UnsupportedEncodingException e) {
            RRException.makeThrow(e.getMessage());
        }
        String result = "";
        try {
            result = EncodeUtils.base64Encode2String(data.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            RRException.makeThrow(e.getMessage());
        }

        return result;
    }
}
