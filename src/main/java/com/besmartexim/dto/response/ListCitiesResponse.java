package com.besmartexim.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ListCitiesResponse {
	
	List<ListCities> citiesList;
	
	private Double totalQuantity;
	private BigDecimal totalValueUSD;
	private BigDecimal totalValueINR;
	private Long shipmentCount;
	private Double valueShare;
	
	private Double totalQuantityTop10;
	private BigDecimal totalValueUSDTop10;
	private BigDecimal totalValueINRTop10;
	private Long shipmentCountTop10;
	private Double valueShareTop10;

	public List<ListCities> getCitiesList() {
		return citiesList;
	}

	public void setCitiesList(List<ListCities> citiesList) {
		this.citiesList = citiesList;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getTotalValueUSD() {
		return totalValueUSD;
	}

	public void setTotalValueUSD(BigDecimal totalValueUSD) {
		this.totalValueUSD = totalValueUSD;
	}

	public BigDecimal getTotalValueINR() {
		return totalValueINR;
	}

	public void setTotalValueINR(BigDecimal totalValueINR) {
		this.totalValueINR = totalValueINR;
	}

	public Long getShipmentCount() {
		return shipmentCount;
	}

	public void setShipmentCount(Long shipmentCount) {
		this.shipmentCount = shipmentCount;
	}

	public Double getValueShare() {
		return valueShare;
	}

	public void setValueShare(Double valueShare) {
		this.valueShare = valueShare;
	}

	public Double getTotalQuantityTop10() {
		return totalQuantityTop10;
	}

	public void setTotalQuantityTop10(Double totalQuantityTop10) {
		this.totalQuantityTop10 = totalQuantityTop10;
	}

	public BigDecimal getTotalValueUSDTop10() {
		return totalValueUSDTop10;
	}

	public void setTotalValueUSDTop10(BigDecimal totalValueUSDTop10) {
		this.totalValueUSDTop10 = totalValueUSDTop10;
	}

	public BigDecimal getTotalValueINRTop10() {
		return totalValueINRTop10;
	}

	public void setTotalValueINRTop10(BigDecimal totalValueINRTop10) {
		this.totalValueINRTop10 = totalValueINRTop10;
	}

	public Long getShipmentCountTop10() {
		return shipmentCountTop10;
	}

	public void setShipmentCountTop10(Long shipmentCountTop10) {
		this.shipmentCountTop10 = shipmentCountTop10;
	}

	public Double getValueShareTop10() {
		return valueShareTop10;
	}

	public void setValueShareTop10(Double valueShareTop10) {
		this.valueShareTop10 = valueShareTop10;
	}
	
		

}
