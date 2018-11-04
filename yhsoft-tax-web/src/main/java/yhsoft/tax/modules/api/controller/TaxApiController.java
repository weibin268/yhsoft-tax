package yhsoft.tax.modules.api.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/taxapi")
public class TaxApiController {

    @Autowired
    private SqlSession sqlSession;

    @ResponseBody
    @RequestMapping(value = "test")
    public Object test() {
       Map map = sqlSession.selectOne("yhsoft.tax.modules.core.mapper.Test.getList");
        System.out.println(map);
        //AAA
        return "test";
    }
}
