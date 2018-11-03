package yhsoft.tax.modules.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import yhsoft.tax.modules.core.service.SubsystemService;
import yhsoft.tax.security.model.MenuInfo;
import yhsoft.tax.modules.base.service.MainService;
import yhsoft.tax.util.SecurityUtils;
import yhsoft.tax.config.zhuang.ZhuangSystemProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhuang on 2/27/2018.
 */
@Controller
@RequestMapping(value = "/main")
public class MainController extends BaseController {

    @Autowired
    private MainService mainService;
    @Autowired
    private SubsystemService subsystemService;
    @Autowired
    private ZhuangSystemProperties systemProperties;

    @RequestMapping(value = "")
    public String index(HttpServletRequest request) {
        request.setAttribute("systemProperties", systemProperties);
        request.setAttribute("mainService", mainService);
        int topMenusLevel = 0;
        String topMenusParentId = "root";
        String leftParentMenusContainsId = null;
        if (systemProperties.getSystemCode() != null) {
            topMenusParentId = subsystemService.getMenuIdByCode(systemProperties.getSystemCode());
            topMenusLevel = 1;
            leftParentMenusContainsId = topMenusParentId;
        }
        List<MenuInfo> topMenus = mainService.getMainMenus(topMenusLevel, topMenusParentId);
        request.setAttribute("topMenus", topMenus);
        List<MenuInfo> leftParentMenus = mainService.getMainMenus(topMenusLevel + 1, null, leftParentMenusContainsId);
        request.setAttribute("leftParentMenus", leftParentMenus);
        request.setAttribute("currentUserInfo", SecurityUtils.getCurrentUserInfo());
        request.setAttribute("leafMenusLevel", topMenusLevel + 2);
        return "main";
    }

}
