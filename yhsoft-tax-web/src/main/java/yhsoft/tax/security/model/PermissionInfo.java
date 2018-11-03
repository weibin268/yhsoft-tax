package yhsoft.tax.security.model;

/**
 * Created by zhuang on 8/26/2017.
 */
public class PermissionInfo {

    private String permissionCode;
    private String permissionExpression;
    private Integer permissionPriority;

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionExpression() {
        return permissionExpression;
    }

    public void setPermissionExpression(String permissionExpression) {
        this.permissionExpression = permissionExpression;
    }

    public Integer getPermissionPriority() {
        return permissionPriority;
    }

    public void setPermissionPriority(Integer permissionPriority) {
        this.permissionPriority = permissionPriority;
    }
}
