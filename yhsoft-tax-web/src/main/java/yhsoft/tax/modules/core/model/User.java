package yhsoft.tax.modules.core.model;

import java.util.Date;
import com.zhuang.data.orm.annotation.Id;
import com.zhuang.data.orm.annotation.Table;
import com.zhuang.data.orm.annotation.UnderscoreNaming;

@UnderscoreNaming
@Table(name="sys_user")
public class User {
	
	@Id
	private String id;
	private String loginId;
	private String name;
	private String password;
	private String sex;
	private String orgId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	private Integer status;

	private Date createdTime;

	private Date modifiedTime;

	private String createdBy;

	private String modifiedBy;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginId=" + loginId + ", name=" + name + ", password=" + password + ", sex=" + sex
				+ ", status=" + status + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]";
	}


	
}
