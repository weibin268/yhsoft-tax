package yhsoft.tax.modules.base.controller;

import com.yhsoft.common.web.model.MyJsonResult;
import com.yhsoft.common.web.util.RequestUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import yhsoft.tax.modules.core.service.UserService;
import yhsoft.tax.security.model.MyUsernamePasswordToken;
import yhsoft.tax.security.model.UserInfo;
import yhsoft.tax.modules.log.service.LoginLogService;
import yhsoft.tax.modules.log.model.LoginLog;
import yhsoft.tax.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhuang on 2/26/2018.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

    @Autowired
    LoginLogService loginLogService;

    @RequestMapping(value = "")
    public String index() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public MyJsonResult login(HttpServletRequest request, HttpServletResponse response) {
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String validateCode = request.getParameter("validateCode");
        password = UserService.encryptPassword(password);
        MyUsernamePasswordToken token = new MyUsernamePasswordToken(loginId, password, validateCode);
        Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            token.setRememberMe(true);
            //设置登录超时时间
            //currentUser.getSession().setTimeout(24 * 60 * 60 * 1000);//the default value is 1800000
            currentUser.login(token);
            //记录登录日志
            UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
            LoginLog loginLog = new LoginLog();
            loginLog.setUserId(userInfo.getUserId());
            loginLog.setUserLoginId(userInfo.getLoginId());
            loginLog.setUserName(userInfo.getUserName());
            loginLog.setClientIp(RequestUtils.getIpAddr(request));
            loginLog.setUserAgent(request.getHeader("user-agent"));
            loginLogService.add(loginLog);
        }
        MyJsonResult myJsonResult = new MyJsonResult();
        myJsonResult.setSuccess(true);
        myJsonResult.setValid(true);
        return myJsonResult;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/login";
    }

}
