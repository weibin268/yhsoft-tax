package yhsoft.tax.modules.base.service;

import com.zhuang.data.DbAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yhsoft.tax.security.model.MenuInfo;
import yhsoft.tax.util.SecurityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuang on 8/26/2017.
 */

@Service
public class MainService {

    @Autowired
    protected DbAccessor dbAccessor;

    public List<MenuInfo> getMainMenus(int level) {
        return getMainMenus(level, null, null);
    }

    public List<MenuInfo> getMainMenus(int level, String parentId) {
        return getMainMenus(level, parentId, null);
    }

    public List<MenuInfo> getMainMenus(int level, String parentId, String containsId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("level", level);
        if (parentId != null) {
            params.put("parentId", parentId);
        }
        if (containsId != null) {
            params.put("belongId", containsId);
        }
        List<MenuInfo> menuInfoList = dbAccessor.queryEntities("zhuang.upms.modules.core.mapper.Menu.getMainMenus", params, MenuInfo.class);

        List<MenuInfo> result = new ArrayList<MenuInfo>();

        for (MenuInfo menuInfo :
                menuInfoList) {
            if (menuInfo.getNeedPermission() == false || SecurityUtils.hasPermission("menu:" + menuInfo.getMenuId())) {
                result.add(menuInfo);
            }
        }
        return result;
    }

}
