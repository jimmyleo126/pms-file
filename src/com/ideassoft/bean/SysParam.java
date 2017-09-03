package com.ideassoft.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TP_C_SYSPARAM", schema = "UCR_PMS")
public class SysParam implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -2137532036178475790L;
	private String paramId;
	private String paramType;
	private String paramName;
	private String paramDesc;
	private String content;
	private BigDecimal orderNo;
	private String status;

	// Constructors

	/** default constructor */
	public SysParam() {
	}

	/** minimal constructor */
	public SysParam(String paramId, String paramType, String paramName, String content, String status) {
		this.paramId = paramId;
		this.paramType = paramType;
		this.paramName = paramName;
		this.content = content;
		this.status = status;
	}

	/** full constructor */
	public SysParam(String paramId, String paramType, String paramName, String paramDesc, String content,
			BigDecimal orderNo, String status) {
		this.paramId = paramId;
		this.paramType = paramType;
		this.paramName = paramName;
		this.paramDesc = paramDesc;
		this.content = content;
		this.orderNo = orderNo;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "PARAM_ID", unique = true, nullable = false, length = 8)
	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	@Column(name = "PARAM_TYPE", nullable = false, length = 20)
	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	@Column(name = "PARAM_NAME", nullable = false, length = 30)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "PARAM_DESC", length = 100)
	public String getParamDesc() {
		return this.paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	@Column(name = "CONTENT", nullable = false, length = 50)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ORDER_NO", precision = 22, scale = 0)
	public BigDecimal getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}