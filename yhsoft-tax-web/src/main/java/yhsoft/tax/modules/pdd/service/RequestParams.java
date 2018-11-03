package yhsoft.tax.modules.pdd.service;

import java.util.*;

/**
 * Created by zhuang on 10/5/2017.
 */
public class RequestParams {

    private Map<String,Object> params;

    public RequestParams()
    {
        params=new TreeMap<String, Object>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public void add(String name,Object value)
    {
        params.put(name,value);
    }

    public String toString4Sign()
    {
        String result="";

        Set<String> keys = params.keySet();

        for (String key :
                keys) {

            result=result+key+params.get(key);

        }
        return result;
    }

    public String toString4Url()
    {
        String result="";

        Set<String> keys = params.keySet();

        for (String key :
                keys) {

            result=result+key+"="+params.get(key)+"&";

        }

        if(result.length()>0)
        {
            result=result.substring(0,result.length()-1);
        }

        return result;
    }

}
