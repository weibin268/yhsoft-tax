package yhsoft.tax.modules.pdd.service;

import yhsoft.tax.modules.pdd.model.PurchaseOrderInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * Created by zhuang on 10/5/2017.
 */

public class PurchaseService {

    public List<PurchaseOrderInfo> getSummaryInfoList(Date startDate, Date endDate) {

        List<PurchaseOrderInfo> result = new ArrayList<PurchaseOrderInfo>();

        PddService pddService = new PddService();

        List<String> orderSnList = pddService.getOrderSnList(startDate, endDate);

        for (String orderSn :
                orderSnList) {

            Map mapOrderInfo = pddService.getOrderInfo(orderSn);

            Map orderInfo = (Map) ((Map) mapOrderInfo
                    .get("order_info_get_response"))
                    .get("order_info");

            List<Map> itemList = (List<Map>) orderInfo.get("item_list");

            for (Map item :
                    itemList) {

                PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();

                purchaseOrderInfo.setGoodsName(item.get("goods_spec").toString());
                purchaseOrderInfo.setGoodsCount((int) Float.parseFloat(item.get("goods_count").toString()));
                purchaseOrderInfo.setGoodsImage(item.get("goods_img").toString());

                result.add(purchaseOrderInfo);
            }

        }

        List<PurchaseOrderInfo> groupResult = new ArrayList<PurchaseOrderInfo>();

        result.stream().collect(groupingBy(PurchaseOrderInfo::getGoodsName))
                .forEach((goodsName, subList) -> {
                    PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
                    purchaseOrderInfo.setGoodsName(goodsName);
                    purchaseOrderInfo.setGoodsCount(subList.stream().mapToInt(p -> p.getGoodsCount()).sum());
                    purchaseOrderInfo.setGoodsImage(subList.stream().map(p -> p.getGoodsImage()).findFirst().get());
                    groupResult.add(purchaseOrderInfo);
                });

        List<PurchaseOrderInfo> sortedResult = groupResult.stream().sorted((a, b) -> {
            return a.getGoodsName().compareTo(b.getGoodsName());
        }).collect(toList());

        return sortedResult;
    }
}
