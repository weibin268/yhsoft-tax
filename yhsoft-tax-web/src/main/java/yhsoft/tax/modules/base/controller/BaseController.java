package yhsoft.tax.modules.base.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhsoft.common.web.model.MyJsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zwb on 12/3/2017.
 */
public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public Object toMyJsonResult(Object data) {
        MyJsonResult myJsonResult = new MyJsonResult();
        myJsonResult.setSuccess(true);
        myJsonResult.setValid(true);
        myJsonResult.setData(data);
        return myJsonResult;
    }

    public <T> T getArgs(Class<T> argsClazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        T args = null;
        try {
            args = objectMapper.readValue(getRequest().getParameter("args"), argsClazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return args;
    }

    public HttpServletRequest getRequest()
    {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }
}
