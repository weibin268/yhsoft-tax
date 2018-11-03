package com.yhsoft.ocr.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    @Test
    public void toJsonString() {
        Map map = new HashMap();
        map.put("zwb", "庄伟斌");
        System.out.println(JsonUtils.toJsonString(map));
        Map map2 = JsonUtils.parseMap("{\"zwb\":\"庄伟斌\"}");
        System.out.println(map2);
    }

    @Test
    public void parseMap() {
        String filePath = "D:\\zhuang\\temp\\test.json";
        String fileContent = FileUtils.readText(filePath);
        Map map = JsonUtils.parseMap(fileContent);
        JSONArray prism_wordsInfo = (JSONArray) map.get("prism_wordsInfo");
        for (Object item : prism_wordsInfo) {
            Map mapItem = (Map) item;
            System.out.println( mapItem.get("word"));
        }
        System.out.println(map);
    }

}