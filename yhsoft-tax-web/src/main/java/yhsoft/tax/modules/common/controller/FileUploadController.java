package yhsoft.tax.modules.common.controller;

import com.zhuang.fileupload.FileUploadManager;
import com.zhuang.fileupload.model.SysFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import yhsoft.tax.modules.base.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhuang on 3/6/2018.
 */
//@Controller
@RequestMapping(value = "/common/fileupload")
public class FileUploadController extends BaseController {

    @Autowired
    private FileUploadManager fileUploadManager;

    @RequestMapping(value = "upload")
    @ResponseBody
    public Object upload(HttpServletRequest request, @RequestParam MultipartFile file) throws IOException {
        String tplId = request.getParameter("tplId");//文件上传模板Id
        String bizId = request.getParameter("bizId");//业务单据Id
        InputStream inputStream;
        inputStream = file.getInputStream();
        SysFileUpload upload = fileUploadManager.upload(inputStream, tplId, file.getOriginalFilename(), bizId);
        return toMyJsonResult(upload);
    }

    @RequestMapping(value = "download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fId = request.getParameter("fId");//文件上传Id
        SysFileUpload sysFileUpload = fileUploadManager.getSysFileUpload(fId);
        String fileName = sysFileUpload.getOrginFileName();
        fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");//chrome,firefox
        //fileName=URLEncoder.encode(fileName,"utf-8");//IE
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        InputStream inputStream;
        OutputStream outputStream;
        inputStream = fileUploadManager.download(sysFileUpload);
        outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int readCount;
        while ((readCount = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readCount);
        }
    }

}
