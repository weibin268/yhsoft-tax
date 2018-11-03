package yhsoft.tax.modules.log.service;

import org.springframework.stereotype.Service;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.log.model.LoginLog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhuang on 12/24/2017.
 */
@Service
public class LoginLogService extends BaseService<LoginLog> {


    @Override
    public void add(LoginLog model) {
        model.setId(UUID.randomUUID().toString());
        model.setCreatedTime(new Date());
        dbAccessor.insert(model);
    }

    @Override
    public LoginLog get(Object id) {
        return null;
    }

    @Override
    public void save(LoginLog model) {

    }

    @Override
    public void delete(Object id) {

    }
}
