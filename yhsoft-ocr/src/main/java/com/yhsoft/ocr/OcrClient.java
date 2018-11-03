package com.yhsoft.ocr;

import com.yhsoft.ocr.model.CommonResult;
import com.yhsoft.ocr.model.IdCardResult;

public interface OcrClient {
    CommonResult recognizeCommon(String base64Img);

    IdCardResult recognizeIdCard(String base64Img);
}
