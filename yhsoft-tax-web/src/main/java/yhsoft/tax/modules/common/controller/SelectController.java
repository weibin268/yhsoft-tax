package yhsoft.tax.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhuang on 3/4/2018.
 */
@Controller
@RequestMapping(value = "/common/select")
public class SelectController {

    @RequestMapping("tree_dialog")
    public String treeDialog()
    {
        return "common/select/tree_dialog";
    }

    @RequestMapping("user_dialog")
    public String userDialog()
    {
        return "common/select/user_dialog";
    }

}
