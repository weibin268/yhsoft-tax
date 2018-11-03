package yhsoft.tax.spring;

import com.yhsoft.common.web.model.MyJsonResult;
import com.yhsoft.common.web.util.RequestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhuang on 3/25/2018.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage(), e);
        if (RequestUtils.isAjaxRequest(request)) {
            MyJsonResult restApiJsonResult = new MyJsonResult();
            restApiJsonResult.setValid(false);
            restApiJsonResult.setSuccess(false);
            restApiJsonResult.setMessage(e.getMessage());
            restApiJsonResult.setData(ExceptionUtils.getStackTrace(e));
            return restApiJsonResult;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("url", request.getRequestURL());
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("detail", ExceptionUtils.getStackTrace(e));
            modelAndView.setViewName("error/error_500");
            return modelAndView;
        }
    }

}
