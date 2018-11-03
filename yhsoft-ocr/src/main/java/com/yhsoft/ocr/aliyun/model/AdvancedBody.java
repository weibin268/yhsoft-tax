package com.yhsoft.ocr.aliyun.model;

public class AdvancedBody {

    private String img;//图像数据：base64编码，要求base64编码后大小不超过4M，最短边至少15px，最长边最大4096px，支持jpg/png/bmp格式，和url参数只能同时存在一个
    private boolean prob;//是否需要识别结果中每一行的置信度，默认不需要。 true：需要 false：不需要
    private boolean charInfo;//是否需要单字识别功能，默认不需要。 true：需要 false：不需要
    private boolean rotate;//是否需要自动旋转功能，默认不需要。 true：需要 false：不需要
    private boolean table;//是否需要表格识别功能，默认不需要。 true：需要 false：不需要

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isProb() {
        return prob;
    }

    public void setProb(boolean prob) {
        this.prob = prob;
    }

    public boolean isCharInfo() {
        return charInfo;
    }

    public void setCharInfo(boolean charInfo) {
        this.charInfo = charInfo;
    }

    public boolean isRotate() {
        return rotate;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    public boolean isTable() {
        return table;
    }

    public void setTable(boolean table) {
        this.table = table;
    }
}
