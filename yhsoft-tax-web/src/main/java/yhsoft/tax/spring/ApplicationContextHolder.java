package yhsoft.tax.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhuang
 * @create 7/5/18 3:08 PM
 **/
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {

        if (this.applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        }
        this.applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null)
            throw new IllegalStateException("'applicationContext' property is null,ApplicationContextHolder not yet init.");
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return getApplicationContext().getBean(requiredType);
    }
}