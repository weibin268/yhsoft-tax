package yhsoft.tax.spring;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhuang on 3/25/2018.
 */
@Component
public class MyErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        String viewName = "error/error_other";
        if (status == HttpStatus.NOT_FOUND) {
            viewName = "error/error_404";
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("status", status.value());
        modelAndView.addObject("error", model.get("error"));
        modelAndView.addObject("message", model.get("message"));
        modelAndView.addObject("path", model.get("path"));
        modelAndView.addObject("timestamp", model.get("timestamp"));
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}
