package yhsoft.tax.modules.common.controller;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuang on 3/4/2018.
 */

@Controller
@RequestMapping(value = "common/ztree")
public class ZTreeController extends BaseController {

    @Autowired
    private DbAccessor dbAccessor;

    @RequestMapping(value = "getNodes")
    @ResponseBody
    public Object getNodes(HttpServletRequest request, HttpServletResponse response)
    {
        String sqlId = request.getParameter("sqlId");
        String pId = request.getParameter("pId");
        String isAsync = request.getParameter("isAsync");
        String bizKey = request.getParameter("bizKey");
        Map<String, String> mapParams = new HashMap<String, String>();
        if (pId != null) {
            mapParams.put("pId", pId);
        }
        if(bizKey!=null && !bizKey.equals(""))
        {
            mapParams.put("bizKey",bizKey);
        }
        List<Map> nodes = dbAccessor.queryEntities(sqlId, mapParams, Map.class);
        if (isAsync != null && isAsync.equals("1")) {
            for (Map node : nodes) {
                pId = node.get("id").toString();
                mapParams.put("pId", pId);
                List<Map> subNodes = dbAccessor.queryEntities(sqlId, mapParams, Map.class);
                boolean isParent = false;
                if (subNodes.size() > 0) {
                    isParent = true;
                }
                node.put("isParent", isParent);
            }
        }
        return nodes;
    }

}
