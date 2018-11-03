package yhsoft.tax.handler;

import com.zhuang.data.handler.CustomTagHandler;
import com.zhuang.data.handler.DbExecutionContext;
import com.zhuang.data.handler.DbExecutionHandler;
import yhsoft.tax.util.SecurityUtils;

/**
 * @author zhuang
 * @create 6/2/18 10:10 PM
 * 处理格式如下的自定义标签：
 * @permission{core:user:query ? and 1=1}
 **/
public class PermissionDbExecutionHandler implements DbExecutionHandler {

    private CustomTagHandler permissionTagHandler = new CustomTagHandler("permission") {
        @Override
        public String handleInternal(String tagContent) {
            String result;
            String[] tag = tagContent.split("\\?");
            String permissionCode = tag[0];
            String defaultValue = tag[1];
            String permissionExpression = SecurityUtils.getPermissionExpression(permissionCode.trim());
            if (permissionExpression != null && !permissionExpression.isEmpty()) {
                result = permissionExpression;
            } else {
                result = defaultValue;
            }
            return result;
        }
    };

    @Override
    public void handle(DbExecutionContext context) {
        context.setSql(permissionTagHandler.handle(context.getSql()));
    }

}
