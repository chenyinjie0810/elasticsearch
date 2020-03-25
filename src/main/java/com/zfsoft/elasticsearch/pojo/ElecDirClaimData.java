package com.zfsoft.elasticsearch.pojo;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;


/**
 * 目录认领表
 * 
 * @description
 * @author chenbw
 * @date 2018年6月14日
 * @Copyright 版权由上海卓繁信息技术股份有限公司拥有
 */
@Document(indexName ="elms_elasticsearch" ,type = "ElecDirClaimData")
public class ElecDirClaimData {

    private static final long serialVersionUID = 457079203958725270L;

    /**
     * @COLUMN_EXPLAIN : 主键
     */
    private String oid;
    /**
     * @COLUMN_EXPLAIN : 区划主键
     */
    private String districtOid;
    /**
     * @COLUMN_EXPLAIN : 认领机构主键
     */
    private String organOid;
    /**
     * @COLUMN_EXPLAIN : 认领目录主键
     */
    private String elecDirOid;
    /**
     * @COLUMN_EXPLAIN : 创建时间
     */
    private Date createDate;
    /**
     * @COLUMN_EXPLAIN : 创建人
     */
    private String createUser;
    /**
     * @COLUMN_EXPLAIN : 是否删除
     */
    private String isDelete;
    /**
     * 修改时间
     * 此字段用于做时间戳记录 在新增和修改时 时间会更新
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @Field(format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss", type = FieldType.Date)
    protected Date modifyDate;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDistrictOid() {
        return districtOid;
    }

    public void setDistrictOid(String districtOid) {
        this.districtOid = districtOid;
    }

    public String getOrganOid() {
        return organOid;
    }

    public void setOrganOid(String organOid) {
        this.organOid = organOid;
    }

    public String getElecDirOid() {
        return elecDirOid;
    }

    public void setElecDirOid(String elecDirOid) {
        this.elecDirOid = elecDirOid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
