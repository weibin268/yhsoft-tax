package yhsoft.tax.modules.core.service;

import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.core.model.Menu;
import yhsoft.tax.modules.core.model.Permission;
import yhsoft.tax.modules.core.model.Subsystem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhuang on 3/6/2018.
 */
@Service
public class SubsystemService extends BaseService<Subsystem> {

    @Override
    public void add(Subsystem model) {

    }

    @Override
    public Subsystem get(Object id) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.getById", id, Subsystem.class);
    }

    @Override
    public void save(Subsystem model) {
        if (existsSubsystemCode(model.getCode(), model.getId())) {
            throw new MyCheckException("系统编码“" + model.getName() + "”已存在！");
        }
        if (existsSubsystemName(model.getName(), model.getId())) {
            throw new MyCheckException("系统名称“" + model.getName() + "”已存在！");
        }
        if (model.getId() == null || model.getId().equals("")) {
            MenuService menuService = new MenuService();
            String rootMenuId = menuService.getRootMenuId();
            String menuId = UUID.randomUUID().toString();
            Menu menu = new Menu();
            menu.setId(menuId);
            menu.setName(model.getName());
            menu.setParentId(rootMenuId);
            menu.setFullPath(menuId);
            menu.setLevel(0);
            menu.setSeq(100);
            menu.setCreatedBy(getCurrentUserInfo().getUserId());
            menu.setCreatedTime(new Date());
            menu.setModifiedBy(getCurrentUserInfo().getUserId());
            menu.setModifiedTime(new Date());
            dbAccessor.insert(menu);
            PermissionService permissionService = new PermissionService();
            String rootPermissionId = permissionService.getRootPermissionId();
            String permissionId = UUID.randomUUID().toString();
            Permission permission = new Permission();
            permission.setId(permissionId);
            permission.setName(model.getName());
            permission.setCode("menu:" + menuId);
            permission.setExpression("");
            permission.setParentId(rootPermissionId);
            permission.setFullPath(permissionId);
            permission.setLevel(0);
            permission.setSeq(1);
            permission.setCreatedBy(getCurrentUserInfo().getUserId());
            permission.setCreatedTime(new Date());
            permission.setModifiedBy(getCurrentUserInfo().getUserId());
            permission.setModifiedTime(new Date());
            dbAccessor.insert(permission);
            model.setId(UUID.randomUUID().toString());
            model.setMenuId(menuId);
            model.setPermissionId(permissionId);
            model.setCreatedBy(getCurrentUserInfo().getUserId());
            model.setCreatedTime(new Date());
            dbAccessor.insert(model);

        } else {
            Subsystem oldModel = dbAccessor.select(model.getId(), Subsystem.class);
            model.setCode(oldModel.getCode());
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            dbAccessor.update(model);
            Menu menu = dbAccessor.select(model.getMenuId(), Menu.class);
            menu.setName(model.getName());
            menu.setModifiedBy(model.getModifiedBy());
            menu.setModifiedTime(model.getModifiedTime());
            dbAccessor.update(menu);
            Permission permission = dbAccessor.select(model.getPermissionId(), Permission.class);
            permission.setName(model.getName());
            permission.setModifiedBy(model.getModifiedBy());
            permission.setModifiedTime(model.getModifiedTime());
            dbAccessor.update(menu);
        }
    }

    @Override
    public void delete(Object id) {
        dbAccessor.delete(id, Subsystem.class);
    }

    public boolean existsSubsystemName(String name, String selfId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("selfId", selfId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.existsSubsystemName", params, Integer.class);
        return count > 0 ? true : false;
    }

    public boolean existsSubsystemCode(String code, String selfId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("selfId", selfId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.existsSubsystemCode", params, Integer.class);
        return count > 0 ? true : false;
    }

    public Subsystem getByMenuId(String menuId) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.getByMenuId", menuId, Subsystem.class);
    }

    public Subsystem getByPermissionId(String permissionId) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.getByPermissionId", permissionId, Subsystem.class);
    }

    public String getMenuIdByCode(String code) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.getMenuIdByCode", code, String.class);
    }

    public String getPermissionIdByCode(String code) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.getPermissionIdByCode", code, String.class);
    }

    public boolean hasSubsystem() {
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Subsystem.getCount", null, Integer.class);
        return count > 0 ? true : false;
    }
}
