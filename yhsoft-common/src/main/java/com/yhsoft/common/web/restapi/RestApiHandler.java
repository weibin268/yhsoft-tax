package com.yhsoft.common.web.restapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.yhsoft.common.web.exception.MyCheckException;
import com.yhsoft.common.web.model.MyJsonResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class RestApiHandler {

    private static Logger logger = LoggerFactory.getLogger(RestApiHandler.class);

    public static final String CONTROLER_SUFFIX = "Controller";

    public static final String ARGS_NAME = "args";

    public static void handle(HttpServletRequest request, HttpServletResponse response, String basePkgName)
            throws JsonIOException, IOException {
        if (request.getPathInfo() != null) {
            //PathInfo正确格式：/moduleName/ControllerName-actionName
            String pathInfo = request.getPathInfo().substring(1, request.getPathInfo().length());//去掉第一个“/”字符
            String[] arrPath = pathInfo.split("\\/");

            if (arrPath.length < 1) {
                throw new RuntimeException("PathInfo:“" + request.getPathInfo() + "”格式不正确！");
            } else {
                String moduleName = arrPath.length > 1 ? "." + arrPath[0] : "";
                String controllerName = arrPath[arrPath.length - 1].split("-")[0];
                String actionName = arrPath[arrPath.length - 1].split("-")[1];
                String controllerPkgName = basePkgName + moduleName;

                handle(request, response, controllerPkgName, controllerName, actionName);
            }
        }
    }

    public static void handle(HttpServletRequest request, HttpServletResponse response, String controllerPkgName, String controllerName, String actionName)
            throws JsonIOException, IOException {

        logger.info("controllerPkgName:"+controllerPkgName+"|controllerName:"+controllerName+"|actionName:"+actionName);

        MyJsonResult jsonResult = new MyJsonResult();

        String args = request.getParameter(ARGS_NAME);

        jsonResult.setSuccess(true);
        jsonResult.setValid(true);

        try {

            String controllerFullName = getControllerFullName(controllerPkgName, controllerName);

            Class controllerClass = Class.forName(controllerFullName);

            Method actionMethod = controllerClass.getMethod(actionName, RestApiContext.class);

            RestApiContext context = new RestApiContext();
            context.setRequest(request);
            context.setResponse(response);
            context.setResult(jsonResult);
            context.setAction(controllerPkgName + "." + controllerName + "." + actionName);
            context.setArgs(args);

            Object objActionResult = actionMethod.invoke(controllerClass.newInstance(), context);

            if (objActionResult != null && objActionResult instanceof Boolean) {
                if ((Boolean) objActionResult == false) {
                    return;
                }
            }

        } catch (Throwable ex) {

            Throwable innerEx = ex.getCause();

            if (innerEx != null) {
                ex = innerEx;
            }

            if (ex instanceof MyCheckException) {
                jsonResult.setValid(false);
            } else {
                jsonResult.setSuccess(false);
                jsonResult.setValid(false);
                jsonResult.setData(ExceptionUtils.getStackTrace(ex));
            }

            logger.error("RestApiHandler handle error!",ex);

            jsonResult.setMessage(ex.getMessage());

        } finally {

        }

        response.setHeader("Content-type", "text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().serializeNulls().create();
        gson.toJson(jsonResult, response.getWriter());

    }

    private static String getControllerFullName(String pkgName, String controllerName) {

        return pkgName + "." + controllerName + CONTROLER_SUFFIX;

    }

}
