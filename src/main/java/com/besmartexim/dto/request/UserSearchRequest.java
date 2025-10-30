package com.besmartexim.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class UserSearchRequest {

	//@NotBlank
	private SearchType searchType;
	
	//@NotBlank
	private TreadType tradeType;
	
	@NotBlank
	private String fromDate;
	
	@NotBlank
	private String toDate;
	
	//@NotBlank
	private SearchBy searchBy;	
	
	private List<String> searchValue;
	
	private MatchType matchType;
	
	//@Max(value = 3)
	//private String countryCode;
	
	private Object countryCode;
	
	private Long searchId;
	
	private List<String> hsCodeList;
	
	private List<String> hsCode4DigitList;
	
	private List<String> exporterList;
	
	private List<String> importerList;
	
	private List<String> cityOriginList;
	
	private List<String> cityDestinationList;
	
	private List<String> portOriginList;
	
	private List<String> portDestinationList;	
	
	private String columnName;
	
	private String orderByColumn;
	
	private String orderByMode;
	
	private Integer pageNumber;
	
	private Integer numberOfRecords;

	private List<QueryBuilder> queryBuilder;
	
	private List<String> shipModeList;
	
	private List<String> stdUnitList;
	
	
	private String rangeQuantityStart;
	
	private String rangeQuantityEnd;
	
	private List<String> consumptionType;
	
	private String rangeValueUsdStart;
	
	private String rangeValueUsdEnd;
	
	private String rangeUnitPriceUsdStart;
	
	private String rangeUnitPriceUsdEnd;
	
	private List<String> incoterm;
	
	private List<String> notifyParty;
	
	private List<String> productDesc;
	
	private String conditionProductDesc;
	
	
	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

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

	public List<String> getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(List<String> searchValue) {
		this.searchValue = searchValue;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}
	
	public Object getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Object countryCode) {
		this.countryCode = countryCode;
	}

	public Long getSearchId() {
		return searchId;
	}

	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}

	public List<String> getHsCodeList() {
		return hsCodeList;
	}

	public void setHsCodeList(List<String> hsCodeList) {
		this.hsCodeList = hsCodeList;
	}

	public List<String> getHsCode4DigitList() {
		return hsCode4DigitList;
	}

	public void setHsCode4DigitList(List<String> hsCode4DigitList) {
		this.hsCode4DigitList = hsCode4DigitList;
	}

	public List<String> getExporterList() {
		return exporterList;
	}

	public void setExporterList(List<String> exporterList) {
		this.exporterList = exporterList;
	}

	public List<String> getImporterList() {
		return importerList;
	}

	public void setImporterList(List<String> importerList) {
		this.importerList = importerList;
	}

	public List<String> getCityOriginList() {
		return cityOriginList;
	}

	public void setCityOriginList(List<String> cityOriginList) {
		this.cityOriginList = cityOriginList;
	}

	public List<String> getCityDestinationList() {
		return cityDestinationList;
	}

	public void setCityDestinationList(List<String> cityDestinationList) {
		this.cityDestinationList = cityDestinationList;
	}

	public List<String> getPortOriginList() {
		return portOriginList;
	}

	public void setPortOriginList(List<String> portOriginList) {
		this.portOriginList = portOriginList;
	}

	public List<String> getPortDestinationList() {
		return portDestinationList;
	}

	public void setPortDestinationList(List<String> portDestinationList) {
		this.portDestinationList = portDestinationList;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getOrderByMode() {
		return orderByMode;
	}

	public void setOrderByMode(String orderByMode) {
		this.orderByMode = orderByMode;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public List<QueryBuilder> getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(List<QueryBuilder> queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public List<String> getShipModeList() {
		return shipModeList;
	}

	public void setShipModeList(List<String> shipModeList) {
		this.shipModeList = shipModeList;
	}

	public List<String> getStdUnitList() {
		return stdUnitList;
	}

	public void setStdUnitList(List<String> stdUnitList) {
		this.stdUnitList = stdUnitList;
	}

	public String getRangeQuantityStart() {
		return rangeQuantityStart;
	}

	public void setRangeQuantityStart(String rangeQuantityStart) {
		this.rangeQuantityStart = rangeQuantityStart;
	}

	public String getRangeQuantityEnd() {
		return rangeQuantityEnd;
	}

	public void setRangeQuantityEnd(String rangeQuantityEnd) {
		this.rangeQuantityEnd = rangeQuantityEnd;
	}

	public List<String> getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(List<String> consumptionType) {
		this.consumptionType = consumptionType;
	}

	public String getRangeValueUsdStart() {
		return rangeValueUsdStart;
	}

	public void setRangeValueUsdStart(String rangeValueUsdStart) {
		this.rangeValueUsdStart = rangeValueUsdStart;
	}

	public String getRangeValueUsdEnd() {
		return rangeValueUsdEnd;
	}

	public void setRangeValueUsdEnd(String rangeValueUsdEnd) {
		this.rangeValueUsdEnd = rangeValueUsdEnd;
	}

	public String getRangeUnitPriceUsdStart() {
		return rangeUnitPriceUsdStart;
	}

	public void setRangeUnitPriceUsdStart(String rangeUnitPriceUsdStart) {
		this.rangeUnitPriceUsdStart = rangeUnitPriceUsdStart;
	}

	public String getRangeUnitPriceUsdEnd() {
		return rangeUnitPriceUsdEnd;
	}

	public void setRangeUnitPriceUsdEnd(String rangeUnitPriceUsdEnd) {
		this.rangeUnitPriceUsdEnd = rangeUnitPriceUsdEnd;
	}

	public List<String> getIncoterm() {
		return incoterm;
	}

	public void setIncoterm(List<String> incoterm) {
		this.incoterm = incoterm;
	}

	public List<String> getNotifyParty() {
		return notifyParty;
	}

	public void setNotifyParty(List<String> notifyParty) {
		this.notifyParty = notifyParty;
	}

	public List<String> getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(List<String> productDesc) {
		this.productDesc = productDesc;
	}

	public String getConditionProductDesc() {
		return conditionProductDesc;
	}

	public void setConditionProductDesc(String conditionProductDesc) {
		this.conditionProductDesc = conditionProductDesc;
	}
	
	
	
	
}





