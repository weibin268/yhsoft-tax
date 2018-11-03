package yhsoft.tax.modules.log.service;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.log.model.ErrorLog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhuang on 3/12/2018.
 */
@Service
public class ErrorLogService {

    @Autowired
    private DbAccessor dbAccessor;

    public void add(ErrorLog model) {
        model.setId(UUID.randomUUID().toString());
        model.setCreatedTime(new Date());
        dbAccessor.insert(model);
    }
}
