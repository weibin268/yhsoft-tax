package yhsoft.tax.modules.core.controller;

import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.core.model.Organization;
import yhsoft.tax.modules.core.model.OrganizationExt;
import yhsoft.tax.modules.core.service.OrganizationService;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/2/2018.
 */
@Controller
@RequestMapping(value = "/core/organization")
public class OrganizationController extends BaseController {

    @Autowired
    OrganizationService organizationService;

    @RequestMapping(value = "organization_main")
    public String main(HttpServletRequest request)
    {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:organization:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:organization:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:organization:modify"));
        return "core/organization/organization_main";
    }


    @RequestMapping(value = "organization_edit")
    public String edit()
    {
        return "core/organization/organization_edit";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Object get(IdArgs args) {
        OrganizationExt model = organizationService.get(args.getId());
        return toMyJsonResult(model);
    }


    @RequestMapping(value = "save")
    @ResponseBody
    @RequiresPermissions(value = {"core:organization:add","core:organization:modify"},logical = Logical.OR)
    public Object save(Organization model) {
        organizationService.save(model);
        return toMyJsonResult(null);
    }


    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:organization:delete")
    public Object delete(IdArgs args) {
        organizationService.delete(args.getId());
        return toMyJsonResult(null);
    }

}
