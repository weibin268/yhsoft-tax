package yhsoft.tax.modules.log.model;

import java.util.Date;
import com.zhuang.data.orm.annotation.Id;
import com.zhuang.data.orm.annotation.Table;
import com.zhuang.data.orm.annotation.UnderscoreNaming;

/**
 * Created by zhuang on 2018-09-05.
 */
@UnderscoreNaming
@Table(name="sys_operation_log")
public class OperationLog {

	@Id
    private String id;
    private String userId;
    private String userLoginId;
    private String userName;
    private String module;
    private String moduleName;
    private String action;
    private String actionName;
    private String dataInfo;
    private Date createdTime;

  	public String getId() {
		return id;
	}

    public void setId(String id) {
		this.id=id;
	}

  	public String getUserId() {
		return userId;
	}

    public void setUserId(String userId) {
		this.userId=userId;
	}

  	public String getUserLoginId() {
		return userLoginId;
	}

    public void setUserLoginId(String userLoginId) {
		this.userLoginId=userLoginId;
	}

  	public String getUserName() {
		return userName;
	}

    public void setUserName(String userName) {
		this.userName=userName;
	}

  	public String getModule() {
		return module;
	}

    public void setModule(String module) {
		this.module=module;
	}

  	public String getModuleName() {
		return moduleName;
	}

    public void setModuleName(String moduleName) {
		this.moduleName=moduleName;
	}

  	public String getAction() {
		return action;
	}

    public void setAction(String action) {
		this.action=action;
	}

  	public String getActionName() {
		return actionName;
	}

    public void setActionName(String actionName) {
		this.actionName=actionName;
	}

  	public String getDataInfo() {
		return dataInfo;
	}

    public void setDataInfo(String dataInfo) {
		this.dataInfo=dataInfo;
	}

  	public Date getCreatedTime() {
		return createdTime;
	}

    public void setCreatedTime(Date createdTime) {
		this.createdTime=createdTime;
	}


	@Override
	public String toString() {
		return "Menu{" +
				"id='" + id + '\'' +
				"userId='" + userId + '\'' +
				"userLoginId='" + userLoginId + '\'' +
				"userName='" + userName + '\'' +
				"module='" + module + '\'' +
				"moduleName='" + moduleName + '\'' +
				"action='" + action + '\'' +
				"actionName='" + actionName + '\'' +
				"dataInfo='" + dataInfo + '\'' +
				"createdTime='" + createdTime + '\'' +
				'}';
	}
}
