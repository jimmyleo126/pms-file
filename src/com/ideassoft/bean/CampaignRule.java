package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbCCampaignsRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_C_CAMPAIGNS_RULE", schema = "UCR_PMS")
public class CampaignRule implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5148364667906105144L;
	private String dataId;
	private String campaignId;
	private String liveDay;
	private String currentDay;
	private String dayCount;
	private String status;

	// Constructors

	/** default constructor */
	public CampaignRule() {
	}

	/** minimal constructor */
	public CampaignRule(String dataId, String campaignId, String status) {
		this.dataId = dataId;
		this.campaignId = campaignId;
		this.status = status;
	}

	/** full constructor */
	public CampaignRule(String dataId, String campaignId, String liveDay, String currentDay, String dayCount,
			String status) {
		this.dataId = dataId;
		this.campaignId = campaignId;
		this.liveDay = liveDay;
		this.currentDay = currentDay;
		this.dayCount = dayCount;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 10)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "CAMPAIGN_ID", nullable = false, length = 16)
	public String getCampaignId() {
		return this.campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
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

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}