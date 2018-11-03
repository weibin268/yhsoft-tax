package yhsoft.tax.modules.core.service;

import com.yhsoft.common.util.EncryptionUtils;
import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.core.model.User;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.core.model.UserExt;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService extends BaseService<User> {

    @Override
    public void add(User model) {
        dbAccessor.insert(model);
    }

    @Override
    public void save(User model) {
        if (model.getId() == null || model.getId().equals("")) {
            model.setId(UUID.randomUUID().toString());
            model.setPassword(encryptPassword("123"));
            model.setCreatedBy(getCurrentUserInfo().getUserId());
            model.setCreatedTime(new Date());
            dbAccessor.insert(model);
        } else {
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            User oldUser = get(model.getId());
            model.setLoginId(oldUser.getLoginId());
            model.setPassword(oldUser.getPassword());
            dbAccessor.update(model);
        }
    }

    @Override
    public void delete(Object id) {
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.User.deleteById", getUpdateParam(id));
    }

    @Override
    public UserExt get(Object id) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.User.getById", id, UserExt.class);
    }

    public User getByLoginId(String loginId) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.User.getByLoginId", loginId, User.class);
    }

    public void changePassword(String id, String currentPassword, String newPassword) {
        User user = get(id);
        if (!user.getPassword().equals(encryptPassword(currentPassword))) {
            throw new MyCheckException("原密码错误！");
        }
        Map<String, Object> param = getUpdateParam(id);
        param.put("password", encryptPassword(newPassword));
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.User.changePasswordById", param);
    }

    public void resetPassword(String id, String newPassword) {
        Map<String, Object> param = getUpdateParam(id);
        param.put("password", encryptPassword(newPassword));
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.User.changePasswordById", param);
    }

    public static String encryptPassword(String password) {
        return EncryptionUtils.encryptByMD5(password);
    }
}
