package yhsoft.tax.modules.pdd.model;

/**
 * Created by zhuang on 10/5/2017.
 */
public class PurchaseOrderInfo {

    private int seq;

    private String goodsName;

    private int goodsCount;

    private String goodsImage;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    @Override
    public String toString() {
        return "PurchaseOrderInfo{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", goodsImage='" + goodsImage + '\'' +
                '}';
    }
}
