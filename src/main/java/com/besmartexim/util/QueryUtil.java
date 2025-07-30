package com.besmartexim.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.besmartexim.dto.request.QueryBuilder;
import com.besmartexim.dto.request.UserSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueryUtil {
	
	@Autowired
	QueryUtil queryUtil;

	public String buildSearchQuery(UserSearchRequest userSearchRequest)throws Exception {
		
		/*
		Date frmDate = new SimpleDateFormat("yyyy-MM-dd").parse(userSearchRequest.getFromDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(frmDate);
		
		String query= "Select * from"+
					AppConstant.blank+userSearchRequest.getTradeType().getValue()+"_"+userSearchRequest.getCountryCode()+"_"+calendar.get(Calendar.YEAR)+
					AppConstant.blank+"where "+userSearchRequest.getSearchBy().getValue()+" like '"+userSearchRequest.getSearchValue()+"%'"+
					AppConstant.blank+" and sb_date between '"+userSearchRequest.getFromDate()+"' and '"+userSearchRequest.getToDate()+"'";
		*/
		String str = null;
		if(queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
			str = QueryConstant.searchProcedure;
		else if(queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
			str = QueryConstant.searchProcedureSEZ;
		else
			str = QueryConstant.searchProcedurePrefix+queryUtil.objectToString(userSearchRequest.getCountryCode()).toUpperCase()+QueryConstant.searchProcedureSuffix;
		
		str = QueryConstant.searchProcedureTmpForeignTesting;
		
		int MAX_SIZE = 3;
		int incSize = userSearchRequest.getQueryBuilder().size();
		List<QueryBuilder> qbList = userSearchRequest.getQueryBuilder();
		
		for (int i = incSize; i < MAX_SIZE; i++) {
			QueryBuilder qb = new QueryBuilder();
			qb.setMatchType(null);
			qb.setRelation(null);
			qb.setSearchBy(null);
			qb.setSearchValue(null);
			if(qbList!=null) {
				qbList.add(qb);
			}
		}
		
		
		String searchParams="'"+userSearchRequest.getSearchType().getValue()+"','"+userSearchRequest.getTradeType().getValue()+
				"','"+userSearchRequest.getFromDate()+"','"+userSearchRequest.getToDate()+"','"+userSearchRequest.getSearchBy().getValue()+"','"+userSearchRequest.getMatchType().getValue()+
				"','"+listToString(userSearchRequest.getSearchValue())+"','"+userSearchRequest.getCountryCode()+"','"+listToString(userSearchRequest.getHsCodeList())+"','"+listToString(userSearchRequest.getHsCode4DigitList())+"','"+listToString(userSearchRequest.getExporterList())+
				"','"+listToString(userSearchRequest.getImporterList())+"','"+listToString(userSearchRequest.getCityOriginList())+"','"+listToString(userSearchRequest.getCityDestinationList())+
				"','"+listToString(userSearchRequest.getPortOriginList())+"','"+listToString(userSearchRequest.getPortDestinationList())+"','"+userSearchRequest.getOrderByColumn()+
				"','"+userSearchRequest.getOrderByMode()+"',"+userSearchRequest.getPageNumber()+","+userSearchRequest.getNumberOfRecords()+",'"+qbList.get(0).getRelation()+
				"','"+qbList.get(0).getMatchType()+"','"+qbList.get(0).getSearchBy()+"','"+
				listToString(qbList.get(0).getSearchValue())+"','"+qbList.get(1).getRelation()+
				"','"+qbList.get(1).getMatchType()+"','"+qbList.get(1).getSearchBy()+"','"+
				listToString(qbList.get(1).getSearchValue())+"','"+qbList.get(2).getRelation()+
				"','"+qbList.get(2).getMatchType()+"','"+qbList.get(2).getSearchBy()+"','"+
				listToString(qbList.get(2).getSearchValue())+"',"+
				listToString(userSearchRequest.getShipModeList())+"',"+
				listToString(userSearchRequest.getStdUnitList())+"',"+
				
				userSearchRequest.getRangeQuantityStart()+","+
				userSearchRequest.getRangeQuantityEnd()+",'"+
				listToString(userSearchRequest.getConsumptionType())+"',"+
				userSearchRequest.getRangeValueUsdStart()+","+
				userSearchRequest.getRangeValueUsdEnd()+","+
				userSearchRequest.getRangeUnitPriceUsdStart()+","+
				userSearchRequest.getRangeUnitPriceUsdEnd()+",'"+
				listToString(userSearchRequest.getIncoterm())+"','"+
				listToString(userSearchRequest.getNotifyParty())+"','"+
				listToString(userSearchRequest.getProductDesc())+"','"+
				userSearchRequest.getConditionProductDesc()+"'";
		
		str =  str.replace("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", searchParams);
		
		return str;
	}
	
	
	public static String listToString(List<String> list) {
		String out="";
		if(list!=null && !list.isEmpty()) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				out=out+"'"+str+"',";
			}
			
			out=out.substring(0, out.length()-1);
		}
		
		return out;
	}
	
	public static String objectToString(Object list) {
		String out="";
		if(list!=null && list instanceof List<?>) {
			
			List<String> countryList = (List<String>) list;
			for(String country : countryList) {
				out=out+"'"+country+"',";
			}
			out=out.substring(0, out.length()-1);	
		} else {
			if(list instanceof String)
				out=(String)list;
		}
		
		return out;
	}
	
	
	public static void main(String args[]) throws JsonMappingException, JsonProcessingException {
		
		Double ss =1.0E-5;
		System.out.println(new BigDecimal(ss));
		
		List<String> list = new ArrayList<String>();
		list.add("Jayanta");
		list.add("Palash");
		System.out.println(listToString(list));
		String str ="{\"searchType\":\"TRADE\",\"tradeType\":\"EXPORT\",\"fromDate\":\"2024-01-01\",\"toDate\":\"2024-01-31\",\"searchBy\":\"HS_CODE\",\"searchValue\":[\"2915\"],\"matchType\":\"L\",\"countryCode\":[\"ARG\",\"BAN\"],\"searchId\":null,\"hsCodeList\":null,\"hsCode4DigitList\":null,\"exporterList\":null,\"importerList\":null,\"cityOriginList\":null,\"cityDestinationList\":null,\"portOriginList\":null,\"portDestinationList\":null,\"columnName\":null,\"orderByColumn\":\"\",\"orderByMode\":\"desc\",\"pageNumber\":0,\"numberOfRecords\":20,\"queryBuilder\":[],\"shipModeList\":null,\"stdUnitList\":null,\"rangeQuantityStart\":null,\"rangeQuantityEnd\":null,\"consumptionType\":null,\"rangeValueUsdStart\":null,\"rangeValueUsdEnd\":null,\"rangeUnitPriceUsdStart\":null,\"rangeUnitPriceUsdEnd\":null,\"incoterm\":null,\"notifyParty\":null,\"productDesc\":null,\"conditionProductDesc\":null}";	
				JsonNode jsonNode = new ObjectMapper().readTree(str);
		System.out.println(jsonNode.get("countryCode"));
		String country = jsonNode.get("countryCode").toString();
		System.out.println("String =>"+country);
		
		System.out.println(LocalDateTime.now());
	}
}
