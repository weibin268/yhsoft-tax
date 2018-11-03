package yhsoft.tax.modules.core.model;

import com.yhsoft.common.enums.CommonStatus;
import com.zhuang.data.orm.annotation.Id;
import com.zhuang.data.orm.annotation.Table;
import com.zhuang.data.orm.annotation.UnderscoreNaming;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhuang on 8/18/2017.
 */
@UnderscoreNaming
@Table(name = "sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -6506879064292877534L;

    @Id
    private String id;
    private  String name;
    private String url;
    private String parentId;
    private String fullPath;
    private Integer seq;
    private Integer level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Menu()
    {
        setStatus(CommonStatus.ENABLE);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parentId='" + parentId + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", seq=" + seq +
                ", level=" + level +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
