package yhsoft.tax.modules.pdd.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhuang on 10/5/2017.
 */
public class PddService {

    private PddClient pddClient;

    public PddService() {
        pddClient = new PddClient();
        pddClient.setServerUrl("http://open.yangkeduo.com/api/router");
        pddClient.setMallId("");
        pddClient.setClientSecret("");

    }

    public List<String> getOrderSnList(long startUpdatedAt, long endUpdatedAt) {
        RequestParams params = new RequestParams();

/*
        params.add("type","pdd.order.number.list.get");
        params.add("order_status",orderStatus);
*/

        params.add("type", "pdd.order.number.list.increment.get");
        params.add("is_lucky_flag", 1);
        params.add("refund_status", 1);
        params.add("order_status", 2);
        params.add("start_updated_at", startUpdatedAt);
        params.add("end_updated_at", endUpdatedAt);
        params.add("page_size", 100);

        String requestResult = pddClient.sendRequest(params);

        Gson gson = new GsonBuilder().serializeNulls().create();

        Map map = gson.fromJson(requestResult, Map.class);

        List<Map> orderSnList = (List) ((Map) map.get("order_sn_increment_get_response")).get("order_sn_list");

        List<String> result = new ArrayList<String>();

        for (Map item :
                orderSnList) {
            result.add(item.get("order_sn").toString());
        }

        return result;
    }

    public List<String> getOrderSnList(Date startDate, Date endDate) {

        List<String> result = new ArrayList<String>();

        int startSeconds = (int) (startDate.getTime() / 1000);

        int endSeconds = (int) (endDate.getTime() / 1000);

        while (startSeconds < endSeconds) {

            List<String> temp = getOrderSnList(startSeconds, (startSeconds = startSeconds + 30 * 60));

            result.addAll(temp);
        }

        result = result.stream().distinct().collect(Collectors.toList());

        result = result.stream().sorted((a,b)->{return a.compareTo(b);}).collect(Collectors.toList());

        System.out.println(result);
        System.out.println(result.size());

        return result;
    }

    public Map getOrderInfo(String orderSn) {
        RequestParams params = new RequestParams();

        params.add("type", "pdd.order.information.get");

        params.add("order_sn", orderSn);

        String requestResult = pddClient.sendRequest(params);

        Gson gson = new GsonBuilder().serializeNulls().create();

        Map map = gson.fromJson(requestResult, Map.class);

        return map;
    }

    public Map getGoodsInfo(String goodsId) {
        RequestParams params = new RequestParams();

        params.add("type", "pdd.goods.information.get");

        params.add("goods_id", goodsId);

        String requestResult = pddClient.sendRequest(params);

        Gson gson = new GsonBuilder().serializeNulls().create();

        Map map = gson.fromJson(requestResult, Map.class);

        return map;
    }

}
