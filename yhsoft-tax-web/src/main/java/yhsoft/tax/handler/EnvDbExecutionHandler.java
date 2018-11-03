package yhsoft.tax.handler;

import com.zhuang.data.handler.CustomTagHandler;
import com.zhuang.data.handler.DbExecutionContext;
import com.zhuang.data.handler.DbExecutionHandler;
import yhsoft.tax.security.model.UserInfo;
import com.yhsoft.common.util.ReflectionUtils;
import yhsoft.tax.util.SecurityUtils;

/**
 * @author zhuang
 * @create 6/2/18 10:10 PM
 * 处理格式如下的自定义标签：
 * @env{user.userId}
 **/
public class EnvDbExecutionHandler implements DbExecutionHandler {

    private CustomTagHandler envTagHandler = new CustomTagHandler("env") {
        @Override
        public String handleInternal(String tagContent) {
            return handleEnv(tagContent.trim());
        }
    };

    @Override
    public void handle(DbExecutionContext context) {
        context.setSql(envTagHandler.handle(context.getSql()));
    }

    private String handleEnv(String tagContent) {
        String[] env = tagContent.split("\\.");
        String envName = env[0];
        String envFieldName = env[1];
        if (envName.equals("user")) {
            return handleEnv4User(envFieldName);
        }
        throw new RuntimeException("error envName:" + envName + "!");
    }

    private String handleEnv4User(String envFieldName) {
        UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
        return ReflectionUtils.getFieldValue(userInfo, userInfo.getClass(), envFieldName).toString();
    }

}
