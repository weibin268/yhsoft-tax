package com.yhsoft.ocr.model;

import java.util.List;

public class CommonResult extends BaseResult {
    public List<WordModel> data;

    public List<WordModel> getData() {
        return data;
    }

    public void setData(List<WordModel> data) {
        this.data = data;
    }


}
