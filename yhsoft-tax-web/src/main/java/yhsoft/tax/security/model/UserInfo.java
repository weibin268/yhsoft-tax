package yhsoft.tax.security.model;

import java.io.Serializable;

/**
 * Created by zhuang on 8/24/2017.
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -405078125075978992L;
    private String userId;
    private String loginId;
    private String userName;
    private String orgId;
    private String systemCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", loginId='" + loginId + '\'' +
                ", userName='" + userName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", systemCode='" + systemCode + '\'' +
                '}';
    }

}
