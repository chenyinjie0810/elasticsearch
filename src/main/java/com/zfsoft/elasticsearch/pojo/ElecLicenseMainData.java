package com.zfsoft.elasticsearch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 电子证照信息录入
 * @author chenjian
 */
@Document(indexName = "elms_elasticsearch", type = "ElecLicenseMainData")
public class ElecLicenseMainData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(type = FieldType.Text)
    @JsonProperty("oid")
    private String oid;

    @Field(type = FieldType.Text)
    @JsonProperty("elecLicenOid")
    private String elecLicenOid;

    @Field(type = FieldType.Keyword)
    @JsonProperty("licenseStatus")
    private String licenseStatus;

    @Field(type = FieldType.Text)
    @JsonProperty("licenseType")
    private String licenseType;

    @Field(type = FieldType.Text)
    @JsonProperty("claimOid")
    private String claimOid;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @JsonProperty("directoryName")
    private String directoryName;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @JsonProperty("holder")
    private String holder;

    @Field(type = FieldType.Text)
    @JsonProperty("identificateNumber")
    private String identificateNumber;

    /**
     *  持证人类型  0自然人  1企业（法人） 2混合 3其他
     */
    @Field(type = FieldType.Text)
    @JsonProperty("directoryObj")
    private String directoryObj;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @Field(format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss", type = FieldType.Date)
    @JsonProperty("createDate")
    private Date createDate;

    @Field(type = FieldType.Text)
    @JsonProperty("processState")
    private String processState;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",timezone ="GMT+8")
    @Field(format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", type = FieldType.Date)
    @JsonProperty("createTime")
    private Date createTime;


    @Field(type = FieldType.Text)
    @JsonProperty("version")
    private String version;

    @Field(type = FieldType.Keyword)
    @JsonProperty("organOid")
    private String organOid;

    /**
     * 电子证照唯一标识码
     */
    @Field(type = FieldType.Text)
    @JsonProperty("elecLicenseUnique")
    private String elecLicenseUnique;

    /**
     * 目录主键
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("directoryOid")
    private String directoryOid;

    /**
     * 签发主键
     */
    @Field(type = FieldType.Text)
    @JsonProperty("elecLicenseIssueOid")
    private String elecLicenseIssueOid;

    /**
     * 证照类别状态 A类 B类
     */
    @Field(type = FieldType.Text)
    @JsonProperty("licenseClassifyStatus")
    private String licenseClassifyStatus;

    /**
     * 证照来源
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("infoSource")
    private String infoSource;

    /**
     * 最终状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("finalStatus")
    private String finalStatus;

    /**
     * 注销审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("zxAuditFlag")
    private String zxAuditFlag;

    /**
     * 注销废立状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("zxCancelFlag")
    private String zxCancelFlag;

    /**
     * 吊销审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("dxAuditFlag")
    private String dxAuditFlag;

    /**
     * 吊销废立状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("dxCancelFlag")
    private String dxCancelFlag;

    /**
     * 延续审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("yxAuditFlag")
    private String yxAuditFlag;

    /**
     * 延续删除状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("yxDelFlag")
    private String yxDelFlag;

    /**
     * 纠错审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("jcAuditFlag")
    private String jcAuditFlag;

    /**
     * 纠错删除状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("jcDelFlag")
    private String jcDelFlag;

    /**
     * 是否需要年检
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("autoAnnualFlag")
    private String autoAnnualFlag;

    /**
     * 年检审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("njAuditFlag")
    private String njAuditFlag;

    /**
     * 年检删除状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("njDelFlag")
    private String njDelFlag;

    /**
     * 变更审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("bgAuditFlag")
    private String bgAuditFlag;

    /**
     * 变更前一次证照主键OID
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("bgPreElecOid")
    private String bgPreElecOid;

    /**
     * 上一版证照状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("preLicenseStatus")
    private String preLicenseStatus;

    /**
     * 变更后一次证照主键OID
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("bgNextElecOid")
    private String bgNextElecOid;

    /**
     * 变更后一次审核状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("bgNextAuditFlag")
    private String bgNextAuditFlag;

    /**
     * 作废状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("cancelFlag")
    private String cancelFlag;

    /**
     * 认领部门
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("claimOrganOid")
    private String claimOrganOid;

    /**
     * 认领删除状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("claimDelFlag")
    private String claimDelFlag;

    /**
     * 目录删除状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("dirDelFlag")
    private String dirDelFlag;

    /**
     * 目录启用状态
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("dirAbleFlag")
    private String dirAbleFlag;

    /**
     * 证照编号
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("licenseNumber")
    private String licenseNumber;

    /**
     * 目录状态 0:新建目录，2：待审核，3：审核通过 4：审核不通过，5：变更带审核，6：变更审核不通过,7:变更未提交审核
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("dirAuditStatus")
    private String dirAuditStatus;


    /**
     * 证照类型有效期表ID
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("timeOid")
    private String timeOid;

    /**
     * 是否需要自动注销
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("autoCancelFlag")
    private String autoCancelFlag;

    /**
     * 超期时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("overAuditTime")
    private Date overAuditTime;

    /**
     * @COLUMN_EXPLAIN : 提交审核时间
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("submitAuditTime")
    private Date submitAuditTime;


    /**
     * 超期自动注销时间
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("autoCancelDate")
    private String autoCancelDate;
    /**
     * 创建人 系统用户表主键
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("userOid")
    private String userOid;
    /**
     * 电子证照面元素id
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("tableParamOid")
    private String tableParamOid;
    /**
     * 根据模板照面元素生成对应表名称
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("tableName")
    private String tableName;
    /**
     * 照面元素表ID
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("metadataOid")
    private String metadataOid;
    /**
     * 有效期起
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("validityDateStart")
    private Date validityDateStart;
    /**
     * 有效期止
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("validityDateEnd")
    private Date validityDateEnd;
    /**
     * 自动年检提前时间
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("autoAnnualDate")
    private String autoAnnualDate;
    /**
     * 证照等级  A,B,C,D
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("elecLicenseLevel")
    private String elecLicenseLevel;
    /**
     * 联系电话
     */
    @Field(type = FieldType.Text)
    @JsonProperty("contactTel")
    private String contactTel;
    /**
     * 持证主体代码类型
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("holderCode")
    private String holderCode;
    /**
     * 水印内容
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("waterMark")
    private String waterMark;

    /**
     * 是否长期有效0是1否
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("longEffect")
    private String longEffect;

    /**
     * pdf加密后的路径 pdf的路径和证照文件的路径一样 后缀名不一样
     */
    @Field(type = FieldType.Keyword)
    @JsonProperty("attaPathMD5")
    private String attaPathMD5;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getElecLicenOid() {
        return elecLicenOid;
    }

    public void setElecLicenOid(String elecLicenOid) {
        this.elecLicenOid = elecLicenOid;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getClaimOid() {
        return claimOid;
    }

    public void setClaimOid(String claimOid) {
        this.claimOid = claimOid;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public String getOrganOid() {
        return organOid;
    }

    public void setOrganOid(String organOid) {
        this.organOid = organOid;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getIdentificateNumber() {
        return identificateNumber;
    }

    public void setIdentificateNumber(String identificateNumber) {
        this.identificateNumber = identificateNumber;
    }

    public String getDirectoryObj() {
        return directoryObj;
    }

    public String getAttaPathMD5() {
        return attaPathMD5;
    }

    public void setAttaPathMD5(String attaPathMD5) {
        this.attaPathMD5 = attaPathMD5;
    }

    public void setDirectoryObj(String directoryObj) {
        this.directoryObj = directoryObj;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getElecLicenseUnique() {
        return elecLicenseUnique;
    }

    public void setElecLicenseUnique(String elecLicenseUnique) {
        this.elecLicenseUnique = elecLicenseUnique;
    }

    public String getDirectoryOid() {
        return directoryOid;
    }

    public void setDirectoryOid(String directoryOid) {
        this.directoryOid = directoryOid;
    }

    public String getElecLicenseIssueOid() {
        return elecLicenseIssueOid;
    }

    public void setElecLicenseIssueOid(String elecLicenseIssueOid) {
        this.elecLicenseIssueOid = elecLicenseIssueOid;
    }

    public String getLicenseClassifyStatus() {
        return licenseClassifyStatus;
    }

    public void setLicenseClassifyStatus(String licenseClassifyStatus) {
        this.licenseClassifyStatus = licenseClassifyStatus;
    }

    public String getTimeOid() {
        return timeOid;
    }

    public void setTimeOid(String timeOid) {
        this.timeOid = timeOid;
    }

    public String getAutoCancelFlag() {
        return autoCancelFlag;
    }

    public void setAutoCancelFlag(String autoCancelFlag) {
        this.autoCancelFlag = autoCancelFlag;
    }

    public Date getOverAuditTime() {
        return overAuditTime;
    }

    public void setOverAuditTime(Date overAuditTime) {
        this.overAuditTime = overAuditTime;
    }

    public String getAutoCancelDate() {
        return autoCancelDate;
    }

    public void setAutoCancelDate(String autoCancelDate) {
        this.autoCancelDate = autoCancelDate;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public String getTableParamOid() {
        return tableParamOid;
    }

    public void setTableParamOid(String tableParamOid) {
        this.tableParamOid = tableParamOid;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMetadataOid() {
        return metadataOid;
    }

    public void setMetadataOid(String metadataOid) {
        this.metadataOid = metadataOid;
    }

    public Date getValidityDateStart() {
        return validityDateStart;
    }

    public void setValidityDateStart(Date validityDateStart) {
        this.validityDateStart = validityDateStart;
    }

    public Date getValidityDateEnd() {
        return validityDateEnd;
    }

    public void setValidityDateEnd(Date validityDateEnd) {
        this.validityDateEnd = validityDateEnd;
    }

    public String getAutoAnnualDate() {
        return autoAnnualDate;
    }

    public void setAutoAnnualDate(String autoAnnualDate) {
        this.autoAnnualDate = autoAnnualDate;
    }

    public String getElecLicenseLevel() {
        return elecLicenseLevel;
    }

    public void setElecLicenseLevel(String elecLicenseLevel) {
        this.elecLicenseLevel = elecLicenseLevel;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getHolderCode() {
        return holderCode;
    }

    public void setHolderCode(String holderCode) {
        this.holderCode = holderCode;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }

    public String getLongEffect() {
        return longEffect;
    }

    public void setLongEffect(String longEffect) {
        this.longEffect = longEffect;
    }

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getZxAuditFlag() {
        return zxAuditFlag;
    }

    public void setZxAuditFlag(String zxAuditFlag) {
        this.zxAuditFlag = zxAuditFlag;
    }

    public String getZxCancelFlag() {
        return zxCancelFlag;
    }

    public void setZxCancelFlag(String zxCancelFlag) {
        this.zxCancelFlag = zxCancelFlag;
    }

    public String getDxAuditFlag() {
        return dxAuditFlag;
    }

    public void setDxAuditFlag(String dxAuditFlag) {
        this.dxAuditFlag = dxAuditFlag;
    }

    public String getDxCancelFlag() {
        return dxCancelFlag;
    }

    public void setDxCancelFlag(String dxCancelFlag) {
        this.dxCancelFlag = dxCancelFlag;
    }

    public String getYxAuditFlag() {
        return yxAuditFlag;
    }

    public void setYxAuditFlag(String yxAuditFlag) {
        this.yxAuditFlag = yxAuditFlag;
    }

    public String getYxDelFlag() {
        return yxDelFlag;
    }

    public void setYxDelFlag(String yxDelFlag) {
        this.yxDelFlag = yxDelFlag;
    }

    public String getJcAuditFlag() {
        return jcAuditFlag;
    }

    public void setJcAuditFlag(String jcAuditFlag) {
        this.jcAuditFlag = jcAuditFlag;
    }

    public String getJcDelFlag() {
        return jcDelFlag;
    }

    public void setJcDelFlag(String jcDelFlag) {
        this.jcDelFlag = jcDelFlag;
    }

    public String getAutoAnnualFlag() {
        return autoAnnualFlag;
    }

    public void setAutoAnnualFlag(String autoAnnualFlag) {
        this.autoAnnualFlag = autoAnnualFlag;
    }

    public String getNjAuditFlag() {
        return njAuditFlag;
    }

    public void setNjAuditFlag(String njAuditFlag) {
        this.njAuditFlag = njAuditFlag;
    }

    public String getNjDelFlag() {
        return njDelFlag;
    }

    public void setNjDelFlag(String njDelFlag) {
        this.njDelFlag = njDelFlag;
    }

    public String getBgAuditFlag() {
        return bgAuditFlag;
    }

    public void setBgAuditFlag(String bgAuditFlag) {
        this.bgAuditFlag = bgAuditFlag;
    }

    public String getBgPreElecOid() {
        return bgPreElecOid;
    }

    public void setBgPreElecOid(String bgPreElecOid) {
        this.bgPreElecOid = bgPreElecOid;
    }

    public String getBgNextElecOid() {
        return bgNextElecOid;
    }

    public void setBgNextElecOid(String bgNextElecOid) {
        this.bgNextElecOid = bgNextElecOid;
    }

    public String getBgNextAuditFlag() {
        return bgNextAuditFlag;
    }

    public void setBgNextAuditFlag(String bgNextAuditFlag) {
        this.bgNextAuditFlag = bgNextAuditFlag;
    }

    public String getPreLicenseStatus() {
        return preLicenseStatus;
    }

    public void setPreLicenseStatus(String preLicenseStatus) {
        this.preLicenseStatus = preLicenseStatus;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getClaimOrganOid() {
        return claimOrganOid;
    }

    public void setClaimOrganOid(String claimOrganOid) {
        this.claimOrganOid = claimOrganOid;
    }

    public String getClaimDelFlag() {
        return claimDelFlag;
    }

    public void setClaimDelFlag(String claimDelFlag) {
        this.claimDelFlag = claimDelFlag;
    }

    public String getDirDelFlag() {
        return dirDelFlag;
    }

    public void setDirDelFlag(String dirDelFlag) {
        this.dirDelFlag = dirDelFlag;
    }

    public String getDirAbleFlag() {
        return dirAbleFlag;
    }

    public void setDirAbleFlag(String dirAbleFlag) {
        this.dirAbleFlag = dirAbleFlag;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getDirAuditStatus() {
        return dirAuditStatus;
    }

    public void setDirAuditStatus(String dirAuditStatus) {
        this.dirAuditStatus = dirAuditStatus;
    }

    public Date getSubmitAuditTime() {
        return submitAuditTime;
    }

    public void setSubmitAuditTime(Date submitAuditTime) {
        this.submitAuditTime = submitAuditTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ElecLicenseMainData{");
        sb.append("oid='").append(oid).append('\'');
        sb.append(", elecLicenOid='").append(elecLicenOid).append('\'');
        sb.append(", licenseStatus='").append(licenseStatus).append('\'');
        sb.append(", licenseType='").append(licenseType).append('\'');
        sb.append(", claimOid='").append(claimOid).append('\'');
        sb.append(", directoryName='").append(directoryName).append('\'');
        sb.append(", holder='").append(holder).append('\'');
        sb.append(", organOid='").append(organOid).append('\'');
        sb.append(", identificateNumber='").append(identificateNumber).append('\'');
        sb.append(", directoryObj='").append(directoryObj).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", processState='").append(processState).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", version='").append(version).append('\'');
        sb.append(", zxAuditFlag='").append(zxAuditFlag).append('\'');
        sb.append(", zxCancelFlag='").append(zxCancelFlag).append('\'');
        sb.append(", dxAuditFlag='").append(dxAuditFlag).append('\'');
        sb.append(", dxCancelFlag='").append(dxCancelFlag).append('\'');
        sb.append(", yxAuditFlag='").append(yxAuditFlag).append('\'');
        sb.append(", yxDelFlag='").append(yxDelFlag).append('\'');
        sb.append(", jcAuditFlag='").append(jcAuditFlag).append('\'');
        sb.append(", jcDelFlag='").append(jcDelFlag).append('\'');
        sb.append(", autoAnnualFlag='").append(autoAnnualFlag).append('\'');
        sb.append(", njAuditFlag='").append(njAuditFlag).append('\'');
        sb.append(", njDelFlag='").append(njDelFlag).append('\'');
        sb.append(", bgAuditFlag='").append(bgAuditFlag).append('\'');
        sb.append(", bgPreElecOid='").append(bgPreElecOid).append('\'');
        sb.append(", directoryOid='").append(directoryOid).append('\'');
        sb.append(", elecLicenseIssueOid='").append(elecLicenseIssueOid).append('\'');
        sb.append(", licenseClassifyStatus='").append(licenseClassifyStatus).append('\'');
        sb.append(", infoSource='").append(infoSource).append('\'');
        sb.append(", finalStatus='").append(finalStatus).append('\'');
        sb.append(", bgNextElecOid='").append(bgNextElecOid).append('\'');
        sb.append(", bgNextAuditFlag='").append(bgNextAuditFlag).append('\'');
        sb.append(", preLicenseStatus='").append(preLicenseStatus).append('\'');
        sb.append(", claimDelFlag='").append(claimDelFlag).append('\'');
        sb.append(", dirDelFlag='").append(dirDelFlag).append('\'');
        sb.append(", dirAbleFlag='").append(dirAbleFlag).append('\'');
        sb.append(", cancelFlag='").append(cancelFlag).append('\'');
        sb.append(", licenseNumber='").append(licenseNumber).append('\'');
        sb.append(", claimOrganOid='").append(claimOrganOid).append('\'');
        sb.append(", submitAuditTime='").append(submitAuditTime).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
