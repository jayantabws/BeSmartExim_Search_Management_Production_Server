package com.besmartexim.dto.request;

import java.util.List;

public class QueryBuilder {
	private String relation;
	private String matchType;
	private String searchBy;
	private List<String> searchValue;

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public List<String> getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(List<String> searchValue) {
		this.searchValue = searchValue;
	}
}
