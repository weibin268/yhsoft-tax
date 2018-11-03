package yhsoft.tax.dbscript;

import com.zhuang.data.jdbc.JdbcUtils;
import org.junit.Test;

public class DbScriptExecutorTest {

    private DbScriptExecutor dbScriptExecutor = new DbScriptExecutor();

    @Test
    public void executeClear() {
        dbScriptExecutor.executeClear();
    }

    @Test
    public void executeCreate() {
        dbScriptExecutor.executeCreate();
    }

    @Test
    public void executeData() {
        dbScriptExecutor.executeData();
    }

    @Test
    public void executeAll() {
        dbScriptExecutor.executeAll();
    }

    @Test
    public void createDb() {
        String dbName = "upms";
        String sql = "CREATE SCHEMA `" + dbName + "` DEFAULT CHARACTER SET utf8 ;";
        JdbcUtils.executeNonQuery(sql);
    }
}