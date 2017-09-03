package com.ideassoft.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoomKey implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -4362652355008073505L;
	private String branchId;
	private String roomId;
	private String status;

	// Constructors

	/** default constructor */
	public RoomKey() {
	}

	/** full constructor */
	public RoomKey(String branchId, String roomId, String status) {
		this.branchId = branchId;
		this.roomId = roomId;
		this.status = status;
	}

	// Property accessors

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoomKey))
			return false;
		RoomKey castOther = (RoomKey) other;

		return ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null
				&& castOther.getBranchId() != null && this.getBranchId().equals(castOther.getBranchId())))
				&& ((this.getRoomId() == castOther.getRoomId()) || (this.getRoomId() != null
				&& castOther.getRoomId() != null && this.getRoomId().equals(castOther.getRoomId())))
				&& ((this.getStatus() == castOther.getStatus()) || (this.getStatus() != null
				&& castOther.getStatus() != null && this.getStatus().equals(castOther.getStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
		result = 37 * result + (getRoomId() == null ? 0 : this.getRoomId().hashCode());
		result = 37 * result + (getStatus() == null ? 0 : this.getStatus().hashCode());
		return result;
	}

}