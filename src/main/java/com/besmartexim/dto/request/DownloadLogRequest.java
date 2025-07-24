package com.besmartexim.dto.request;

public class DownloadLogRequest {

	
	private Long searchId;
	private String downloadJson;
	private Long recordsDownloaded;
	private Long initialCredit;
	private Long remainingCredit;	
	
	// Getter & setter
	
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
	
}
