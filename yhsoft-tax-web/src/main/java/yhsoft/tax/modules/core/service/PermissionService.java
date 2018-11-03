package yhsoft.tax.modules.core.service;

import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yhsoft.tax.security.model.PermissionInfo;
import yhsoft.tax.modules.base.service.BaseService;
import yhsoft.tax.modules.core.model.Permission;
import yhsoft.tax.modules.core.model.PermissionExt;
import yhsoft.tax.modules.core.model.Subsystem;

import java.util.*;

/**
 * Created by zhuang on 8/12/2017.
 */
@Service
public class PermissionService extends BaseService<Permission> {

    @Autowired
    private SubsystemService subsystemService;

    public void add(Permission model) {

    }

    public PermissionExt get(Object id) {
        PermissionExt model = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Permission.getById", id, PermissionExt.class);
        return model;
    }

    public void save(Permission model) {
        if (model.getCode() == null || model.getCode().trim().length() == 0) {
            throw new MyCheckException("权限编码不能为空！");
        }
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
                Permission pModel = dbAccessor.select(model.getParentId(), Permission.class);
                if (pModel.getFullPath() != null && !pModel.getFullPath().equals("")) {
                    model.setFullPath(pModel.getFullPath() + "." + model.getFullPath());
                }
            }
            if (existsByCode(model.getCode(), model.getFullPath())) {
                throw new MyCheckException("权限编码“" + model.getCode() + "”已存在！");
            }
            dbAccessor.insert(model);
        } else {
            Permission tempModel = dbAccessor.select(model.getId(), Permission.class);
            tempModel.setModifiedBy(getCurrentUserInfo().getUserId());
            tempModel.setModifiedTime(new Date());
            tempModel.setName(model.getName());
            tempModel.setSeq(model.getSeq());
            tempModel.setPriority(model.getPriority());
            tempModel.setStatus(model.getStatus());
            tempModel.setCode(model.getCode());
            tempModel.setExpression(model.getExpression());
            if (!tempModel.getCode().equals(model.getCode())) {
                if (existsByCode(model.getCode(), tempModel.getFullPath())) {
                    throw new MyCheckException("权限编码“" + model.getCode() + "”已存在！");
                }
            }
            dbAccessor.update(tempModel);
        }
    }

    public void delete(Object id) {
        Subsystem subsystem = subsystemService.getByPermissionId(id.toString());
        if (subsystem != null) {
            throw new MyCheckException("该权限与系统“" + subsystem.getName() + "”相关联，不能删除！");
        }
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.Permission.deleteById", getUpdateParam(id));
    }

    public boolean existsByCode(String code, String fullPath) {
        String rootId = null;

        if (subsystemService.hasSubsystem()) {
            rootId = fullPath.split("\\.")[0];
        }
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("rootId", rootId);
        Integer count = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Permission.existsByCode", params, Integer.class);
        return count > 0 ? true : false;
    }

    public List<PermissionInfo> getUserPermissions(String userId) {
        return getUserPermissions(userId, null);
    }

    public List<PermissionInfo> getUserPermissions(String userId, String containsId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        if (containsId != null) {
            params.put("containsId", containsId);
        }
        return dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.Permission.getUserPermissions", params, PermissionInfo.class);
    }

    public String getRootPermissionId() {
        return "root";
    }
}
