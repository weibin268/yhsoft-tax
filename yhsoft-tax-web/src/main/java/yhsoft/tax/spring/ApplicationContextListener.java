package yhsoft.tax.spring;


import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import yhsoft.tax.security.MyAuthorizingRealm;
import yhsoft.tax.util.SecurityUtils;

/**
 * @author zhuang
 * @create 7/5/18 9:38 AM
 **/
//@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initSecurity(event.getApplicationContext());
    }

    private void initSecurity(ApplicationContext applicationContext) {
        //加载FilterChains
        SecurityUtils.reloadFilterChains();
        //myAuthorizingRealm
        MyAuthorizingRealm myAuthorizingRealm = applicationContext.getBean(MyAuthorizingRealm.class);
        //securityManager
        DefaultWebSecurityManager securityManager = applicationContext.getBean(DefaultWebSecurityManager.class);
        //将myAuthorizingRealm设到securityManager
        securityManager.setRealm(myAuthorizingRealm);
    }

}