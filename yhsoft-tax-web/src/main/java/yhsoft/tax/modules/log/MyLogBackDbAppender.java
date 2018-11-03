package yhsoft.tax.modules.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yhsoft.tax.modules.log.service.ErrorLogService;
import yhsoft.tax.modules.log.service.OperationLogService;
import yhsoft.tax.modules.log.model.OperationLog;
import yhsoft.tax.security.model.UserInfo;
import yhsoft.tax.modules.log.model.ErrorLog;
import yhsoft.tax.util.SecurityUtils;

import java.util.Date;

/**
 * Created by zhuang on 3/12/2018.
 */
@Component
public class MyLogBackDbAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Autowired
    private ErrorLogService errorLogService;
    @Autowired
    private OperationLogService operationLogService;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        try {
            if (iLoggingEvent.getLevel().levelInt > Level.INFO_INT) {
                ErrorLog model = new ErrorLog();
                model.setLogName(iLoggingEvent.getLoggerName());
                model.setLevel(iLoggingEvent.getLevel().levelStr);
                model.setMessage(iLoggingEvent.getFormattedMessage());
                model.setStackInfo(ThrowableProxyUtil.asString(iLoggingEvent.getThrowableProxy()));
                model.setLogTime(new Date(iLoggingEvent.getTimeStamp()));
                errorLogService.add(model);
            } else if (iLoggingEvent.getLoggerName() == OperationLogUtil.class.getName()) {
                OperationLog model = new OperationLog();
                model.setDataInfo(iLoggingEvent.getFormattedMessage());
                Object[] argArray = iLoggingEvent.getArgumentArray();
                model.setModule(argArray[0].toString());
                model.setAction(argArray[1].toString());
                UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
                model.setUserId(userInfo.getUserId());
                model.setUserName(userInfo.getLoginId() + "|" + userInfo.getUserName());
                operationLogService.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    @PostConstruct
    public void init() {

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLoggerList().forEach(new Consumer<Logger>() {
            @Override
            public void accept(Logger logger) {

                System.out.println(logger);
                logger.addAppender(MyLogBackDbAppender.this);

            }
        });

        setContext(context);
        start();

    }*/
}
