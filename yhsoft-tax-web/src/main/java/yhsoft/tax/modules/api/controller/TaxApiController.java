package yhsoft.tax.modules.api.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

/*    @RequestMapping(value = "/uploadSpecialApprovalFile", method = RequestMethod.POST)
    @ResponseBody
    public MAjaxResult uploadSpecialApprovalFile(HttpServletRequest request, HttpServletResponse response) {
        String msg = "操作成功！";
        boolean flag = false;
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multiRequest.getFileNames();
        List<String> fileNameList=new ArrayList<String>();
        while(iter.hasNext()){
            fileNameList.add(iter.next());
        }
        Object object = request.getParameter("detailList");
    }*/
}
