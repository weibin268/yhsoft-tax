package yhsoft.tax.modules.pdd.service;

import com.yhsoft.common.util.EncryptionUtils;
import com.yhsoft.common.web.http.HttpClient;

/**
 * Created by zhuang on 10/5/2017.
 */
public class PddClient {

    private String mallId;

    private String serverUrl;

    private String clientSecret;

    private String dataType;

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public PddClient()
    {
        dataType="JSON";
    }

    public String sendRequest(RequestParams params){

        String strParams=processParams(params);

        HttpClient httpClient=new HttpClient();

        return httpClient.sendPost(serverUrl,strParams);
    }

    private String processParams(RequestParams params)
    {
        String result="";

        params.add("mall_id",mallId);
        params.add("data_type",dataType);
        params.add("timestamp",System.currentTimeMillis());

        String sign=clientSecret+params.toString4Sign()+clientSecret;
        sign= EncryptionUtils.encryptByMD5(sign).toUpperCase();
        params.add("sign",sign);

        result=params.toString4Url();

        return  result;
    }
}


