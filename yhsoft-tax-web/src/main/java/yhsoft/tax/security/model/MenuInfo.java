package yhsoft.tax.security.model;

/**
 * Created by zhuang on 8/26/2017.
 */
public class MenuInfo {

    private String menuId;
    private String parentId;
    private String menuName;
    private String menuUrl;
    private boolean needPermission;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public boolean getNeedPermission() {
        return needPermission;
    }

    public void setNeedPermission(boolean needPermission) {
        this.needPermission = needPermission;
    }
}
