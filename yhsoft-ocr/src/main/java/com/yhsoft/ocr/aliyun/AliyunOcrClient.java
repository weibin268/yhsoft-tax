package com.yhsoft.ocr.aliyun;

import com.alibaba.fastjson.JSONArray;
import com.yhsoft.ocr.OcrClient;
import com.yhsoft.ocr.aliyun.model.AdvancedBody;
import com.yhsoft.ocr.config.OcrProperties;
import com.yhsoft.ocr.model.CommonResult;
import com.yhsoft.ocr.model.IdCardResult;
import com.yhsoft.ocr.model.WordModel;
import com.yhsoft.ocr.util.HttpUtils;
import com.yhsoft.ocr.util.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AliyunOcrClient implements OcrClient {

    private OcrProperties ocrProperties;

    public AliyunOcrClient() {
        this.ocrProperties = new OcrProperties();
    }

    public CommonResult recognizeCommon(String base64Img) {
        String host = ocrProperties.getAliyunOcrApiAdvancedUrl();
        String path = "/ocrservice/advanced";
        String method = "POST";
        String appcode = ocrProperties.getAliyunOcrApiAdvancedAppCode();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        AdvancedBody advancedBody = new AdvancedBody();
        advancedBody.setImg(base64Img);
        String bodys = JsonUtils.toJsonString(advancedBody);
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String strResult = EntityUtils.toString(response.getEntity());
            Map mapResult = JsonUtils.parseMap(strResult);
            CommonResult result = new CommonResult();
            result.data = new ArrayList<WordModel>();
            Map map = JsonUtils.parseMap(strResult);
            JSONArray prism_wordsInfo = (JSONArray) map.get("prism_wordsInfo");
            for (Object item : prism_wordsInfo) {
                Map mapItem = (Map) item;
                String word = mapItem.get("word").toString();
                WordModel wordModel = new WordModel();
                wordModel.setWord(word);
                result.data.add(wordModel);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public IdCardResult recognizeIdCard(String base64Img) {
        return null;
    }
}
