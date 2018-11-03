package yhsoft.tax.modules.core.model;

/**
 * Created by zhuang on 8/17/2017.
 */
public class UserExt extends User
{
    private String orgName;

    private String imgFileId;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getImgFileId() {
        return imgFileId;
    }

    public void setImgFileId(String imgFileId) {
        this.imgFileId = imgFileId;
    }
}
