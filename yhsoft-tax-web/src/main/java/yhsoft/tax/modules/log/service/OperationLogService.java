package yhsoft.tax.modules.log.service;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.log.model.OperationLog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhuang on 3/14/2018.
 */
@Service
public class OperationLogService extends BaseService<OperationLog> {

    @Autowired
    private DbAccessor dbAccessor;

    public void add(OperationLog model) {
        model.setId(UUID.randomUUID().toString());
        model.setCreatedTime(new Date());
        dbAccessor.insert(model);
    }


    public OperationLog get(Object id) {
        OperationLog model = dbAccessor.queryEntity("zhuang.upms.modules.log.mapper.OperationLog.getById", id, OperationLog.class);
        return model;
    }

    public void save(OperationLog model) {
        if (model.getId() == null || model.getId().equals("")) {
            model.setId(UUID.randomUUID().toString());
            model.setCreatedTime(new Date());
            dbAccessor.insert(model);
        } else {
            OperationLog tempModel = dbAccessor.select(model.getId(), OperationLog.class);
            dbAccessor.update(tempModel);
        }
    }

    public void delete(Object id) {
        dbAccessor.executeNonQuery("zhuang.upms.modules.log.mapper.OperationLog.deleteById", getUpdateParam(id));
    }

}
