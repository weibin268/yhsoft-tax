package yhsoft.tax.security;

import com.yhsoft.common.web.util.ValidateCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yhsoft.tax.config.zhuang.ZhuangSystemProperties;
import yhsoft.tax.modules.core.model.User;
import yhsoft.tax.modules.core.service.PermissionService;
import yhsoft.tax.modules.core.service.SubsystemService;
import yhsoft.tax.modules.core.service.UserService;
import yhsoft.tax.security.model.MyUsernamePasswordToken;
import yhsoft.tax.security.model.PermissionInfo;
import yhsoft.tax.security.model.UserInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhuang on 8/22/2017.
 */
@Component
public class MyAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SubsystemService subsystemService;
    @Autowired
    private ZhuangSystemProperties zhuangSystemProperties;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String systemCode = zhuangSystemProperties.getSystemCode();
        UserInfo userInfo = (UserInfo) getAvailablePrincipal(principalCollection);
        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        String permissionId = null;
        if (systemCode != null && (!systemCode.equals(""))) {
            permissionId = subsystemService.getPermissionIdByCode(systemCode);
        }
        List<PermissionInfo> permissionInfos = permissionService.getUserPermissions(userInfo.getUserId(), permissionId);
        for (PermissionInfo permissionInfo :
                permissionInfos) {
            if (permissionInfo.getPermissionCode() != null && permissionInfo.getPermissionCode().trim().length() > 0) {
                permissions.add(permissionInfo.getPermissionCode());
            }
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roleNames);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MyUsernamePasswordToken usernamePasswordToken = (MyUsernamePasswordToken) authenticationToken;
        String loginId = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        String validateCode = usernamePasswordToken.getValidateCode();
        User user = userService.getByLoginId(loginId);
        String rightValidateCode = SecurityUtils.getSubject().getSession().getAttribute(ValidateCodeUtils.VALIDATE_CODE_KEY).toString();
        if (!rightValidateCode.equalsIgnoreCase(validateCode)) {
            throw new AuthenticationException("验证码错误！");
        }
        if (user == null) {
            throw new AuthenticationException("用户不存在！");
        }
        if (!password.equals(user.getPassword())) {
            throw new AuthenticationException("密码错误！");
        }
        UserInfo loginUserInfo = new UserInfo();
        loginUserInfo.setUserId(user.getId());
        loginUserInfo.setLoginId(user.getLoginId());
        loginUserInfo.setUserName(user.getName());
        loginUserInfo.setOrgId(user.getOrgId());
        loginUserInfo.setSystemCode(zhuangSystemProperties.getSystemCode());
        return new SimpleAuthenticationInfo(loginUserInfo, user.getPassword(), getName());
    }

    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
