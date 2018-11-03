package com.yhsoft.common.web.restapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class BaseController {

    Gson gson = new GsonBuilder().serializeNulls().create();

    public Gson getGson() {
        return gson;
    }

    public Map getArgs(RestApiContext context)
    {
        return gson.fromJson(context.getArgs(), Map.class);
    }

    public <T extends BaseArgs> T getArgs(RestApiContext context, Class<T> argsClass) {

        return getArgsInternal(context, argsClass);
    }

    public <T extends BaseArgs> T getArgs(RestApiContext context, Type argsType) {

        return getArgsInternal(context, argsType);
    }

    private <T extends BaseArgs> T getArgsInternal(RestApiContext context, Object argsObject) {

        if (context.getArgs() == null || context.getArgs() == "") {

            throw new RuntimeException("“args”参数不能为空！");
        }

        T result;

        if (argsObject instanceof Class) {

            result = gson.fromJson(context.getArgs(), (Class<T>) argsObject);

        } else if (argsObject instanceof Type) {

            result = gson.fromJson(context.getArgs(), (Type) argsObject);

        } else {
            throw new RuntimeException("argsObject的类型必须为“Class”或“Type”!");
        }

        result.init();

        return result;
    }

    public boolean toJsonResult(RestApiContext context, Object obj) throws IOException {

        String strResult = gson.toJson(obj);

        context.getResponse().setHeader("Content-type", "text/plain;charset=UTF-8");
        context.getResponse().setCharacterEncoding("UTF-8");
        context.getResponse().getWriter().append(strResult);

        return false;

    }

}
