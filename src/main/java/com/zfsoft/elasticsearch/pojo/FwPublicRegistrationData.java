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
 * 窗口登记对象
 * @author chenjian
 */
@Document(indexName = "elms_elasticsearch", type = "FwPublicRegistrationData")
public class FwPublicRegistrationData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(type = FieldType.Text)
    @JsonProperty("oid")
    private String oid;

    @Field(type = FieldType.Text)
    @JsonProperty("fwOid")
    private String fwOid;

    @Field(type = FieldType.Text)
    @JsonProperty("handleOid")
    private String handleOid;

    @Field(type = FieldType.Text)
    @JsonProperty("handleLinkOid")
    private String handleLinkOid;

    @Field(type = FieldType.Text)
    @JsonProperty("actionName")
    private String actionName;

    @Field(type = FieldType.Text)
    @JsonProperty("personOid")
    private String personOid;

    @Field(type = FieldType.Text)
    @JsonProperty("linkName")
    private String linkName;

    @Field(type = FieldType.Text)
    @JsonProperty("workflowLinkOid")
    private String workflowLinkOid;

    @Field(type = FieldType.Text)
    @JsonProperty("stepOid")
    private String stepOid;

    @Field(type = FieldType.Text)
    @JsonProperty("regNumber")
    private String regNumber;

    @Field(type = FieldType.Text)
    @JsonProperty("serviceName")
    private String serviceName;

    @Field(type = FieldType.Text)
    @JsonProperty("applicantName")
    private String applicantName;

    @Field(type = FieldType.Text)
    @JsonProperty("stepName")
    private String stepName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Field(format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", type = FieldType.Date)
    @JsonProperty("createDate")
    private Date createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Field(format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", type = FieldType.Date)
    @JsonProperty("limitDate")
    private Date limitDate;

    @Field(type = FieldType.Text)
    @JsonProperty("processState")
    private String processState;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Field(format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", type = FieldType.Date)
    @JsonProperty("@timestamp")
    private Date createTime;

    @Field(type = FieldType.Text)
    @JsonProperty("@version")
    private String version;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getHandleOid() {
        return handleOid;
    }

    public void setHandleOid(String handleOid) {
        this.handleOid = handleOid;
    }

    public String getHandleLinkOid() {
        return handleLinkOid;
    }

    public void setHandleLinkOid(String handleLinkOid) {
        this.handleLinkOid = handleLinkOid;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getPersonOid() {
        return personOid;
    }

    public void setPersonOid(String personOid) {
        this.personOid = personOid;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getWorkflowLinkOid() {
        return workflowLinkOid;
    }

    public void setWorkflowLinkOid(String workflowLinkOid) {
        this.workflowLinkOid = workflowLinkOid;
    }

    public String getStepOid() {
        return stepOid;
    }

    public void setStepOid(String stepOid) {
        this.stepOid = stepOid;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
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

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getFwOid() {
        return fwOid;
    }

    public void setFwOid(String fwOid) {
        this.fwOid = fwOid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwPublicRegistrationData{");
        sb.append("oid='").append(oid).append('\'');
        sb.append(", fwOid='").append(fwOid).append('\'');
        sb.append(", handleOid='").append(handleOid).append('\'');
        sb.append(", handleLinkOid='").append(handleLinkOid).append('\'');
        sb.append(", actionName='").append(actionName).append('\'');
        sb.append(", personOid='").append(personOid).append('\'');
        sb.append(", linkName='").append(linkName).append('\'');
        sb.append(", workflowLinkOid='").append(workflowLinkOid).append('\'');
        sb.append(", stepOid='").append(stepOid).append('\'');
        sb.append(", regNumber='").append(regNumber).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", applicantName='").append(applicantName).append('\'');
        sb.append(", stepName='").append(stepName).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", limitDate=").append(limitDate);
        sb.append(", processState='").append(processState).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
