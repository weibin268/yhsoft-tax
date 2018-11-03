package yhsoft.tax.dbscript;

import com.yhsoft.common.util.FileUtils;
import com.zhuang.data.jdbc.JdbcUtils;

public class DbScriptExecutor {

    private String basePath = "/zhuang/upms/dbscript/";
    private String dbDialectName = "mysql";

    public void setDbDialectName(String dbDialectName) {
        this.dbDialectName = dbDialectName;
    }

    public void executeClear() {
        execute("clear");
    }

    public void executeCreate() {
        execute("create");
    }

    public void executeData() {
        execute("data");
    }

    public void executeAll() {
        executeCreate();
        executeData();
        executeClear();
    }

    private void execute(String type) {
        String path = DbScriptExecutor.class.getResource(getSqlFilePath(type)).getPath();
        String sql = FileUtils.readText(path);
        System.out.println("sql content:\n" + sql);
        JdbcUtils.executeNonQuery(sql);
    }

    private String getSqlFilePath(String type) {
        String sqlFilePath = basePath + dbDialectName + "." + type + ".sql";
        System.out.println("sql file:\n" + sqlFilePath);
        return sqlFilePath;
    }
}
