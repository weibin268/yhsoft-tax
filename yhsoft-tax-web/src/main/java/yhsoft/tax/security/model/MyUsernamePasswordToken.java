package yhsoft.tax.security.model;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by zhuang on 9/3/2017.
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken {

    private String validateCode;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public MyUsernamePasswordToken(String username, String password, String validateCode) {
        super(username, password);
        this.validateCode = validateCode;
    }
}
