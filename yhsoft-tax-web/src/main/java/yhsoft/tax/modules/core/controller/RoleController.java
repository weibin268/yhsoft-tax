package yhsoft.tax.modules.core.controller;

import com.yhsoft.common.web.restapi.args.IdAndIdListArgs;
import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.core.service.RoleService;
import yhsoft.tax.modules.core.model.Role;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/2/2018.
 */
@Controller
@RequestMapping(value = "/core/role")
public class RoleController extends BaseController {

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "role_main")
    public String main(HttpServletRequest request)
    {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:role:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:role:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:role:modify"));
        request.setAttribute("canSetPermission", SecurityUtils.hasPermission("core:role:setpermission"));
        request.setAttribute("canSetUser", SecurityUtils.hasPermission("core:role:setuser"));
        return "core/role/role_main";
    }

    @RequestMapping(value = "role_edit")
    public String edit()
    {
        return "core/role/role_edit";
    }

    @RequestMapping(value = "role_permission_main")
    public String permissionMain()
    {
        return "core/role/role_permission_main";
    }

    @RequestMapping(value = "role_user_main")
    public String userMain()
    {
        return "core/role/role_user_main";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Object get(IdArgs args) {
        Role model = roleService.get(args.getId());
        return toMyJsonResult(model);
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @RequiresPermissions(value = {"core:role:add","core:role:modify"},logical = Logical.OR)
    public Object save(Role model) {
        roleService.save(model);
        return toMyJsonResult(null);

    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:role:delete")
    public Object delete(IdArgs args) {
        roleService.delete(args.getId());
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "addUsers")
    @ResponseBody
    @RequiresPermissions(value = "core:role:setuser")
    public Object addUsers() {
        IdAndIdListArgs args = getArgs(IdAndIdListArgs.class);
        for (String userId:args.getIdList()) {
            roleService.addUser(args.getId(),userId);
        }
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "deleteUsers")
    @ResponseBody
    @RequiresPermissions(value = "core:role:setuser")
    public Object deleteUsers() {
        IdAndIdListArgs args = getArgs(IdAndIdListArgs.class);
        for (String userId:args.getIdList()
                ) {
            roleService.deleteUser(args.getId(),userId);
        }
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "setPermissions")
    @ResponseBody
    @RequiresPermissions(value = "core:role:setpermission")
    public Object setPermissions() {
        IdAndIdListArgs args = getArgs(IdAndIdListArgs.class);
        roleService.setPermissions(args.getId(),args.getIdList());
        return toMyJsonResult(null);
    }

}
