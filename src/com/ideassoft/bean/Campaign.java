package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * TbCCampaigns entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_C_CAMPAIGNS", schema = "UCR_PMS")
public class Campaign implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2398444820880479077L;
	private String dataId;
	private String campaignName;
	private String campaignType;
	private String campaignDesc;
	private String usingRange;
	private String usingPerson;
	private String usingType;
	private Date startTime;
	private Date endTime;
	private String campaignCycle;
	private String businessId;
	private Date recordTime;
	private String recordUser;
	private String priority;
	private Double couponPrice;
	private String couponGroupId;
	private Double operMoney;
	private String operType;
	private String operScore;
	private String roomCount;
	private String gift;
	private String goodsService;
	private String experienceCount;
	private String branchId;
	private String roomType;
	private String discount;
	private Double discountPrice;
	private String liveDay;
	private String currentDay;
	private String dayCount;
	private String remark;
	private String status;

	// Constructors

	/** default constructor */
	public Campaign() {
	}

	/** minimal constructor */
	public Campaign(String dataId, String campaignName, String campaignType, String campaignDesc,
			String usingRange, String usingPerson, String usingType, Date startTime, Date endTime, String businessId,
			String recordUser, String priority, String status) {
		this.dataId = dataId;
		this.campaignName = campaignName;
		this.campaignType = campaignType;
		this.campaignDesc = campaignDesc;
		this.usingRange = usingRange;
		this.usingPerson = usingPerson;
		this.usingType = usingType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.businessId = businessId;
		this.recordUser = recordUser;
		this.priority = priority;
		this.status = status;
	}

	/** full constructor */
	public Campaign(String dataId, String campaignName, String campaignType, String campaignDesc,
			String usingRange, String usingPerson, String usingType, Date startTime, Date endTime,
			String campaignCycle, String businessId, Date recordTime, String recordUser, String priority,
			Double couponPrice, String couponGroupId, Double operMoney, String operType, String operScore,
			String roomCount, String gift, String goodsService, String experienceCount, String branchId,
			String roomType, String discount, Double discountPrice, String liveDay, String currentDay, String dayCount,
			String remark, String status) {
		this.dataId = dataId;
		this.campaignName = campaignName;
		this.campaignType = campaignType;
		this.campaignDesc = campaignDesc;
		this.usingRange = usingRange;
		this.usingPerson = usingPerson;
		this.usingType = usingType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.campaignCycle = campaignCycle;
		this.businessId = businessId;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.priority = priority;
		this.couponPrice = couponPrice;
		this.couponGroupId = couponGroupId;
		this.operMoney = operMoney;
		this.operType = operType;
		this.operScore = operScore;
		this.roomCount = roomCount;
		this.gift = gift;
		this.goodsService = goodsService;
		this.experienceCount = experienceCount;
		this.branchId = branchId;
		this.roomType = roomType;
		this.discount = discount;
		this.discountPrice = discountPrice;
		this.liveDay = liveDay;
		this.currentDay = currentDay;
		this.dayCount = dayCount;
		this.remark = remark;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 16)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "CAMPAIGN_NAME", nullable = false, length = 30)
	public String getCampaignName() {
		return this.campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@Column(name = "CAMPAIGN_TYPE", nullable = false, length = 2)
	public String getCampaignType() {
		return this.campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	@Column(name = "CAMPAIGN_DESC", nullable = false, length = 200)
	public String getCampaignDesc() {
		return this.campaignDesc;
	}

	public void setCampaignDesc(String campaignDesc) {
		this.campaignDesc = campaignDesc;
	}

	@Column(name = "USING_RANGE", nullable = false, length = 2)
	public String getUsingRange() {
		return this.usingRange;
	}

	public void setUsingRange(String usingRange) {
		this.usingRange = usingRange;
	}

	@Column(name = "USING_PERSON", nullable = false, length = 12)
	public String getUsingPerson() {
		return this.usingPerson;
	}

	public void setUsingPerson(String usingPerson) {
		this.usingPerson = usingPerson;
	}

	@Column(name = "USING_TYPE", nullable = false, length = 6)
	public String getUsingType() {
		return this.usingType;
	}

	public void setUsingType(String usingType) {
		this.usingType = usingType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Column(name = "END_TIME", nullable = false, length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "CAMPAIGN_CYCLE", length = 30)
	public String getCampaignCycle() {
		return this.campaignCycle;
	}

	public void setCampaignCycle(String campaignCycle) {
		this.campaignCycle = campaignCycle;
	}

	@Column(name = "BUSINESS_ID", nullable = false, length = 3)
	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECORD_TIME", insertable = false, updatable = true)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "PRIORITY", nullable = false, length = 2)
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(name = "COUPON_PRICE", precision = 9)
	public Double getCouponPrice() {
		return this.couponPrice;
	}

	public void setCouponPrice(Double couponPrice) {
		this.couponPrice = couponPrice;
	}

	@Column(name = "COUPON_GROUPID", length = 10)
	public String getCouponGroupId() {
		return this.couponGroupId;
	}

	public void setCouponGroupId(String couponGroupId) {
		this.couponGroupId = couponGroupId;
	}

	@Column(name = "OPER_MONEY", precision = 9)
	public Double getOperMoney() {
		return this.operMoney;
	}

	public void setOperMoney(Double operMoney) {
		this.operMoney = operMoney;
	}

	@Column(name = "OPER_TYPE", length = 2)
	public String getOperType() {
		return this.operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Column(name = "OPER_SCORE", length = 10)
	public String getOperScore() {
		return this.operScore;
	}

	public void setOperScore(String operScore) {
		this.operScore = operScore;
	}

	@Column(name = "ROOM_COUNT", length = 3)
	public String getRoomCount() {
		return this.roomCount;
	}

	public void setRoomCount(String roomCount) {
		this.roomCount = roomCount;
	}

	@Column(name = "GIFT", length = 10)
	public String getGift() {
		return this.gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	@Column(name = "GOODS_SERVICE", length = 10)
	public String getGoodsService() {
		return this.goodsService;
	}

	public void setGoodsService(String goodsService) {
		this.goodsService = goodsService;
	}

	@Column(name = "EXPERIENCE_COUNT", length = 2)
	public String getExperienceCount() {
		return this.experienceCount;
	}

	public void setExperienceCount(String experienceCount) {
		this.experienceCount = experienceCount;
	}

	@Column(name = "BRANCH_ID", length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_TYPE", length = 3)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "DISCOUNT", length = 3)
	public String getDiscount() {
		return this.discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Column(name = "DISCOUNT_PRICE", precision = 9)
	public Double getDiscountPrice() {
		return this.discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	@Column(name = "LIVE_DAY", length = 4)
	public String getLiveDay() {
		return this.liveDay;
	}

	public void setLiveDay(String liveDay) {
		this.liveDay = liveDay;
	}

	@Column(name = "CURRENT_DAY", length = 4)
	public String getCurrentDay() {
		return this.currentDay;
	}

	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}

	@Column(name = "DAY_COUNT", length = 3)
	public String getDayCount() {
		return this.dayCount;
	}

	public void setDayCount(String dayCount) {
		this.dayCount = dayCount;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}