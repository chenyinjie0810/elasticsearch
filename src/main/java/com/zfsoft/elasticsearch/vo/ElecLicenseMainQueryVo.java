package com.zfsoft.elasticsearch.vo;

/**
 * @author chenjian
 * 窗口登记
 */
public class ElecLicenseMainQueryVo {
    private Integer pageNum;

    private Integer pageSize;

    private String oid;

    private String licenseStatus;

    private String directoryName;

    private String holder;

    private String identificateNumber;

    private String claimOid;

    private String organOid;

    /**
     * add by chenyq 20200313
     * 数据来源
     */
    private String infoSource;

    /**
     * 签发主键
     */
    private String elecLicenseIssueOid;

    /**
     * 注销审核状态
     */
    private String zxAuditFlag;

    /**
     * 注销废立状态
     */
    private String zxCancelFlag;

    /**
     * 吊销审核状态
     */
    private String dxAuditFlag;

    /**
     * 吊销废立状态
     */
    private String dxCancelFlag;

    /**
     * 纠错审核状态
     */
    private String jcAuditFlag;

    /**
     * 纠错删除状态
     */
    private String jcDelFlag;

    /**
     * 是否需要年检
     */
    private String autoAnnualFlag;

    /**
     * 年检审核状态
     */
    private String njAuditFlag;

    /**
     * 年检删除状态
     */
    private String njDelFlag;

    /**
     * 证照维护类型（1：注销、2：吊销、3：延续、4:纠错、5：变更）
     */
    private String whType;

    /**
     * 变更审核状态
     */
    private String bgAuditFlag;

    /**
     * 变更后一次证照主键OID
     */
    private String bgNextElecOid;

    /**
     * 变更后一次审核状态
     */
    private String bgNextAuditFlag;

    /**
     * 是否纠错
     */
    private String isJcFlag;

    /**
     * 证照类型（0：证照、1：批文）
     */
    private String licenseType;

    /**
     * 延续审核状态
     */
    private String yxAuditFlag;

    /**
     * 延续删除状态
     */
    private String yxDelFlag;

    /**
     * 作废状态
     */
    private String cancelFlag;

    /**
     * 认领部门
     */
    private String claimOrganOid;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
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

    public String getElecLicenseIssueOid() {
        return elecLicenseIssueOid;
    }

    public void setElecLicenseIssueOid(String elecLicenseIssueOid) {
        this.elecLicenseIssueOid = elecLicenseIssueOid;
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

    public String getClaimOid() {
        return claimOid;
    }

    public void setClaimOid(String claimOid) {
        this.claimOid = claimOid;
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

    public String getWhType() {
        return whType;
    }

    public void setWhType(String whType) {
        this.whType = whType;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
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

    public String getIsJcFlag() {
        return isJcFlag;
    }

    public void setIsJcFlag(String isJcFlag) {
        this.isJcFlag = isJcFlag;
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

    public String getBgAuditFlag() {
        return bgAuditFlag;
    }

    public void setBgAuditFlag(String bgAuditFlag) {
        this.bgAuditFlag = bgAuditFlag;
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

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }
}
