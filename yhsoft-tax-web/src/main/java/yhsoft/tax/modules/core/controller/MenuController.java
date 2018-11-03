package yhsoft.tax.modules.core.controller;

import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.core.service.MenuService;
import yhsoft.tax.modules.core.model.Menu;
import yhsoft.tax.modules.core.model.MenuExt;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/2/2018.
 */
@Controller
@RequestMapping(value = "/core/menu")
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    @RequestMapping(value = "menu_main")
    public String main(HttpServletRequest request)
    {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:menu:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:menu:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:menu:modify"));
        return "core/menu/menu_main";
    }


    @RequestMapping(value = "menu_edit")
    public String edit()
    {
        return "core/menu/menu_edit";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Object get(IdArgs args) {
        MenuExt model = menuService.get(args.getId());
        return toMyJsonResult(model);
    }


    @RequestMapping(value = "save")
    @ResponseBody
    @RequiresPermissions(value = {"core:menu:add","core:menu:modify"},logical = Logical.OR)
    public Object save(Menu menu) {
        menuService.save(menu);
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:menu:delete")
    public Object delete(IdArgs args) {
        menuService.delete(args.getId());
        return toMyJsonResult(null);
    }
}
