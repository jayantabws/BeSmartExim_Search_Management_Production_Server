package com.besmartexim.dto.response;

import java.util.Date;

public class UserDetailsResponse {
	
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	private String password;
	private String userType;
	private String companyName;
	private String isActive;
	private Date createdDate;
	private Date subscriptionExpiredDate;
	private Long uplineId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getSubscriptionExpiredDate() {
		return subscriptionExpiredDate;
	}
	public void setSubscriptionExpiredDate(Date subscriptionExpiredDate) {
		this.subscriptionExpiredDate = subscriptionExpiredDate;
	}
	public Long getUplineId() {
		return uplineId;
	}
	public void setUplineId(Long uplineId) {
		this.uplineId = uplineId;
	}
	
	
	
}
