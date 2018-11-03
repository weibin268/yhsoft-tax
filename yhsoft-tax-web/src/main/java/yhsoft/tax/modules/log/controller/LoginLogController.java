package yhsoft.tax.modules.log.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/12/2018.
 */
@Controller
@RequestMapping(value = "/log/loginLog")
public class LoginLogController {

    @RequestMapping(value = "loginLog_main")
    public String main(HttpServletRequest request) {
        return "log/loginLog/loginLog_main";
    }

}
