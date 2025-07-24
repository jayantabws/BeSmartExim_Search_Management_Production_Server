package com.besmartexim.dto.response;

import java.util.Date;

import com.besmartexim.dto.request.UserSearchRequest;

public class SearchDetails {

	public Long searchId;
	
	//public String searchParams;
	public UserSearchRequest userSearchQuery;
	
	 
	public Date createdDate;	
	public long totalRecords;	
	public Long createdBy;
	public String createdByName;
	public String createdByEmail;
	public String isDownloaded;
	public Date downloadedDate;
	public Long downloadedBy;
	public String downloadedByName;
	public String downloadedByEmail;
	public Long recordsDownloaded;
	
	
	public Long getSearchId() {
		return searchId;
	}
	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}
	public UserSearchRequest getUserSearchQuery() {
		return userSearchQuery;
	}
	public void setUserSearchQuery(UserSearchRequest userSearchQuery) {
		this.userSearchQuery = userSearchQuery;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getCreatedByEmail() {
		return createdByEmail;
	}
	public void setCreatedByEmail(String createdByEmail) {
		this.createdByEmail = createdByEmail;
	}
	public String getIsDownloaded() {
		return isDownloaded;
	}
	public void setIsDownloaded(String isDownloaded) {
		this.isDownloaded = isDownloaded;
	}
	public Date getDownloadedDate() {
		return downloadedDate;
	}
	public void setDownloadedDate(Date downloadedDate) {
		this.downloadedDate = downloadedDate;
	}
	public Long getDownloadedBy() {
		return downloadedBy;
	}
	public void setDownloadedBy(Long downloadedBy) {
		this.downloadedBy = downloadedBy;
	}
	public String getDownloadedByName() {
		return downloadedByName;
	}
	public void setDownloadedByName(String downloadedByName) {
		this.downloadedByName = downloadedByName;
	}
	public String getDownloadedByEmail() {
		return downloadedByEmail;
	}
	public void setDownloadedByEmail(String downloadedByEmail) {
		this.downloadedByEmail = downloadedByEmail;
	}
	public Long getRecordsDownloaded() {
		return recordsDownloaded;
	}
	public void setRecordsDownloaded(Long recordsDownloaded) {
		this.recordsDownloaded = recordsDownloaded;
	}
	
	
	
	

	
}
