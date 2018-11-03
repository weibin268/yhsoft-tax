package yhsoft.tax.modules.core.controller;

import com.zhuang.fileupload.FileUploadManager;
import com.zhuang.fileupload.model.SysFileUpload;
import com.yhsoft.common.web.restapi.args.IdArgs;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.base.controller.BaseController;
import yhsoft.tax.modules.core.service.UserService;
import yhsoft.tax.modules.core.model.User;
import yhsoft.tax.modules.core.model.UserExt;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by zhuang on 3/2/2018.
 */
//@Controller
@RequestMapping(value = "/core/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadManager fileUploadManager;

    @RequestMapping(value = "user_main")
    public String main(HttpServletRequest request) {
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:user:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:user:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:user:modify"));
        request.setAttribute("canResetPasswd", SecurityUtils.hasPermission("core:user:resetpasswd"));
        return "core/user/user_main";
    }

    @RequestMapping(value = "user_edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("tempId", UUID.randomUUID().toString());
        return "core/user/user_edit";
    }

    @RequestMapping(value = "user_passwd_change")
    public String passwdChange(HttpServletRequest request) {
        return "core/user/user_passwd_change";
    }

    @RequestMapping(value = "user_passwd_reset")
    public String passwdReset(HttpServletRequest request) {
        return "core/user/user_passwd_reset";
    }

    @RequestMapping(value = "user_base_info")
    public String baseInfo(HttpServletRequest request,@RequestParam(value = "id") String id) {
        UserExt user = userService.get(id);
        SysFileUpload sysFileUpload = fileUploadManager.getSysFileUploadFirst(user.getId());
        if (sysFileUpload != null) {
            user.setImgFileId(sysFileUpload.getId());
        }
        request.setAttribute("user",user);
        return "core/user/user_base_info";
    }

    @ResponseBody
    @RequestMapping(value = "get")
    public Object get(IdArgs args) {
        UserExt user = userService.get(args.getId());
        SysFileUpload sysFileUpload = fileUploadManager.getSysFileUploadFirst(user.getId());
        if (sysFileUpload != null) {
            user.setImgFileId(sysFileUpload.getId());
        }
        return toMyJsonResult(user);
    }

    @ResponseBody
    @RequiresPermissions(value = {"core:user:add", "core:user:modify"}, logical = Logical.OR)
    @RequestMapping("save")
    public Object save(User user, @RequestParam(value = "imgFileId") String imgFileId) {
        userService.save(user);
        fileUploadManager.submit(imgFileId, user.getId());
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresPermissions(value = "core:user:delete")
    public Object delete(IdArgs args) {
        userService.delete(args.getId());
        return toMyJsonResult(null);
    }

    @RequestMapping(value = "changePassword")
    @ResponseBody
    public Object changePassword(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "currentPassword") String currentPassword,
                                 @RequestParam(value = "newPassword") String newPassword) {
        userService.changePassword(id,currentPassword,newPassword);
        return toMyJsonResult(null);
    }


    @RequestMapping(value = "resetPassword")
    @ResponseBody
    public Object resetPassword(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "newPassword") String newPassword) {
        userService.resetPassword(id,newPassword);
        return toMyJsonResult(null);
    }


}
