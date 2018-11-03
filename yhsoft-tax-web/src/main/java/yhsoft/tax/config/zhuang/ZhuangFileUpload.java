package yhsoft.tax.config.zhuang;

import com.zhuang.data.DbAccessor;
import com.zhuang.fileupload.FileUploadManager;
import com.zhuang.fileupload.config.FileUploadProperties;
import com.zhuang.fileupload.impl.ftp.FtpStoreProvider;
import com.zhuang.fileupload.impl.local.LocalStoreProvider;
import com.zhuang.fileupload.service.impl.DefaultFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ZhuangFileUpload {

    @Autowired
    DbAccessor dbAccessor;

    @Bean
    public FileUploadManager fileUploadManager() {
        FileUploadManager fileUploadManager = null;
        FileUploadProperties fileUploadProperties = new FileUploadProperties();
        if (fileUploadProperties.getStoreProvider().equalsIgnoreCase("ftp")) {
            fileUploadManager = new FileUploadManager(new FtpStoreProvider(), new DefaultFileUploadService(dbAccessor));
        } else if (fileUploadProperties.getStoreProvider().equalsIgnoreCase("local")) {
            fileUploadManager = new FileUploadManager(new LocalStoreProvider(), new DefaultFileUploadService(dbAccessor));
        }
        return fileUploadManager;
    }

}
