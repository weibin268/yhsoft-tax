package yhsoft.tax.util;

import com.yhsoft.common.web.exception.MyCheckException;
import com.zhuang.data.DbAccessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yhsoft.tax.modules.core.service.MenuService;
import yhsoft.tax.modules.core.service.SubsystemService;
import yhsoft.tax.spring.ApplicationContextHolder;
import yhsoft.tax.security.MyAuthorizingRealm;
import yhsoft.tax.security.model.PermissionInfo;
import yhsoft.tax.security.model.UserInfo;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuang on 8/29/2017.
 */
@Component
public class SecurityUtils {

    private static SecurityUtils securityUtils;

    @Autowired
    private DbAccessor dbAccessor;
    @Autowired
    private SubsystemService subsystemService;
    @Autowired
    private MenuService menuService;

    @PostConstruct
    public void init() {
        securityUtils = this;
    }

    public static boolean hasPermission(String permissionCode) {
        return org.apache.shiro.SecurityUtils.getSubject().isPermitted(permissionCode);
    }

    public static void checkPermission(String permissionCode) {
        if (!hasPermission(permissionCode)) {
            throw new MyCheckException("没有权限，操作无效！");
        }
    }

    public static boolean hasRole(String roleName) {
        return org.apache.shiro.SecurityUtils.getSubject().hasRole(roleName);
    }

    public static UserInfo getCurrentUserInfo() {
        Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
        UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
        return userInfo;
    }

    public static String getPermissionExpression(String permissionCode) {
        Map<String, Object> params = new HashMap<>();
        if (getCurrentUserInfo().getSystemCode() != null || getCurrentUserInfo().getSystemCode() != "") {
            String rootId = securityUtils.subsystemService.getPermissionIdByCode(getCurrentUserInfo().getSystemCode());
            params.put("rootId", rootId);
        }
        params.put("permissionCode", permissionCode);
        List<PermissionInfo> permissionInfoList = securityUtils.dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.Permission.getPermissionsByCode", params, PermissionInfo.class);
        for (PermissionInfo permissionInfo : permissionInfoList) {
            if (hasPermission(permissionInfo.getPermissionCode())) {
                return permissionInfo.getPermissionExpression();
            }
        }
        return null;
    }

    public static void clearCache() {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) org.apache.shiro.SecurityUtils.getSecurityManager();
        MyAuthorizingRealm realm = (MyAuthorizingRealm) securityManager.getRealms().iterator().next();
        realm.clearCache();
    }

    public static void reloadFilterChains() {
        //获取filterChainDefinitionMap
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.putAll(securityUtils.menuService.getFilterChainDefinitions());
        filterChainDefinitionMap.put("/test", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/base/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/login/*", "anon");
        filterChainDefinitionMap.put("/common/validate-code/*", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        //获取shiroFilter
        ShiroFilterFactoryBean shiroFilterFactoryBean = ApplicationContextHolder.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();
        //清理
        defaultFilterChainManager.getFilterChains().clear();
        //重建
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        if (!CollectionUtils.isEmpty(filterChainDefinitionMap)) {
            filterChainDefinitionMap.forEach((url, definitionChains) -> {
                defaultFilterChainManager.createChain(url, definitionChains.trim().replace(" ", ""));
            });
        }
    }

}
