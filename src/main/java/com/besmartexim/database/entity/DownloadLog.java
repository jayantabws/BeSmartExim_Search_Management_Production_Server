package com.besmartexim.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "download_log")
public class DownloadLog {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "search_id")
	private Long searchId;
	
	@Column(name = "download_json")
	private String downloadJson;
	
	@Column(name = "records_downloaded")
	private Long recordsDownloaded;
	
	@Column(name = "initial_credit")
	private Long initialCredit;
	
	@Column(name = "remaining_credit")
	private Long remainingCredit;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_date")
	private Date createdDate;


	
	
	// Getter & Setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSearchId() {
		return searchId;
	}

	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}

	public String getDownloadJson() {
		return downloadJson;
	}

	public void setDownloadJson(String downloadJson) {
		this.downloadJson = downloadJson;
	}

	public Long getRecordsDownloaded() {
		return recordsDownloaded;
	}

	public void setRecordsDownloaded(Long recordsDownloaded) {
		this.recordsDownloaded = recordsDownloaded;
	}

	public Long getInitialCredit() {
		return initialCredit;
	}

	public void setInitialCredit(Long initialCredit) {
		this.initialCredit = initialCredit;
	}

	public Long getRemainingCredit() {
		return remainingCredit;
	}

	public void setRemainingCredit(Long remainingCredit) {
		this.remainingCredit = remainingCredit;
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
}
