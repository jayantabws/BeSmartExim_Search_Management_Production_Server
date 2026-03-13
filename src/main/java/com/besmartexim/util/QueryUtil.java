package com.besmartexim.util;


import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.besmartexim.dto.request.QueryBuilder;
import com.besmartexim.dto.request.UserSearchRequest;


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
	
	
	public String listToString(List<String> list) {
		String out="";
		if(list!=null && !list.isEmpty()) {
			for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				out=out+"'"+str+"',";
			}
			
			out=out.substring(0, out.length()-1);
		}
		
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public String objectToString(Object list) {
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
	
	
//	public static void main(String args[]) {
//		
//		String ss = "";
//		String ss1 = " ";
//		System.out.println(ss.trim().length());
//	}
}
