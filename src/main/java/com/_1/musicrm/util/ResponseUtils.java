package com._1.musicrm.util;  // 包路径必须匹配

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static Map<String, Object> createResponse(String key, Object value) {
        Map<String, Object> response = new HashMap<>();
        response.put(key, value);
        return response;
    }
    public static Map<String, Object> createResponse(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> response = new HashMap<>();
        response.put(key1, value1);
        response.put(key2, value2);
        return response;}
}