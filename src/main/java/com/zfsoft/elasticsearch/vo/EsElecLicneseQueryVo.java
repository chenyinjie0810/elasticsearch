package com.zfsoft.elasticsearch.vo;

/**
 * @author 郑家祥
 * @className EsElecLicneseQueryVo
 * @description 证照查询，郑家祥测试使用vo
 * @date 2020/3/9 15:19
 */
public class EsElecLicneseQueryVo {

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 证照名称
     */
    private String directoryName;

    /**
     * 持有者名称
     */
    private String identificateNumber;

    /**
     *  持证人名称（法人名称）
     */
    private String holder;

    /**
     *  持证人类型  0自然人  1企业（法人） 2混合 3其他
     */
    private String holderType;

    /**
     * 认领机构
     */
    private String organOid;

    /**
     * 证照状态
     */
    private String licenseStatus;


    /**
     * 所属电子证照目录主键 对应T_ELEC _LICENSE_DIR_MAIN表主键
     */
    private String directoryOid;


    /**
     * 证照类别状态  A类 B类
     */
    private String licenseClassifyStatus;


    /**
     * 电子证照实施清单审核状态
     */
    private String dirAuditPass;

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getIdentificateNumber() {
        return identificateNumber;
    }

    public void setIdentificateNumber(String identificateNumber) {
        this.identificateNumber = identificateNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

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

    public String getOrganOid() {
        return organOid;
    }

    public void setOrganOid(String organOid) {
        this.organOid = organOid;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getDirectoryOid() {
        return directoryOid;
    }

    public void setDirectoryOid(String directoryOid) {
        this.directoryOid = directoryOid;
    }

    public String getLicenseClassifyStatus() {
        return licenseClassifyStatus;
    }

    public void setLicenseClassifyStatus(String licenseClassifyStatus) {
        this.licenseClassifyStatus = licenseClassifyStatus;
    }

    public String getDirAuditPass() {
        return dirAuditPass;
    }

    public void setDirAuditPass(String dirAuditPass) {
        this.dirAuditPass = dirAuditPass;
    }
}
