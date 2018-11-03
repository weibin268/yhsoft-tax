package yhsoft.tax.modules.core.service;

import com.yhsoft.common.enums.CommonStatus;
import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.stereotype.Service;
import yhsoft.tax.security.model.RoleInfo;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.core.model.Role;
import yhsoft.tax.modules.core.model.RolePermission;
import yhsoft.tax.modules.core.model.UserRole;
import yhsoft.tax.util.SecurityUtils;

import java.util.*;

/**
 * Created by zhuang on 8/18/2017.
 */

@Service
public class RoleService extends BaseService<Role> {

    public void add(Role model) {

    }

    public Role get(Object id) {
        return dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Role.getById", id, Role.class);
    }

    public void save(Role model) {
        if(existsRoleName(model.getName(),model.getId()))
        {
            throw new MyCheckException("角色名“"+model.getName()+"”已存在！");
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

    public boolean existsRoleName(String roleName, String selfRoleId) {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("roleName",roleName);
        params.put("selfRoleId",selfRoleId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Role.existsRoleName", params, Integer.class);
        return count > 0 ? true : false;
    }

    public void delete(Object id) {
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.Role.deleteById", getUpdateParam(id));
    }

    public boolean hasUser(String roleId,String userId) {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("roleId",roleId);
        params.put("userId",userId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Role.getUserCount", params, Integer.class);
        if (count > 0)
            return true;
        else
            return false;
    }

    public void addUser(String roleId,String userId)
    {
        if(!hasUser(roleId,userId))
        {
            UserRole userRole=new UserRole();
            userRole.setId(UUID.randomUUID().toString());
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setStatus(CommonStatus.ENABLE);
            userRole.setCreatedBy(getCurrentUserInfo().getUserId());
            userRole.setCreatedTime(new Date());
            userRole.setModifiedBy(getCurrentUserInfo().getUserId());
            userRole.setModifiedTime(new Date());
            dbAccessor.insert(userRole);
        }
    }

    public void deleteUser(String roleId,String userId)
    {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("roleId",roleId);
        params.put("userId",userId);
        params.put("ModifiedBy",getCurrentUserInfo().getUserId());
        params.put("ModifiedTime",new Date());
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.Role.deleteUser",params);
    }


    public void setPermissions(String roleId,List<String> permissionIds)
    {
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.Role.deletePermissionsByRoleId", getUpdateParam(roleId));
        for (String permissionId:
             permissionIds) {
            RolePermission rolePermission=new RolePermission();
            rolePermission.setId(UUID.randomUUID().toString());
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermission.setStatus(CommonStatus.ENABLE);
            rolePermission.setCreatedBy(getCurrentUserInfo().getUserId());
            rolePermission.setCreatedTime(new Date());
            rolePermission.setModifiedBy(getCurrentUserInfo().getUserId());
            rolePermission.setModifiedTime(new Date());
            dbAccessor.insert(rolePermission);
        }
        SecurityUtils.clearCache();
    }

    public List<RoleInfo> getUserPermissions(String userId)
    {
        return dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.Role.getUserRoles",userId,RoleInfo.class);
    }
}
