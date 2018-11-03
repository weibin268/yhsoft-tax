package com.yhsoft.ocr.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonUtils {
    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static Map parseMap(String json) {
        return JSONObject.parseObject(json);
    }
}
