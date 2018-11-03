package yhsoft.tax.modules.log.controller;

import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.log.service.OperationLogService;
import yhsoft.tax.util.SecurityUtils;
import yhsoft.tax.modules.log.model.OperationLog;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 2018-09-05.
 */
@Controller
@RequestMapping(value = "/log/operationLog")
public class OperationLogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping(value = "operationLog_main")
    public String main(HttpServletRequest request) {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:operationLog:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:operationLog:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:operationLog:modify"));
        return "log/operationLog/operationLog_main";
    }

    @RequestMapping(value = "operationLog_edit")
    public String edit(HttpServletRequest request) {
        return "log/operationLog/operationLog_edit";
    }
 
    @ResponseBody
    @RequestMapping(value = "get")
    public Object get(IdArgs args) {
        OperationLog model = operationLogService.get(args.getId());
        return toMyJsonResult(model);
    }

    @ResponseBody
    @RequiresPermissions(value = {"core:operationLog:add", "core:operationLog:modify"}, logical = Logical.OR)
    @RequestMapping("save")
    public Object save(OperationLog model) {
        operationLogService.save(model);
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:operationLog:delete")
    public Object delete(IdArgs args) {
        operationLogService.delete(args.getId());
        return toMyJsonResult(null);
    }

}
