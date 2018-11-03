package yhsoft.tax.aop;

import com.yhsoft.common.util.ReflectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yhsoft.tax.modules.core.model.Menu;
import yhsoft.tax.modules.core.service.MenuService;
import yhsoft.tax.modules.log.model.OperationLog;
import yhsoft.tax.modules.log.service.OperationLogService;
import yhsoft.tax.util.SecurityUtils;
import yhsoft.tax.util.SpringMvcUtils;
import yhsoft.tax.security.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author zhuang
 * @create 6/18/18 6:55 PM
 **/
//@Aspect
//@Component
public class OperationLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<String> actionList = Arrays.asList("get", "save", "delete");

    @Autowired
    private MenuService menuService;

    @Autowired
    private OperationLogService operationLogService;

    @Pointcut("execution(* yhsoft.tax.modules.*.service.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        //System.out.println("AOP拦截:" + signature.getDeclaringTypeName() + "." + signature.getName());
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        //System.out.println("AOP拦截:" + signature.getDeclaringTypeName() + "." + signature.getName());
    }

    @Around("log()")
    public Object doOperationLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        if (!actionList.contains(signature.getName())) {
            return proceedingJoinPoint.proceed();
        }
        OperationLog operationLog = new OperationLog();
        operationLog.setModule(signature.getDeclaringTypeName());
        operationLog.setModuleName(getModuleName());
        operationLog.setAction(signature.getName());
        //根据Id判断“保存”操作是“新增”还是“新增”
        if (signature.getName().equals("save")) {
            operationLog.setAction(getAction4Save(proceedingJoinPoint));
        }
        operationLog.setActionName(getActionName(operationLog.getAction()));
        //将第一个参数作为DataInfo进行保存
        if (proceedingJoinPoint.getArgs().length == 1) {
            String firstArgs = proceedingJoinPoint.getArgs()[0].toString();
            int maxDataInfoLength = 1000;
            operationLog.setDataInfo(firstArgs.length() > maxDataInfoLength ? firstArgs.substring(0, maxDataInfoLength) : firstArgs);
        }
        UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
        if (userInfo != null) {
            operationLog.setUserId(userInfo.getUserId());
            operationLog.setUserLoginId(userInfo.getLoginId());
            operationLog.setUserName(userInfo.getUserName());
        }
        Object result = proceedingJoinPoint.proceed();
        operationLogService.add(operationLog);
        return result;
    }

    private String getAction4Save(ProceedingJoinPoint proceedingJoinPoint) {
        String result = null;
        if (proceedingJoinPoint.getArgs().length == 1) {
            String idFieldName = "id";
            Object firstArgs = proceedingJoinPoint.getArgs()[0];
            if (ReflectionUtils.hasField(firstArgs.getClass(), idFieldName)) {
                if (Strings.isEmpty(ReflectionUtils.getFieldValue(firstArgs, firstArgs.getClass(), idFieldName).toString())) {
                    result = "add";
                } else {
                    result = "update";
                }
            }
        }
        return result;
    }

    private String getModuleName() {
        String result = null;
        HttpServletRequest request = SpringMvcUtils.getRequest();
        String menuUrl = request.getServletPath().replaceAll(request.getContextPath(), "");
        menuUrl = menuUrl.substring(0, menuUrl.lastIndexOf("/"));
        List<Menu> menuList = menuService.getAll();
        String finalMenuUrl = menuUrl;
        Optional<Menu> menu = menuList.stream().filter(c ->
        {
            if (c.getUrl() != null) {
                return c.getUrl().contains(finalMenuUrl);
            } else {
                return false;
            }
        }).findFirst();
        if (menu.isPresent()) {
            result = getMenuFullName(menuList, menu.get());
        }
        return result;
    }

    private String getMenuFullName(List<Menu> menuList, Menu menu) {
        StringBuilder sb = new StringBuilder();
        String[] menuIds = menu.getFullPath().split("\\.");
        for (int i = 0; i < menuIds.length; i++) {
            if (i == 0) continue;
            String menuId = menuIds[i];
            sb.append(menuList.stream().filter(c -> c.getId().equals(menuId)).findFirst().get().getName());
            if (i < (menuIds.length - 1)) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private String getActionName(String action) {
        if (action.equals("get")) {
            return "查看";
        } else if (action.equals("add")) {
            return "新增";
        } else if (action.equals("update")) {
            return "修改";
        } else if (action.equals("delete")) {
            return "删除";
        } else {
            return action;
        }
    }
}
