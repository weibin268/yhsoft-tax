package yhsoft.tax.modules.common.controller;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.model.PageInfo;
import com.yhsoft.common.web.util.UrlParamUtils;
import com.yhsoft.common.web.restapi.results.EasyUIPaginationResult;
import com.yhsoft.common.web.restapi.results.LayUIPaginationResult;
import com.zhuang.data.util.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuang on 3/4/2018.
 */
@Controller
@RequestMapping(value = "/common/pagination")
public class PaginationController extends BaseController {

    @Autowired
    private DbAccessor dbAccessor;

    @RequestMapping(value = "getEasyUIDataGridPage")
    @ResponseBody
    public Object getEasyUIDataGridPage(HttpServletRequest request, HttpServletResponse response) {
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        String sql = request.getParameter("sql");
        String order = request.getParameter("order");
        String param = request.getParameter("param");
        Map<String, Object> mapParam = UrlParamUtils.toMap(param);
        EasyUIPaginationResult<Object> result = new EasyUIPaginationResult<Object>();
        String orderClause = "Id";
        if (order != null && !order.equals("")) {
            orderClause = SqlUtils.removeSqlKeyWord(order);
        }
        PageInfo pageInfo = new PageInfo(page, rows, orderClause);
        List<Object> models = dbAccessor.pageQueryEntities(sql, pageInfo, mapParam, Object.class);
        result.setRows(models);
        result.setTotal(pageInfo.getTotalRowCount());
        return result;
    }

    @RequestMapping(value = "getLayUITablePage")
    @ResponseBody
    public Object getLayUITablePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("limit"));
        String sql = request.getParameter("sql");
        String order = request.getParameter("order");
        String param = request.getParameter("param");
        Map<String, Object> mapParam = UrlParamUtils.toMap(param);
        LayUIPaginationResult<Object> result = new LayUIPaginationResult<Object>();
        String orderClause = "Id";
        if (order != null && !order.equals("")) {
            orderClause = order;
        }
        PageInfo pageInfo = new PageInfo(page, rows, orderClause);
        List<Object> models = dbAccessor.pageQueryEntities(sql, pageInfo, mapParam, Object.class);
        result.setData(models);
        result.setCount(pageInfo.getTotalRowCount());
        result.setCode(0);
        return result;
    }

}
