package yhsoft.tax.modules.core.model;

/**
 * Created by zhuang on 8/12/2017.
 */
public class OrganizationExt extends  Organization{

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
