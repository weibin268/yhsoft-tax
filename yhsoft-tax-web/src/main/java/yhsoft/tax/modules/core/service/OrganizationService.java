package yhsoft.tax.modules.core.service;

import com.yhsoft.common.web.exception.MyCheckException;
import org.springframework.stereotype.Service;
import yhsoft.tax.modules.core.model.Organization;
import yhsoft.tax.modules.core.model.OrganizationExt;
import yhsoft.tax.modules.base.service.BaseService;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhuang on 8/12/2017.
 */
@Service
public class OrganizationService extends BaseService<Organization>{

    public void add(Organization model) {

    }

    public OrganizationExt get(Object id) {
        OrganizationExt model = dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Organization.getById",id,OrganizationExt.class);
        return  model;
    }

    public void save(Organization model) {
        if(model.getId()==null || model.getId().equals(""))
        {
            model.setId(UUID.randomUUID().toString());
            model.setCreatedBy(getCurrentUserInfo().getUserId());
            model.setCreatedTime(new Date());
            model.setModifiedBy(getCurrentUserInfo().getUserId());
            model.setModifiedTime(new Date());
            model.setFullPath(model.getId());
            if(model.getParentId().equals(""))
            {
                model.setParentId(null);
            }
            if(!model.getParentId().equals("root"))
            {
                Organization pModel = dbAccessor.select(model.getParentId(),Organization.class);

                if(pModel.getFullPath()!=null && !pModel.getFullPath().equals(""))
                {
                    model.setFullPath(pModel.getFullPath()+"."+model.getFullPath());
                }
            }
            dbAccessor.insert(model);
        }else
        {
            Organization tempModel = dbAccessor.select(model.getId(),Organization.class);
            tempModel.setModifiedBy(getCurrentUserInfo().getUserId());
            tempModel.setModifiedTime(new Date());
            tempModel.setName(model.getName());
            tempModel.setSeq(model.getSeq());
            tempModel.setStatus(model.getStatus());
            dbAccessor.update(tempModel);
        }
    }

    public void delete(Object id) {
        Integer count=dbAccessor.queryEntity("zhuang.upms.modules.core.mapper.Organization.refUserCount",id,Integer.class);
        if(count>0)
        {
            throw new MyCheckException("已被用户引用，无法删除！");
        }
        dbAccessor.executeNonQuery("zhuang.upms.modules.core.mapper.Organization.deleteById", getUpdateParam(id));
    }

}
