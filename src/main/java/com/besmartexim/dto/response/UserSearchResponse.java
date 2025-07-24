package com.besmartexim.dto.response;

import java.util.List;

public class UserSearchResponse {

	public Long searchId;
	
	public List<ExpInd> expIndList;
	
	public List<ImpInd> impIndList;
	
	public List<ExpFor> expForeignList;
	
	public List<ImpFor> impForeignList;

	public List<ExpInd> getExpIndList() {
		return expIndList;
	}

	public void setExpIndList(List<ExpInd> expIndList) {
		this.expIndList = expIndList;
	}	

	public List<ImpInd> getImpIndList() {
		return impIndList;
	}

	public void setImpIndList(List<ImpInd> impIndList) {
		this.impIndList = impIndList;
	}

	public Long getSearchId() {
		return searchId;
	}

	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}

	public List<ExpFor> getExpForeignList() {
		return expForeignList;
	}

	public void setExpForeignList(List<ExpFor> expForeignList) {
		this.expForeignList = expForeignList;
	}

	public List<ImpFor> getImpForeignList() {
		return impForeignList;
	}

	public void setImpForeignList(List<ImpFor> impForeignList) {
		this.impForeignList = impForeignList;
	}
	
	
}
