package yhsoft.tax.modules.core.service;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.DbAccessorFactory;
import com.zhuang.data.config.JdbcProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.core.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhuang on 3/8/2018.
 */
@Service
public class DataSyncService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbAccessor dbAccessor;

    public void sync() {
        List<Subsystem> subsystems = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getAllSubsystem", null, Subsystem.class);
        for (Subsystem item : subsystems) {
            try {
                JdbcProperties jdbcProperties = new JdbcProperties();
                jdbcProperties.setUrl(item.getDbUrl());
                jdbcProperties.setUserName(item.getDbUserName());
                jdbcProperties.setPassword(item.getDbPassword());
                DbAccessor dbAccessor4sub = DbAccessorFactory.createMyBatisDbAccessor(jdbcProperties.getProperties(), true);
                sync(dbAccessor4sub);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }

    public void sync(DbAccessor dbAccessor4sub) {
        syncUser(dbAccessor4sub);
        syncOrganization(dbAccessor4sub);
        syncMenu(dbAccessor4sub);
        syncPermission(dbAccessor4sub);
        syncRole(dbAccessor4sub);
        syncRolePermission(dbAccessor4sub);
        syncUserRole(dbAccessor4sub);
    }

    public void syncUser(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getUserMaxModifiedTime", null, Date.class);
        List<User> users = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getUserDataNeedToSync", maxModifiedTime, User.class);
        for (User item : users) {
            dbAccessor4sub.delete(item, User.class);
            dbAccessor4sub.insert(item);
        }
    }

    public void syncOrganization(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getOrganizationMaxModifiedTime", null, Date.class);
        List<Organization> organizations = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getOrganizationDataNeedToSync", maxModifiedTime, Organization.class);
        for (Organization item : organizations) {
            dbAccessor4sub.delete(item, Organization.class);
            dbAccessor4sub.insert(item);
        }
    }

    public void syncMenu(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getMenuMaxModifiedTime", null, Date.class);
        List<Menu> menus = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getMenuDataNeedToSync", maxModifiedTime, Menu.class);
        for (Menu item : menus) {
            dbAccessor4sub.delete(item, Menu.class);
            dbAccessor4sub.insert(item);
        }
    }

    public void syncPermission(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getPermissionMaxModifiedTime", null, Date.class);
        List<Permission> permissions = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getPermissionDataNeedToSync", maxModifiedTime, Permission.class);
        for (Permission item : permissions) {
            dbAccessor4sub.delete(item, Permission.class);
            dbAccessor4sub.insert(item);
        }
    }

    public void syncRole(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getRoleMaxModifiedTime", null, Date.class);
        List<Role> roles = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getRoleDataNeedToSync", maxModifiedTime, Role.class);
        for (Role item : roles) {
            dbAccessor4sub.delete(item, Role.class);
            dbAccessor4sub.insert(item);
        }
    }

    public void syncRolePermission(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getRolePermissionMaxModifiedTime", null, Date.class);
        List<RolePermission> rolePermissions = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getRolePermissionDataNeedToSync", maxModifiedTime, RolePermission.class);
        for (RolePermission item : rolePermissions) {
            dbAccessor4sub.delete(item, RolePermission.class);
            dbAccessor4sub.insert(item);
        }
    }

    public void syncUserRole(DbAccessor dbAccessor4sub) {
        Date maxModifiedTime = dbAccessor4sub.queryEntity("zhuang.upms.modules.core.mapper.DataSync.getUserRoleMaxModifiedTime", null, Date.class);
        List<UserRole> userRoles = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.DataSync.getUserRoleDataNeedToSync", maxModifiedTime, UserRole.class);
        for (UserRole item : userRoles) {
            dbAccessor4sub.delete(item, UserRole.class);
            dbAccessor4sub.insert(item);
        }
    }

}

