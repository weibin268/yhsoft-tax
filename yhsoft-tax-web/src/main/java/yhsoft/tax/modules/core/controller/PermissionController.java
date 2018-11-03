package yhsoft.tax.modules.core.controller;

import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.core.service.PermissionService;
import yhsoft.tax.modules.core.model.Permission;
import yhsoft.tax.modules.core.model.PermissionExt;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/2/2018.
 */
@Controller
@RequestMapping(value = "/core/permission")
public class PermissionController extends BaseController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "permission_main")
    public String main(HttpServletRequest request)
    {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:permission:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:permission:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:permission:modify"));
        return "core/permission/permission_main";
    }

    @RequestMapping(value = "permission_edit")
    public String edit()
    {
        return "core/permission/permission_edit";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Object get(IdArgs args) {
        PermissionExt model = permissionService.get(args.getId());
        return toMyJsonResult(model);
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @RequiresPermissions(value = {"core:permission:add","core:permission:modify"},logical = Logical.OR)
    public Object save(Permission model) {
        permissionService.save(model);
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:permission:delete")
    public Object delete(IdArgs args) {
        permissionService.delete(args.getId());
        return toMyJsonResult(null);
    }

}
