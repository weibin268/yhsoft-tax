package yhsoft.tax.config.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import yhsoft.tax.config.redis.MyRedisProperties;

/**
 * Created by zhuang on 2/26/2018.
 */
//@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setSessionManager(sessionManager());
        manager.setCacheManager(shiroRedisCacheManager());
        return manager;
    }

    @Bean(name = "sessionManager")
    public ServletContainerSessionManager sessionManager() {
        ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
        return sessionManager;
    }

    @Bean(name = "shiroRedisCacheManager")
    @DependsOn(value = "shiroRedisTemplate")
    public ShiroRedisCacheManager shiroRedisCacheManager() {
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager(shiroRedisTemplate());
        cacheManager.getCache("shiro_redis:");
        return cacheManager;
    }

    @Bean(name = "shiroRedisTemplate")
    public RedisTemplate<byte[], byte[]> shiroRedisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean(name = "connectionFactory")
    public RedisConnectionFactory connectionFactory() {
        MyRedisProperties myRedisProperties = myRedisProperties();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(myRedisProperties.getDatabase());
        redisStandaloneConfiguration.setHostName(myRedisProperties.getHost());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(myRedisProperties.getPassword()));
        redisStandaloneConfiguration.setPort(myRedisProperties.getPort());
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return connectionFactory;

    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    MyRedisProperties myRedisProperties() {
        return new MyRedisProperties();
    }

}
