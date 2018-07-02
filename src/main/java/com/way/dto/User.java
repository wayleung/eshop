package com.way.dto;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5436690411447667663L;

	private Integer id;
	
	private String lanId;
	
	private String userName;
	
	private String password;
	
	private String status;
	
	private String isSuperAdmin;
	
	private Date unlockTime;
	
	private String lockFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}
	
	
	

	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanId) {
		this.lanId = lanId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", lanId=" + lanId + ", userName=" + userName
				+ ", password=" + password + ", status=" + status
				+ ", isSuperAdmin=" + isSuperAdmin + ", unlockTime="
				+ unlockTime + ", lockFlag=" + lockFlag + "]";
	}

	

	
	
	
	
}
