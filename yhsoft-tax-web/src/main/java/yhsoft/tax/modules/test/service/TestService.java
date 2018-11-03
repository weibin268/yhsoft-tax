package yhsoft.tax.modules.test.service;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yhsoft.tax.modules.core.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuang on 3/18/2018.
 */
@Service
public class TestService {

    @Autowired
    private DbAccessor dbAccessor;

    @Transactional
    public Object getObject()
    {
        User user = dbAccessor.select("4363264c-37b5-4cfa-ba89-1f0b9145656e", User.class);
        Map<String,Object> params=new HashMap<>();
        params.put("id",user.getId());
        params.put("ModifiedBy",user.getModifiedBy());
        params.put("ModifiedTime",user.getModifiedTime());
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.User.deleteById",params);
        user.setStatus(1);
        dbAccessor.update(user);
        if(true)
            throw new RuntimeException("aa");
        return user;

    }

}
