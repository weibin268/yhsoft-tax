package yhsoft.tax.modules.setting.controller;

import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.setting.service.DictionaryService;
import yhsoft.tax.util.DictionaryUtils;
import yhsoft.tax.util.SecurityUtils;
import yhsoft.tax.modules.setting.model.DictionaryItem;
import yhsoft.tax.modules.setting.model.Dictionary;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuang
 * @create 6/8/18 7:04 PM
 **/
@Controller
@RequestMapping(value = "/setting/dictionary")
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "dictionary_main")
    public String main(HttpServletRequest request)
    {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("setting:dictionary:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("setting:dictionary:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("setting:dictionary:modify"));
        return "setting/dictionary/dictionary_main";
    }

    @RequestMapping(value = "dictionary_edit")
    public String edit()
    {
        return "setting/dictionary/dictionary_edit";
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Object get(IdArgs args) {
        Dictionary model = dictionaryService.get(args.getId());
        return toMyJsonResult(model);
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @RequiresPermissions(value = {"setting:dictionary:add","setting:dictionary:modify"},logical = Logical.OR)
    public Object save(Dictionary model) {
        dictionaryService.save(model);
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "setting:dictionary:delete")
    public Object delete(IdArgs args) {
        dictionaryService.delete(args.getId());
        return toMyJsonResult(null);
    }
    
    @RequestMapping(value = "dictionary_item_main")
    public String itemMain(HttpServletRequest request)
    {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("setting:dictionary:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("setting:dictionary:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("setting:dictionary:modify"));
        return "setting/dictionary/dictionary_item_main";
    }

    @RequestMapping(value = "dictionary_item_edit")
    public String itemEdit(HttpServletRequest request)
    {
        return "setting/dictionary/dictionary_item_edit";
    }

    @RequestMapping(value = "getItem")
    @ResponseBody
    public Object getItem(IdArgs args) {
        DictionaryItem model = dictionaryService.getItem(args.getId());
        return toMyJsonResult(model);
    }

    @RequestMapping(value = "saveItem")
    @ResponseBody
    @RequiresPermissions(value = {"setting:dictionary:add","setting:dictionary:modify"},logical = Logical.OR)
    public Object saveItem(DictionaryItem model) {
        dictionaryService.saveItem(model);
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "deleteItem")
    @ResponseBody
    @RequiresPermissions(value = "setting:dictionary:delete")
    public Object delete4Item(IdArgs args) {
        dictionaryService.deleteItem(args.getId());
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "getDictionaryItemInfoList")
    @ResponseBody
    public Object getDictionaryItemInfoList(@RequestParam(name = "code") String code) {
        return toMyJsonResult(DictionaryUtils.getItemList(code));
    }


}
