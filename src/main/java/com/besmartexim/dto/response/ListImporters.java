package com.besmartexim.dto.response;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class ListImporters {
	
	private String importer_name;
	private Double value_inr;
	private Double value_usd;
	private Double quantity;
	private Double share;
	private Long shipment_count;
	
	
	public String getImporter_name() {
		return importer_name;
	}
	public void setImporter_name(String importer_name) {
		this.importer_name = importer_name;
	}
	public Double getValue_inr() {
		return value_inr;
	}
	public void setValue_inr(Double value_inr) {
		this.value_inr = value_inr;
	}
	public Double getValue_usd() {
		return value_usd;
	}
	public void setValue_usd(Double value_usd) {
		this.value_usd = value_usd;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getShare() {
		return share;
	}
	public void setShare(Double share) {
		this.share = share;
	}
	public Long getShipment_count() {
		return shipment_count;
	}
	public void setShipment_count(Long shipment_count) {
		this.shipment_count = shipment_count;
	}
	
	

}
