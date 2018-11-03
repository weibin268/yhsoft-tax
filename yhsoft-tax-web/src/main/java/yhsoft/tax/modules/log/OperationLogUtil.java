package yhsoft.tax.modules.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuang on 3/14/2018.
 */
public class OperationLogUtil {

    private static Logger logger = LoggerFactory.getLogger(OperationLogUtil.class);

    public static void log(String module, String action, Object data) {
        Object[] argArray = new Object[2];
        argArray[0] = module;
        argArray[1] = action;
        logger.info(data.toString(), argArray);
    }
}
