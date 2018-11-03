package yhsoft.tax.modules.core.controller;

import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.core.service.SubsystemService;
import yhsoft.tax.modules.core.model.Subsystem;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/6/2018.
 */
@Controller
@RequestMapping(value = "/core/subsystem")
public class SubsystemController extends BaseController {

    @Autowired
    private SubsystemService subsystemService;

    @RequestMapping(value = "subsystem_main")

    public String main(HttpServletRequest request) {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:subsystem:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:subsystem:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:subsystem:modify"));
        return "core/subsystem/subsystem_main";
    }

    @RequestMapping(value = "subsystem_edit")
    public String edit() {
        return "core/subsystem/subsystem_edit";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Object get(IdArgs args) {
        Subsystem model = subsystemService.get(args.getId());
        return toMyJsonResult(model);
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @RequiresPermissions(value = {"core:subsystem:add","core:subsystem:modify"},logical = Logical.OR)
    public Object save(Subsystem model) {
        subsystemService.save(model);
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:subsystem:delete")
    public Object delete(IdArgs args) {
        subsystemService.delete(args.getId());
        return toMyJsonResult(null);
    }

}
