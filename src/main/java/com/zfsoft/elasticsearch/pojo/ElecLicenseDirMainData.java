package com.zfsoft.elasticsearch.pojo;


import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @电子证照实施清单主表实体
 * 
 * @author : chenyj
 * @date : 2020/3/2
 */
@Document(indexName = "elms_elasticsearch", type ="ElecLicenseDirMainData" )
public class ElecLicenseDirMainData implements Serializable {

    /**
     * @COLUMN_EXPLAIN : 主键
     * 使用Id注解，则保存时不可指定主键ID，否则报错
     */
//    @Id
    private String directoryOid;
    /**
     * @COLUMN_EXPLAIN : 所属区划主键
     */
    private String districtOid;
    /**
     * @COLUMN_EXPLAIN : 目录类型，0证照/1批文
     */
    private String directoryType;
    /**
     * @COLUMN_EXPLAIN : 主办部门,所属系统组织表OID
     */
    private String mainOrganOid;
    /**
     * @COLUMN_EXPLAIN : 所属服务事项
     */
    private String serviceOid;

    /** 目录名称 */
    private String directoryName;

    /** 目录类型：0个人基本信息、个人资格信息、法人基本信息、企业资格信息、投资项目审批环节结果信息 */
    private String directoryGenre;

    /** 目录编码 */
    private String directoryCode;

    /** 目录授予对象 */
    private String directoryObj;

    /** 部门类型 */
    private String departmentType;

    /** 目录编码前缀 */
    private String directoryCodePre;

    /** 目录编码后缀 */
    private Integer directoryCodePos;

    /**
     * @COLUMN_EXPLAIN : 目录目录清单OID
     */
    private String billOid;
    /**
     * @COLUMN_EXPLAIN : 上游单位,关联组织机构表
     */
    private String upstreamUnit;
    /**
     * @COLUMN_EXPLAIN : 是否有前置证照0是1否
     */

    private String frontLiceseFlag;
    /**
     * @COLUMN_EXPLAIN : 有无业务系统支撑0是1否
     */
    private String bizSystemFlag;
    /**
     * @COLUMN_EXPLAIN : 业务系统名称
     */
    /**
     * 分词划分，可建立不同分词，如果没有安装IK分词器则不要打开
     */
    // modify by chenyj on 2020/3/2
//    @Field(index = true, type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String bizSystemName;
    /**
     * @COLUMN_EXPLAIN : 业务系统部署网
     */
    private String bizSystemNetwork;
    /**
     * @COLUMN_EXPLAIN : 是否包含涉密内容0是1否
     */
    private String classifiedComtentFlag;
    /**
     * @COLUMN_EXPLAIN : 是否有电子印章0是1否
     */
    private String sealFlag;
    /**
     * @COLUMN_EXPLAIN : 吊销列表地址
     */
    private String revokedUrl;
    /**
     * @COLUMN_EXPLAIN : 在线查询地址
     */
    private String onlineQueryUrl;
    /**
     * @COLUMN_EXPLAIN : 在线验证地址
     */

    private String onlineValidUrl;
    /**
     * @COLUMN_EXPLAIN : 主题分类
     */
    private String headingSubject;
    /**
     * @COLUMN_EXPLAIN : 来源
     */

    private String source;
    /**
     * @COLUMN_EXPLAIN : 语言
     */

    private String language;
    /**
     * @COLUMN_EXPLAIN : 硬件环境
     */

    private String hardwareEnvironment;
    /**
     * @COLUMN_EXPLAIN : 软件环境
     */
    private String softwareEnvironment;
    /**
     * @COLUMN_EXPLAIN : 业务行为
     */

    private String businessBehavior;
    /**
     * @COLUMN_EXPLAIN : 种类码
     */
    private String extractCode;
    /**
     * @COLUMN_EXPLAIN : 备注
     */
    private String remark;
    /**
     * @COLUMN_EXPLAIN : 目录状态 0:新建目录，2：待审核，3：审核通过 4：审核不通过，5：变更带审核，6：变更审核不通过
     *                 7:变更未提交审核
     * @TABLE_COLUMN_TYPE : VARCHAR(1)
     */
    private String auditStatus;
    /**
     * @COLUMN_EXPLAIN : 启用状态 Y启用，N禁用
     * @TABLE_COLUMN_TYPE : VARCHAR(2)
     */
    private String enabledStatus;
    /**
     * @COLUMN_EXPLAIN : 删除状态 Y删除，N未删除
     * @TABLE_COLUMN_TYPE : VARCHAR(2)
     */
    private String delFlag;
    /**
     * @COLUMN_EXPLAIN : 创建人，关联系统用户表
     * @TABLE_COLUMN_TYPE : VARCHAR(32)
     */
    private String createUser;
    /**
     * @COLUMN_EXPLAIN : 创建时间
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    private Date createDate;
    /**
     * 根据模板照面元素生成对应表名称
     */
    private String metadTableName;
    /**
     * 是否长期有效 0 是 1否 2是自定义
     */
    private String expipyFlag;
    /**
     * 有效期时间
     */
    private String expipyDate;
    /**
     * 是否需要自动年检 0需要 1不需要
     */
    private String autoAnnualFlag;
    /**
     * 提前多少时间年检 默认单位 天
     */
    private String autoAnnualDate;
    /**
     * 是否需要自动注销 0需要 1不需要
     */
    private String autoCancelFlag;
    /**
     * 超期多少时间自动注销 默认单位 天
     */
    private String autoCancelDate;
    /**
     * 实施清单编码字段
     */
    private String implementCode;

    /** 证照类型 */
    private String licenceType;

    /**
     * @COLUMN_EXPLAIN : 提交审核时间
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    private Date submitAuditTime;
    /**
     * 超期时间
     */
    private Date overAuditTime;
    /**
     * 修改时间
     * 此字段用于做时间戳记录 在新增和修改时 时间会更新
     */
    private Date modifyDate;

    public String getDirectoryOid() {
        return directoryOid;
    }

    public void setDirectoryOid(String directoryOid) {
        this.directoryOid = directoryOid;
    }

    public String getDistrictOid() {
        return districtOid;
    }

    public void setDistrictOid(String districtOid) {
        this.districtOid = districtOid;
    }

    public String getDirectoryType() {
        return directoryType;
    }

    public void setDirectoryType(String directoryType) {
        this.directoryType = directoryType;
    }

    public String getMainOrganOid() {
        return mainOrganOid;
    }

    public void setMainOrganOid(String mainOrganOid) {
        this.mainOrganOid = mainOrganOid;
    }

    public String getServiceOid() {
        return serviceOid;
    }

    public void setServiceOid(String serviceOid) {
        this.serviceOid = serviceOid;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryGenre() {
        return directoryGenre;
    }

    public void setDirectoryGenre(String directoryGenre) {
        this.directoryGenre = directoryGenre;
    }

    public String getDirectoryCode() {
        return directoryCode;
    }

    public void setDirectoryCode(String directoryCode) {
        this.directoryCode = directoryCode;
    }

    public String getDirectoryObj() {
        return directoryObj;
    }

    public void setDirectoryObj(String directoryObj) {
        this.directoryObj = directoryObj;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    public String getDirectoryCodePre() {
        return directoryCodePre;
    }

    public void setDirectoryCodePre(String directoryCodePre) {
        this.directoryCodePre = directoryCodePre;
    }

    public Integer getDirectoryCodePos() {
        return directoryCodePos;
    }

    public void setDirectoryCodePos(Integer directoryCodePos) {
        this.directoryCodePos = directoryCodePos;
    }

    public String getBillOid() {
        return billOid;
    }

    public void setBillOid(String billOid) {
        this.billOid = billOid;
    }

    public String getUpstreamUnit() {
        return upstreamUnit;
    }

    public void setUpstreamUnit(String upstreamUnit) {
        this.upstreamUnit = upstreamUnit;
    }

    public String getFrontLiceseFlag() {
        return frontLiceseFlag;
    }

    public void setFrontLiceseFlag(String frontLiceseFlag) {
        this.frontLiceseFlag = frontLiceseFlag;
    }

    public String getBizSystemFlag() {
        return bizSystemFlag;
    }

    public void setBizSystemFlag(String bizSystemFlag) {
        this.bizSystemFlag = bizSystemFlag;
    }

    public String getBizSystemName() {
        return bizSystemName;
    }

    public void setBizSystemName(String bizSystemName) {
        this.bizSystemName = bizSystemName;
    }

    public String getBizSystemNetwork() {
        return bizSystemNetwork;
    }

    public void setBizSystemNetwork(String bizSystemNetwork) {
        this.bizSystemNetwork = bizSystemNetwork;
    }

    public String getClassifiedComtentFlag() {
        return classifiedComtentFlag;
    }

    public void setClassifiedComtentFlag(String classifiedComtentFlag) {
        this.classifiedComtentFlag = classifiedComtentFlag;
    }

    public String getSealFlag() {
        return sealFlag;
    }

    public void setSealFlag(String sealFlag) {
        this.sealFlag = sealFlag;
    }

    public String getRevokedUrl() {
        return revokedUrl;
    }

    public void setRevokedUrl(String revokedUrl) {
        this.revokedUrl = revokedUrl;
    }

    public String getOnlineQueryUrl() {
        return onlineQueryUrl;
    }

    public void setOnlineQueryUrl(String onlineQueryUrl) {
        this.onlineQueryUrl = onlineQueryUrl;
    }

    public String getOnlineValidUrl() {
        return onlineValidUrl;
    }

    public void setOnlineValidUrl(String onlineValidUrl) {
        this.onlineValidUrl = onlineValidUrl;
    }

    public String getHeadingSubject() {
        return headingSubject;
    }

    public void setHeadingSubject(String headingSubject) {
        this.headingSubject = headingSubject;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHardwareEnvironment() {
        return hardwareEnvironment;
    }

    public void setHardwareEnvironment(String hardwareEnvironment) {
        this.hardwareEnvironment = hardwareEnvironment;
    }

    public String getSoftwareEnvironment() {
        return softwareEnvironment;
    }

    public void setSoftwareEnvironment(String softwareEnvironment) {
        this.softwareEnvironment = softwareEnvironment;
    }

    public String getBusinessBehavior() {
        return businessBehavior;
    }

    public void setBusinessBehavior(String businessBehavior) {
        this.businessBehavior = businessBehavior;
    }

    public String getExtractCode() {
        return extractCode;
    }

    public void setExtractCode(String extractCode) {
        this.extractCode = extractCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getEnabledStatus() {
        return enabledStatus;
    }

    public void setEnabledStatus(String enabledStatus) {
        this.enabledStatus = enabledStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMetadTableName() {
        return metadTableName;
    }

    public void setMetadTableName(String metadTableName) {
        this.metadTableName = metadTableName;
    }

    public String getExpipyFlag() {
        return expipyFlag;
    }

    public void setExpipyFlag(String expipyFlag) {
        this.expipyFlag = expipyFlag;
    }

    public String getExpipyDate() {
        return expipyDate;
    }

    public void setExpipyDate(String expipyDate) {
        this.expipyDate = expipyDate;
    }

    public String getAutoAnnualFlag() {
        return autoAnnualFlag;
    }

    public void setAutoAnnualFlag(String autoAnnualFlag) {
        this.autoAnnualFlag = autoAnnualFlag;
    }

    public String getAutoAnnualDate() {
        return autoAnnualDate;
    }

    public void setAutoAnnualDate(String autoAnnualDate) {
        this.autoAnnualDate = autoAnnualDate;
    }

    public String getAutoCancelFlag() {
        return autoCancelFlag;
    }

    public void setAutoCancelFlag(String autoCancelFlag) {
        this.autoCancelFlag = autoCancelFlag;
    }

    public String getAutoCancelDate() {
        return autoCancelDate;
    }

    public void setAutoCancelDate(String autoCancelDate) {
        this.autoCancelDate = autoCancelDate;
    }

    public String getImplementCode() {
        return implementCode;
    }

    public void setImplementCode(String implementCode) {
        this.implementCode = implementCode;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public Date getSubmitAuditTime() {
        return submitAuditTime;
    }

    public void setSubmitAuditTime(Date submitAuditTime) {
        this.submitAuditTime = submitAuditTime;
    }

    public Date getOverAuditTime() {
        return overAuditTime;
    }

    public void setOverAuditTime(Date overAuditTime) {
        this.overAuditTime = overAuditTime;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
