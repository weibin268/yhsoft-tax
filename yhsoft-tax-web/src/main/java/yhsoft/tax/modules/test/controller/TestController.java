package yhsoft.tax.modules.test.controller;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yhsoft.tax.modules.core.model.UserExt;
import yhsoft.tax.modules.test.service.TestService;
import yhsoft.tax.modules.core.model.User;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 12/3/2017.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    RedisProperties redisProperties;
    @Autowired
    TestService testService;
    @Autowired
    DbAccessor dbAccessor;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        request.setAttribute("zwb", "庄伟斌");
        request.setAttribute("host", redisProperties.getHost());
        request.setAttribute("canDelete", SecurityUtils.hasPermission("core:user:delete"));
        request.setAttribute("canAdd", SecurityUtils.hasPermission("core:user:add"));
        request.setAttribute("canModify", SecurityUtils.hasPermission("core:user:modify"));
        return "test/index";
    }


    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Object get() {
        return new int[]{1, 2, 3, 4, 5, 6};
    }

    @RequestMapping(value = "session", method = RequestMethod.GET)
    public String session(HttpServletRequest request) {
        request.getSession().setAttribute("zwb", "庄伟斌");
        return "index";
    }

    @RequestMapping(value = "post")
    @ResponseBody
    public String post(User user) {
        return "{1}";
    }

    @RequestMapping("getObject")
    @ResponseBody
    public Object getObject() {
        return testService.getObject();
    }

    @RequestMapping("getExpression")
    @ResponseBody
    public Object getExpression() {
        return SecurityUtils.getPermissionExpression("zhuang");
    }

    @RequestMapping("getEnv")
    @ResponseBody
    public Object getEnv() {
        return dbAccessor.queryEntity("zhuang.upms.modules.test.mapper.Test.getUser", null, UserExt.class);
    }


    @RequestMapping("testPermission")
    @ResponseBody
    public Object testSecurity() {
        return dbAccessor.queryEntity("zhuang.upms.modules.test.mapper.Test.testPermission", null, UserExt.class);
    }

}
