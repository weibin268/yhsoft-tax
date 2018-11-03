package yhsoft.tax.handler;

import com.zhuang.data.handler.DbExecutionContext;
import org.junit.Test;

public class EnvDbExecutionHandlerTest {

    @Test
    public void handle() {

        EnvDbExecutionHandler envDbExecutionHandler = new EnvDbExecutionHandler();

        DbExecutionContext dbExecutionContext=new DbExecutionContext();

        dbExecutionContext.setSql("select * from sys_user where a=@env{user.name} and b=@env{user.loginid}");

        envDbExecutionHandler.handle(dbExecutionContext);

        System.out.println(dbExecutionContext.getSql());
    }
}