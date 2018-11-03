package yhsoft.tax.config.zhuang;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zhuang on 3/2/2018.
 */

@Component
@ConfigurationProperties(prefix = "zhuang.system")
public class ZhuangSystemProperties {

    private String systemCode;
    private String systemName;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
