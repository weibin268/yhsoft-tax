package yhsoft.tax.modules.base.controller;

import com.yhsoft.common.web.util.RequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuang on 3/25/2018.
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "error_404")
    public String error404() {
        return "error/error_404";
    }

    @RequestMapping(value = "error_other")
    public String errorOther(HttpServletRequest request) {
        Integer statusCode = RequestUtils.statusCode(request);
        request.setAttribute("statusCode", statusCode);
        return "error/error_other";
    }

}
