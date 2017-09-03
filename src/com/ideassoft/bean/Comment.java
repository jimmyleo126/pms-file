package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_COMMENT", schema = "UCR_PMS")
public class Comment implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6395215802888008925L;
	private String commentId;
	private String branchId;
	private String orderId;
	private String memberId;
	private double serviceScore;
	private double facilityScore;
	private double environmentScore;
	private double locationScore;
	private String commentContent;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String relativeComment;
	private String remark;
	
	@Id
	@Column(name = "COMMENT_ID", unique = true, nullable = false, length = 17)
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "ORDER_ID",  nullable = false, length = 16)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "MEMBER_ID", nullable = false, length = 8)
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Column(name = "SERVICE_SCORE", precision = 2)
	public double getServiceScore() {
		return serviceScore;
	}
	public void setServiceScore(double serviceScore) {
		this.serviceScore = serviceScore;
	}
	
	@Column(name = "FACILITY_SCORE",  precision = 2)
	public double getFacilityScore() {
		return facilityScore;
	}
	public void setFacilityScore(double facilityScore) {
		this.facilityScore = facilityScore;
	}
	
	@Column(name = "ENVIRONMENT_SCORE",  precision = 2)
	public double getEnvironmentScore() {
		return environmentScore;
	}
	public void setEnvironmentScore(double environmentScore) {
		this.environmentScore = environmentScore;
	}
	
	@Column(name = "LOCATION_SCORE", precision = 2)
	public double getLocationScore() {
		return locationScore;
	}
	public void setLocationScore(double locationScore) {
		this.locationScore = locationScore;
	}
	
	@Column(name = "COMMENT_CONTENT", length = 1000)
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", length = 7)
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	@Column(name = "RECORD_USER", length = 8)
	
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	
	@Column(name = "RELATIVE_COMMENT", nullable = true, length = 17)
	public String getRelativeComment() {
		return relativeComment;
	}
	public void setRelativeComment(String relativeComment) {
		this.relativeComment = relativeComment;
	}
	
	@Column(name = "REMARK",nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
