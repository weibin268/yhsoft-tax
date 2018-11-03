package yhsoft.tax.modules.core.service;

import com.yhsoft.common.model.KeyValue;
import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yhsoft.tax.config.zhuang.ZhuangSystemProperties;
import yhsoft.tax.modules.core.model.Subsystem;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.core.model.Menu;
import yhsoft.tax.modules.core.model.MenuExt;

import java.util.*;

/**
 * Created by zhuang on 8/12/2017.
 */
@Service
public class MenuService extends BaseService<Menu> {

    @Autowired
    private SubsystemService subsystemService;
    @Autowired
    private ZhuangSystemProperties zhuangSystemProperties;

    public void add(Menu model) {

    }

    public MenuExt get(Object id) {
        MenuExt model = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Menu.getById", id, MenuExt.class);
        return model;
    }

    public void save(Menu model) {
        if (model.getId() == null || model.getId().equals("")) {
            model.setId(UUID.randomUUID().toString());
            model.setCreatedBy(getCurrentUserInfo().getUserId());
            model.setCreatedTime(new Date());
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            model.setFullPath(model.getId());
            if (model.getParentId().equals("")) {
                model.setParentId(null);
            }
            if (!model.getParentId().equals("root")) {
                Menu pModel = dbAccessor.select(model.getParentId(), Menu.class);

                if (pModel.getFullPath() != null && !pModel.getFullPath().equals("")) {
                    model.setFullPath(pModel.getFullPath() + "." + model.getFullPath());
                }
            }
            dbAccessor.insert(model);
        } else {
            Menu tempModel = dbAccessor.select(model.getId(), Menu.class);
            tempModel.setModifiedBy(getCurrentUserInfo().getUserId());
            tempModel.setModifiedTime(new Date());
            tempModel.setName(model.getName());
            tempModel.setSeq(model.getSeq());
            tempModel.setStatus(model.getStatus());
            tempModel.setUrl(model.getUrl());
            dbAccessor.update(tempModel);
        }
    }

    public void delete(Object id) {
        Subsystem subsystem = subsystemService.getByMenuId(id.toString());
        if (subsystem != null) {
            throw new MyCheckException("该菜单与系统“" + subsystem.getName() + "”相关联，不能删除！");
        }
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.Menu.deleteById", getUpdateParam(id));
    }

    public Map<String, String> getFilterChainDefinitions() {
        String systemCode = zhuangSystemProperties.getSystemCode();
        String menuId = null;
        if (systemCode != null && systemCode != "") {
            menuId = subsystemService.getMenuIdByCode(systemCode);
        }
        Map<String, String> result = new HashMap<String, String>();
        Map<String, Object> params = new HashMap<>();
        params.put("containsId", menuId);
        List<KeyValue> definitionsList = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.Menu.getFilterChainDefinitions", params, KeyValue.class);
        for (KeyValue item : definitionsList) {
            result.put(item.getKey(), item.getValue());
        }
        return result;
    }

    public String getRootMenuId() {
        //return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Menu.getRootMenuId",null,String.class);
        return "root";
    }

    @Cacheable(value = "menuList")
    public List<Menu> getAll() {
        return dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.Menu.getAll", null, Menu.class);
    }
}
