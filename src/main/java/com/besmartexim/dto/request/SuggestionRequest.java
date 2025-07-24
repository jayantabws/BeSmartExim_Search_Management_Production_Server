package com.besmartexim.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class SuggestionRequest {	
	
	
	private TreadType tradeType;
	private String fromDate;
	private String toDate;
	private SearchBy searchBy;
	private String countryCode;
	private String searchValue;
	private String matchType;
	
	public TreadType getTradeType() {
		return tradeType;
	}
	public void setTradeType(TreadType tradeType) {
		this.tradeType = tradeType;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public SearchBy getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(SearchBy searchBy) {
		this.searchBy = searchBy;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	
	

}
