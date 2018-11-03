package yhsoft.tax.controller.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yhsoft.tax.modules.log.OperationLogUtil;

/**
 * Created by zhuang on 3/12/2018.
 */
public class OperationLogControllerTest {

    @Test
    public void test()
    {
        Logger logger= LoggerFactory.getLogger(getClass());

        OperationLogUtil.log("aa","a","b");

        logger.error("error01",new Exception("myerror!"));
    }

}