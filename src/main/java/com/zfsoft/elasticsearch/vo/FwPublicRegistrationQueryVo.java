package com.zfsoft.elasticsearch.vo;

import java.util.Date;

public class FwPublicRegistrationQueryVo {

    private Integer pageNum;

    private Integer pageSize;

    private String regNumber;

    private String applicantName;

    private Date createDateBegin;

    private Date createDateEnd;

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

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }
}
