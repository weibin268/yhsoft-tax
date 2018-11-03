package yhsoft.tax.modules.setting.service;

import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.setting.model.Dictionary;
import yhsoft.tax.modules.setting.model.DictionaryItem;
import yhsoft.tax.modules.setting.model.DictionaryItemInfo;

import java.util.*;

/**
 * Created by zhuang on 3/11/2018.
 */
@Service
public class DictionaryService extends BaseService<Dictionary>
{
    @Override
    public void add(Dictionary model) {

    }

    @Override
    public Dictionary get(Object id) {
        return dbAccessor.queryEntity("zhuang.upms.modules.setting.mapper.Dictionary.getById", id, Dictionary.class);
    }

    @Override
    public void save(Dictionary model) {
        if(existsDictionaryCode(model.getCode(),model.getId()))
        {
            throw new MyCheckException("字典编码“"+model.getCode()+"”已存在！");
        }
        if (model.getId() == null || model.getId().equals("")) {
            model.setId(UUID.randomUUID().toString());
            model.setCreatedBy(getCurrentUserInfo().getUserId());
            model.setCreatedTime(new Date());
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            dbAccessor.insert(model);
        } else {
            Dictionary oldModel = dbAccessor.select(model.getId(), Dictionary.class);
            model.setCode(oldModel.getCode());
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            dbAccessor.update(model);
        }
    }

    public boolean existsDictionaryCode(String code, String selfId) {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("code",code);
        params.put("selfId",selfId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.setting.mapper.Dictionary.existsDictionaryCode", params, Integer.class);
        return count > 0 ? true : false;
    }

    @Override
    public void delete(Object id) {
        dbAccessor.executeNonQuery("zhuang.upms.modules.setting.mapper.Dictionary.deleteById", getUpdateParam(id));
    }

    public void saveItem(DictionaryItem model) {
        if(existsDictionaryItemCode(model.getCode(),model.getId()))
        {
            throw new MyCheckException("编码“"+model.getCode()+"”已存在！");
        }
        if (model.getId() == null || model.getId().equals("")) {
            model.setId(UUID.randomUUID().toString());
            model.setCreatedBy(getCurrentUserInfo().getUserId());
            model.setCreatedTime(new Date());
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            dbAccessor.insert(model);
        } else {
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            dbAccessor.update(model);
        }
    }

    public boolean existsDictionaryItemCode(String code, String selfId) {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("code",code);
        params.put("selfId",selfId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.setting.mapper.Dictionary.existsDictionaryItemCode", params, Integer.class);
        return count > 0 ? true : false;
    }

    public DictionaryItem getItem(Object id) {
        return dbAccessor.queryEntity("zhuang.upms.modules.setting.mapper.Dictionary.getItemById", id, DictionaryItem.class);
    }

    public void deleteItem(Object id) {
        dbAccessor.executeNonQuery("zhuang.upms.modules.setting.mapper.Dictionary.deleteItemById", getUpdateParam(id));
    }

    public List<DictionaryItemInfo> getDictionaryItemInfoListByCode(String code)
    {
        return dbAccessor.queryEntities("zhuang.upms.modules.setting.mapper.Dictionary.getDictionaryItemInfoListByCode",code,DictionaryItemInfo.class);
    }

}
