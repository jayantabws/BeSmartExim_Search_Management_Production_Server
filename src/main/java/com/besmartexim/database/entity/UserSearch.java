package com.besmartexim.database.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_search")
public class UserSearch {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "search_json")
	private String searchJson;
	
	@Column(name = "search_query")
	private String searchQuery;
	
	@Column(name = "is_saved")
	private String isSaved;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "modified_by")
	private Long modifiedBy;
	
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "total_records")
	private Long totalRecords;
	
	@Column(name = "is_downloaded")
	private String isDownloaded;
	
	@Column(name = "downloaded_by")
	private Long downloadedBy;
	
	@Column(name = "downloaded_date")
	private Date downloadedDate;
	
	@Column(name = "records_downloaded")
	private Long recordsDownloaded;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchJson() {
		return searchJson;
	}

	public void setSearchJson(String searchJson) {
		this.searchJson = searchJson;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public String getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getIsDownloaded() {
		return isDownloaded;
	}

	public void setIsDownloaded(String isDownloaded) {
		this.isDownloaded = isDownloaded;
	}

	public Long getDownloadedBy() {
		return downloadedBy;
	}

	public void setDownloadedBy(Long downloadedBy) {
		this.downloadedBy = downloadedBy;
	}

	public Date getDownloadedDate() {
		return downloadedDate;
	}

	public void setDownloadedDate(Date downloadedDate) {
		this.downloadedDate = downloadedDate;
	}

	public Long getRecordsDownloaded() {
		return recordsDownloaded;
	}

	public void setRecordsDownloaded(Long recordsDownloaded) {
		this.recordsDownloaded = recordsDownloaded;
	}

	
	
}
