package yhsoft.tax.spring;

import com.zhuang.data.handler.DbExecuteHandlerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import yhsoft.tax.handler.EnvDbExecutionHandler;
import yhsoft.tax.handler.PermissionDbExecutionHandler;

/**
 * @author zhuang
 * @create 7/7/18 11:24 PM
 **/
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args){
        DbExecuteHandlerFactory.addDbExecutionHandler(new PermissionDbExecutionHandler());
        DbExecuteHandlerFactory.addDbExecutionHandler(new EnvDbExecutionHandler());
    }

}
