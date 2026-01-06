package com.besmartexim.dto.response;

import java.math.BigDecimal;

public class GraphResponse {
	
	private String monthName;
	private BigDecimal monthValue;
	private BigDecimal industryExportValue;
	private BigDecimal relativeStrengthValue;
	
	
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public BigDecimal getMonthValue() {
		return monthValue;
	}
	public void setMonthValue(BigDecimal monthValue) {
		this.monthValue = monthValue;
	}
	public BigDecimal getIndustryExportValue() {
		return industryExportValue;
	}
	public void setIndustryExportValue(BigDecimal industryExportValue) {
		this.industryExportValue = industryExportValue;
	}
	public BigDecimal getRelativeStrengthValue() {
		return relativeStrengthValue;
	}
	public void setRelativeStrengthValue(BigDecimal relativeStrengthValue) {
		this.relativeStrengthValue = relativeStrengthValue;
	}
	public GraphResponse(String monthName) {
		super();
		this.monthName = monthName;
	}
	public GraphResponse() {
		super();
	}
	
	
}
