package com.besmartexim.dto.response;

public class ListDistinctColumnValues {
	
	private String column_name;
	private Long records_count;
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public Long getRecords_count() {
		return records_count;
	}
	public void setRecords_count(Long records_count) {
		this.records_count = records_count;
	}
	
	

}
