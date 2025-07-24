package com.besmartexim.dto.response;

public class CountryWiseCount {
	
	
	private String ctry_code;
	private String ctry_name;
	private Long shipment_count;
	
	public String getCtry_code() {
		return ctry_code;
	}
	public void setCtry_code(String ctry_code) {
		this.ctry_code = ctry_code;
	}
	public String getCtry_name() {
		return ctry_name;
	}
	public void setCtry_name(String ctry_name) {
		this.ctry_name = ctry_name;
	}
	public Long getShipment_count() {
		return shipment_count;
	}
	public void setShipment_count(Long shipment_count) {
		this.shipment_count = shipment_count;
	}
	
	

}
