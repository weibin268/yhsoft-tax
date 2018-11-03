package yhsoft.tax.modules.base.service;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import yhsoft.tax.security.model.UserInfo;
import yhsoft.tax.util.SecurityUtils;

import java.util.Date;
import java.util.HashMap;

public abstract class BaseService<T> {

    @Autowired
    protected DbAccessor dbAccessor;

    public abstract void add(T model);

    public abstract T get(Object id);

    public abstract void save(T model);

    public abstract void delete(Object id);

    public UserInfo getCurrentUserInfo() {
        return SecurityUtils.getCurrentUserInfo();
    }

    public HashMap<String, Object> getUpdateParam(Object id) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("ModifiedBy", getCurrentUserInfo().getUserId());
        result.put("ModifiedTime", new Date());
        return result;
    }
}
