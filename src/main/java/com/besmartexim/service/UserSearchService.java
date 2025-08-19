package com.besmartexim.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor.STRING;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.besmartexim.database.entity.User;
import com.besmartexim.database.entity.UserSearch;
import com.besmartexim.database.repository.UserRepository;
import com.besmartexim.database.repository.UserSearchRepository;
import com.besmartexim.dto.request.QueryBuilder;
import com.besmartexim.dto.request.SearchCountUpdateRequest;
import com.besmartexim.dto.request.SuggestionRequest;
import com.besmartexim.dto.request.UserSearchRequest;
import com.besmartexim.dto.response.CountryWiseCountResponse;
import com.besmartexim.dto.response.ListCitiesResponse;
import com.besmartexim.dto.response.ListCountriesResponse;
import com.besmartexim.dto.response.ListDistinctColumnValuesResponse;
import com.besmartexim.dto.response.ListExportersResponse;
import com.besmartexim.dto.response.ListHscodesResponse;
import com.besmartexim.dto.response.ListImportersResponse;
import com.besmartexim.dto.response.ListMonthwiseResponse;
import com.besmartexim.dto.response.ListPortsResponse;
import com.besmartexim.dto.response.ListShipmentModeResponse;
import com.besmartexim.dto.response.ListStdUnitResponse;
import com.besmartexim.dto.response.SearchDetails;
import com.besmartexim.dto.response.SearchDetailsResponse;
import com.besmartexim.dto.response.SuggestionListResponse;
import com.besmartexim.dto.response.UserSearchResponse;
import com.besmartexim.util.QueryConstant;
import com.besmartexim.util.QueryUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class UserSearchService {

	@Autowired
	UserSearchRepository userSearchRepository;

	@Autowired
	QueryUtil queryUtil;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	UserSearchServiceHelper userSearchServiceHelper;

	ObjectMapper objectMapper = new ObjectMapper();

	int time_in_seconds = 300;
	
	private static final Logger logger = LoggerFactory.getLogger(UserSearchService.class);
	
	@Autowired
	private UserRepository  userRepository;

	public UserSearchResponse search(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		UserSearch userSearch = new UserSearch();

		if (userSearchRequest.getSearchId() == null || userSearchRequest.getSearchId().equals("") || userSearchRequest.getSearchId() == 0) {
			userSearch.setCreatedDate(new Date());
			userSearch.setCreatedBy(accessedBy);
			userSearch.setIsSaved("N");
			userSearch.setIsDownloaded("N");
			userSearch.setTotalRecords(Long.valueOf(0));
			userSearch.setRecordsDownloaded(Long.valueOf(0));
			userSearch.setSearchJson(objectMapper.writeValueAsString(userSearchRequest));
			String query = queryUtil.buildSearchQuery(userSearchRequest);
			userSearch.setSearchQuery(query);
			userSearchRepository.save(userSearch);
		} else {
			UserSearch existingUserSearch = userSearchRepository.findById(userSearchRequest.getSearchId()).get();
			existingUserSearch.setId(userSearchRequest.getSearchId());
			existingUserSearch.setModifiedDate(new Date());
			existingUserSearch.setModifiedBy(accessedBy);
			// existingUserSearch.setIsSaved("N");
			// existingUserSearch.setIsDownloaded("N");
			existingUserSearch.setSearchJson(objectMapper.writeValueAsString(userSearchRequest));
			String query = queryUtil.buildSearchQuery(userSearchRequest);
			existingUserSearch.setSearchQuery(query);
			userSearchRepository.save(existingUserSearch);

			userSearch.setId(userSearchRequest.getSearchId());
		}

		Connection connection = null;
		ResultSet rs = null;
		UserSearchResponse userSearchResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;
			//int isAdvance = 1;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.searchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.searchProcedureSEZ;
			else
				//proeName = QueryConstant.searchProcedurePrefix + queryUtil.objectToString(userSearchRequest.getCountryCode()).toUpperCase()
						//+ QueryConstant.searchProcedureSuffix;
			
			proeName = QueryConstant.searchProcedureTmpForeignTesting;

			
			
//			if((userSearchRequest.getHsCodeList()== null || userSearchRequest.getHsCodeList().isEmpty()) && 
//					(userSearchRequest.getHsCode4DigitList()== null || userSearchRequest.getHsCode4DigitList().isEmpty()) && 
//					(userSearchRequest.getExporterList()== null || userSearchRequest.getExporterList().isEmpty())&& 
//					(userSearchRequest.getImporterList()== null || userSearchRequest.getImporterList().isEmpty()) && 
//					(userSearchRequest.getCityOriginList()== null || userSearchRequest.getCityOriginList().isEmpty()) && 
//					(userSearchRequest.getCityDestinationList()== null || userSearchRequest.getCityDestinationList().isEmpty()) &&
//					(userSearchRequest.getPortOriginList()== null || userSearchRequest.getPortOriginList().isEmpty()) && 
//					(userSearchRequest.getPortDestinationList()== null || userSearchRequest.getPortDestinationList().isEmpty()) &&
//					(userSearchRequest.getShipModeList()== null || userSearchRequest.getShipModeList().isEmpty()) && 
//					(userSearchRequest.getStdUnitList()== null || userSearchRequest.getStdUnitList().isEmpty()))
//			{
//				isAdvance = 0;
//			}
			
			
			if(userSearchRequest.getPageNumber() == 0 && userSearchRequest.getNumberOfRecords() < 10000 
					&& (userSearchRequest.getSearchId() == null || userSearchRequest.getSearchId().equals("") || userSearchRequest.getSearchId() == 0) && !userSearchRequest.getSearchType().equals("ADVANCE")) {
				CallableStatement callableStatement = connection.prepareCall(proeName);
				callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
				callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
				callableStatement.setString(3, userSearchRequest.getFromDate());
				callableStatement.setString(4, userSearchRequest.getToDate());
				callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
				callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
				callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
				callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
				callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
				callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
				callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
				callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
				callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
				callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
				callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
				callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
				callableStatement.setString(17, userSearchRequest.getOrderByColumn());
				callableStatement.setString(18, userSearchRequest.getOrderByMode());
				callableStatement.setInt(19, userSearchRequest.getPageNumber());
				callableStatement.setInt(20, userSearchRequest.getNumberOfRecords());
				
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
				callableStatement.setString(21, qbList.get(0).getRelation());
				callableStatement.setString(22, qbList.get(0).getMatchType());
				callableStatement.setString(23, qbList.get(0).getSearchBy());
				callableStatement.setString(24, queryUtil.listToString(qbList.get(0).getSearchValue()));

				callableStatement.setString(25, qbList.get(1).getRelation());
				callableStatement.setString(26, qbList.get(1).getMatchType());
				callableStatement.setString(27, qbList.get(1).getSearchBy());
				callableStatement.setString(28, queryUtil.listToString(qbList.get(1).getSearchValue()));
				
				callableStatement.setString(29, qbList.get(2).getRelation());
				callableStatement.setString(30, qbList.get(2).getMatchType());
				callableStatement.setString(31, qbList.get(2).getSearchBy());
				callableStatement.setString(32, queryUtil.listToString(qbList.get(2).getSearchValue()));
				
				callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getShipModeList()));
				callableStatement.setString(34, queryUtil.listToString(userSearchRequest.getStdUnitList()));
								
				callableStatement.setString(35, userSearchRequest.getRangeQuantityStart());
				callableStatement.setString(36, userSearchRequest.getRangeQuantityEnd());
				callableStatement.setString(37, queryUtil.listToString(userSearchRequest.getConsumptionType()));
				callableStatement.setString(38, userSearchRequest.getRangeValueUsdStart());
				callableStatement.setString(39, userSearchRequest.getRangeValueUsdEnd());
				callableStatement.setString(40, userSearchRequest.getRangeUnitPriceUsdStart());
				callableStatement.setString(41, userSearchRequest.getRangeUnitPriceUsdEnd());
				callableStatement.setString(42, queryUtil.listToString(userSearchRequest.getIncoterm()));
				callableStatement.setString(43, queryUtil.listToString(userSearchRequest.getNotifyParty()));
				callableStatement.setString(44, queryUtil.listToString(userSearchRequest.getProductDesc()));
				callableStatement.setString(45, userSearchRequest.getConditionProductDesc());	
				
				
				callableStatement.setString(46, accessedBy.toString());
				
				callableStatement.setQueryTimeout(time_in_seconds);
				
				boolean b = callableStatement.execute();
			}
			CallableStatement callableStatement;
			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND") || queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
			{			
				callableStatement = connection.prepareCall(QueryConstant.searchProcedureTmp);
			}
			else
			{
				callableStatement = connection.prepareCall(QueryConstant.searchProcedureTmpForeign);
				//callableStatement = connection.prepareCall(QueryConstant.searchProcedureTmpForeignTesting);
			}
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			callableStatement.setString(17, userSearchRequest.getOrderByColumn());
			callableStatement.setString(18, userSearchRequest.getOrderByMode());
			callableStatement.setInt(19, userSearchRequest.getPageNumber());
			callableStatement.setInt(20, userSearchRequest.getNumberOfRecords());
			
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
			callableStatement.setString(21, qbList.get(0).getRelation());
			callableStatement.setString(22, qbList.get(0).getMatchType());
			callableStatement.setString(23, qbList.get(0).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(25, qbList.get(1).getRelation());
			callableStatement.setString(26, qbList.get(1).getMatchType());
			callableStatement.setString(27, qbList.get(1).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(29, qbList.get(2).getRelation());
			callableStatement.setString(30, qbList.get(2).getMatchType());
			callableStatement.setString(31, qbList.get(2).getSearchBy());
			callableStatement.setString(32, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(34, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(35, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(36, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(37, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(38, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(39, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(40, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(41, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(42, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(43, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(44, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(45, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(46, accessedBy.toString());
			
			callableStatement.setQueryTimeout(time_in_seconds);
			//callableStatement.sett
			boolean b = callableStatement.execute();

			rs = callableStatement.getResultSet();

			userSearchResponse = userSearchServiceHelper.creteResponse(rs, userSearchRequest);

			userSearchResponse.setSearchId(userSearch.getId());

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}

		return userSearchResponse;
	}

	public Long searchcount(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Long count = 0l;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.searchCountProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.searchCountProcedureSEZ;
			else
				proeName = QueryConstant.searchCountProcedurePrefix + queryUtil.objectToString(userSearchRequest.getCountryCode()).toUpperCase()
						+ QueryConstant.searchCountProcedureSuffix;
			
			proeName = QueryConstant.searchCountProcedureAllCountries;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			while (rs.next()) {
				count = rs.getLong("total_shipment");
			}

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return count;
	}

	public Long searchcountbycolumn(UserSearchRequest userSearchRequest) throws Exception {
		Long count = 0l;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement callableStatement = connection
					.prepareCall(QueryConstant.searchCountByColumnNameProcedure);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			callableStatement.setString(17, userSearchRequest.getColumnName());

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			while (rs.next()) {
				count = rs.getLong("total_count");
			}

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return count;
	}

	public ListImportersResponse listimporters(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListImportersResponse listImportersResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listImportersBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listImportersBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listImportersBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());
			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listImportersResponse = userSearchServiceHelper.creteListImporters(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listImportersResponse;
	}

	public ListExportersResponse listexporters(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListExportersResponse listExportersResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listExportersBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listExportersBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listExportersBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());
			
			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listExportersResponse = userSearchServiceHelper.creteListExporters(rs);
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}

		return listExportersResponse;
	}

	public ListCountriesResponse listforeigncountries(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListCountriesResponse listCountriesResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listForeignCountriesBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listForeignCountriesBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listForeignCountriesBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());
			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listCountriesResponse = userSearchServiceHelper.creteListCountries(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}

		return listCountriesResponse;
	}

	public ListCitiesResponse listindiancities(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListCitiesResponse listCitiesResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listIndianCitiesBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listIndianCitiesBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listIndianCitiesBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listCitiesResponse = userSearchServiceHelper.creteListCities(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}

		return listCitiesResponse;
	}

	public ListPortsResponse listindianports(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListPortsResponse listPortsResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listIndianPortsBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listIndianPortsBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listIndianPortsBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());
			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listPortsResponse = userSearchServiceHelper.creteListPorts(rs);
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}

		return listPortsResponse;
	}

	public ListPortsResponse listforeignports(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListPortsResponse listPortsResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listForeignPortsBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listForeignPortsBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listForeignPortsBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listPortsResponse = userSearchServiceHelper.creteListPorts(rs);
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}

		return listPortsResponse;
	}

	public ListHscodesResponse listhscodes(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListHscodesResponse listHscodesResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listHscodesBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listHscodesBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listHscodesBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listHscodesResponse = userSearchServiceHelper.creteListHscodes(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listHscodesResponse;

	}
	
	public ListMonthwiseResponse listmonthwise(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListMonthwiseResponse listMonthwiseResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

//			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
//				proeName = QueryConstant.listMonthwiseBySearchProcedure;
//			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
//				proeName = QueryConstant.listMonthwiseBySearchProcedureSEZ;
//			else
//				proeName = QueryConstant.listMonthwiseBySearchProcedure;
			
			proeName = QueryConstant.listMonthwiseBySearchProcedureAllCountries;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());	

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listMonthwiseResponse = userSearchServiceHelper.creteListMonthwise(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listMonthwiseResponse;

	}

	public ListHscodesResponse listhscodes4digit(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListHscodesResponse listHscodesResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listHscodes4DigitBySearchProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listHscodes4DigitBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listHscodes4DigitBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listHscodesResponse = userSearchServiceHelper.creteListHscodes(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listHscodesResponse;

	}

	public ListShipmentModeResponse listshipmentmode(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListShipmentModeResponse listShipmentModeResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			
			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.listShipmentModeBySearchProcedure;
			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listShipmentModeBySearchProcedureSEZ;
			
			
			CallableStatement callableStatement = connection
					.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, accessedBy.toString());
			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listShipmentModeResponse = userSearchServiceHelper.creteListShipmentMode(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listShipmentModeResponse;

	}
	
	
	public ListStdUnitResponse liststdunit(UserSearchRequest userSearchRequest, Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListStdUnitResponse listStdUnitResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			
			String proeName = null;

			
				proeName = QueryConstant.listStdUnitBySearchProcedure;
			
			
			CallableStatement callableStatement = connection
					.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listStdUnitResponse = userSearchServiceHelper.creteListStdUnit(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listStdUnitResponse;

	}

	public SearchDetailsResponse searchDetails(Long searchId) throws Exception {

		SearchDetailsResponse searchDetailsResponse = new SearchDetailsResponse();
		List<SearchDetails> list = new ArrayList<SearchDetails>();
		SearchDetails searchDetails = new SearchDetails();

		Optional<UserSearch> ulist = userSearchRepository.findById(searchId);

		if (ulist.isPresent()) {
			UserSearch userSearch = ulist.get();

			searchDetails.setSearchId(searchId);
			searchDetails
			.setUserSearchQuery(objectMapper.readValue(userSearch.getSearchJson(), UserSearchRequest.class));
			
//			JsonNode jsonNode = new ObjectMapper().readTree(userSearch.getSearchJson());
//			String country_code = jsonNode.get("countryCode").toString();
//			if(country_code.contains(",")) {
//				searchDetails
//				.setUserSearchQuery(objectMapper.readValue(userSearch.getSearchJson(), UserSearchRequest.class));
//			} else {
//				UserSearchRequest request = new UserSearchRequest();
//				ArrayList<String> con_list = new ArrayList<String>();
//				con_list.add(country_code.replaceAll("\"", ""));
//				request.setCountryCode(con_list);
//				
//				request.setColumnName(jsonNode.get("columnName").asText());
//				request.setSearchId(jsonNode.get("searchId").asLong());
//				searchDetails
//				.setUserSearchQuery(request);
//			}
			searchDetails.setTotalRecords(userSearch.getTotalRecords());
			searchDetails.setIsDownloaded(userSearch.getIsDownloaded());
			searchDetails.setDownloadedDate(userSearch.getDownloadedDate());
			searchDetails.setCreatedDate(userSearch.getCreatedDate());
			searchDetails.setCreatedBy(userSearch.getCreatedBy());
			searchDetails.setDownloadedBy(userSearch.getDownloadedBy());
			searchDetails.setRecordsDownloaded(userSearch.getRecordsDownloaded());
		}
		list.add(searchDetails);
		
		searchDetailsResponse.setQueryList(list);
		searchDetailsResponse = convertCountryToList(searchDetailsResponse);
		
		return searchDetailsResponse;

	}

	public SearchDetailsResponse topFiveQueries(Long usetId) throws Exception {
		SearchDetailsResponse searchDetailsResponse = new SearchDetailsResponse();
		List<SearchDetails> list = new ArrayList<SearchDetails>();
		SearchDetails searchDetails = null;

		List<UserSearch> userSearchList = userSearchRepository.findTop5ByCreatedByOrderByCreatedDateDesc(usetId);

		for (Iterator iterator = userSearchList.iterator(); iterator.hasNext();) {
			UserSearch userSearch = (UserSearch) iterator.next();
			searchDetails = new SearchDetails();
			searchDetails.setSearchId(userSearch.getId());
			searchDetails.setCreatedDate(userSearch.getCreatedDate());
			searchDetails.setTotalRecords(userSearch.getTotalRecords());
			searchDetails.setCreatedBy(userSearch.getCreatedBy());
			searchDetails
					.setUserSearchQuery(objectMapper.readValue(userSearch.getSearchJson(), UserSearchRequest.class));
//			JsonNode jsonNode = new ObjectMapper().readTree(userSearch.getSearchJson());
//			String country_code = jsonNode.get("countryCode").toString();
//			if(country_code.contains(",")) {
//				searchDetails
//				.setUserSearchQuery(objectMapper.readValue(userSearch.getSearchJson(), UserSearchRequest.class));
//			} else {
//				UserSearchRequest request = new UserSearchRequest();
//				ArrayList<String> con_list = new ArrayList<String>();
//				con_list.add(country_code.replaceAll("\"", ""));
//				request.setCountryCode(con_list);
//				
//				request.setColumnName(jsonNode.get("columnName").asText());
//				request.setSearchId(jsonNode.get("searchId").asLong());
//				searchDetails
//				.setUserSearchQuery(request);
//			}
			
			searchDetails.setIsDownloaded(userSearch.getIsDownloaded());
			searchDetails.setDownloadedDate(userSearch.getDownloadedDate());
			searchDetails.setCreatedDate(userSearch.getCreatedDate());
			searchDetails.setCreatedBy(userSearch.getCreatedBy());
			searchDetails.setDownloadedBy(userSearch.getDownloadedBy());
			searchDetails.setRecordsDownloaded(userSearch.getRecordsDownloaded());
			list.add(searchDetails);
		}

		searchDetailsResponse.setQueryList(list);
		searchDetailsResponse = convertCountryToList(searchDetailsResponse);

		return searchDetailsResponse;
	}

	@Value("${usermanagement.service.url}")
	private String usermanagementUrl;

	@Autowired
	private RestTemplate restTemplate;

	public SearchDetailsResponse listAllQueries(Long userId, Long uplineId, String isDownloaded, Long accessedBy, Pageable pageable)
			throws Exception {

		SearchDetailsResponse searchDetailsResponse = new SearchDetailsResponse();
		List<SearchDetails> list = new ArrayList<SearchDetails>();
		SearchDetails searchDetails = null;
		List<UserSearch> userSearchList = null;
	
		pageable = PageRequest.of(pageable.getPageNumber()*10, pageable.getPageSize(), Sort.by("createdDate").descending());

		if (userId != null) {
			if (isDownloaded != null && isDownloaded != "")
				userSearchList = userSearchRepository.findByCreatedByAndIsDownloaded(userId,
						isDownloaded, pageable).getContent();
			else
				userSearchList = userSearchRepository.findByCreatedBy(userId, pageable).getContent();
		} else if (uplineId != null) {
			if (isDownloaded != null && isDownloaded != "")
				userSearchList = userSearchRepository.findByUplineIdAndIsDownloadedOrderByCreatedDateDesc(uplineId,
						isDownloaded, pageable.getPageNumber(), pageable.getPageSize());
			else
				userSearchList = userSearchRepository.findByUplineIdOrderByCreatedDateDesc(uplineId, pageable.getPageNumber(), pageable.getPageSize());
		} else {
			if (isDownloaded != null && isDownloaded != "")
				userSearchList = userSearchRepository.findByIsDownloaded(isDownloaded, pageable).getContent();
			else
				userSearchList = userSearchRepository.findAll(pageable).getContent();
		}

		// Fetch user management Data From User Data
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("accessedBy", "" + accessedBy);
		headers.add("Authorization", "Basic YXBpLWV4aW13YXRjaDp1ZTg0Q1JSZnRAWGhBMyRG");

		for (Iterator iterator = userSearchList.iterator(); iterator.hasNext();) {
			UserSearch userSearch = (UserSearch) iterator.next();
			searchDetails = new SearchDetails();
			searchDetails.setSearchId(userSearch.getId());
			searchDetails.setCreatedDate(userSearch.getCreatedDate());
			searchDetails.setTotalRecords(userSearch.getTotalRecords());
			searchDetails.setCreatedBy(userSearch.getCreatedBy());
			searchDetails
					.setUserSearchQuery(objectMapper.readValue(userSearch.getSearchJson(), UserSearchRequest.class));
//			JsonNode jsonNode = new ObjectMapper().readTree(userSearch.getSearchJson());
//			String country_code = jsonNode.get("countryCode").toString();
//			if(country_code.contains(",")) {
//				searchDetails
//				.setUserSearchQuery(objectMapper.readValue(userSearch.getSearchJson(), UserSearchRequest.class));
//			} else {
//				UserSearchRequest request = new UserSearchRequest();
//				ArrayList<String> con_list = new ArrayList<String>();
//				con_list.add(country_code.replaceAll("\"", ""));
//				request.setCountryCode(con_list);
//				
//				request.setColumnName(jsonNode.get("columnName").asText());
//				request.setSearchId(jsonNode.get("searchId").asLong());
//				searchDetails
//				.setUserSearchQuery(request);
//			}
			
			
			
			User userEntity = userRepository.findById(userSearch.getCreatedBy()).orElse(null);
			
			/*
			String absUrl = usermanagementUrl + userSearch.getCreatedBy();
			System.out.println("absUrl = "+ absUrl);
			
			ResponseEntity<UserDetailsResponse> responseEntity = restTemplate.exchange(absUrl, HttpMethod.GET,
					new HttpEntity<Object>(headers), UserDetailsResponse.class);
			UserDetailsResponse userDetailsResponse = responseEntity.getBody();*/
			if(userEntity != null )
			{
				searchDetails.setCreatedByName(userEntity.getFirstname() + " " + userEntity.getLastname());
				searchDetails.setCreatedByEmail(userEntity.getEmail());
			}

			searchDetails.setIsDownloaded(userSearch.getIsDownloaded());
			searchDetails.setDownloadedDate(userSearch.getDownloadedDate());
			searchDetails.setDownloadedBy(userSearch.getDownloadedBy());

			if (userSearch.getDownloadedBy() != null) {
				/*absUrl = usermanagementUrl + userSearch.getDownloadedBy();
				responseEntity = restTemplate.exchange(absUrl, HttpMethod.GET, new HttpEntity<Object>(headers),
						UserDetailsResponse.class);
				userDetailsResponse = responseEntity.getBody();*/
				
				userEntity = userRepository.findById(userSearch.getDownloadedBy()).orElse(null);
				
				if(userEntity != null )
				{
					searchDetails.setDownloadedByName(
							userEntity.getFirstname() + " " + userEntity.getLastname());
					searchDetails.setDownloadedByEmail(userEntity.getEmail());
				}
				
			}
			
			searchDetails.setRecordsDownloaded(userSearch.getRecordsDownloaded());

			list.add(searchDetails);
		}

		searchDetailsResponse.setQueryList(list);
		searchDetailsResponse = convertCountryToList(searchDetailsResponse);
		
		return searchDetailsResponse;
	}
	
	public long countAllQueries(Long userId, Long uplineId, String isDownloaded, Long accessedBy)
			throws Exception {
		
		long count = 0l;

		if (userId != null) {
			if (isDownloaded != null && isDownloaded != "")
				count = userSearchRepository.countByCreatedByAndIsDownloaded(userId, isDownloaded);
			else
				count = userSearchRepository.countByCreatedBy(userId);
		} else if (uplineId != null) {
			if (isDownloaded != null && isDownloaded != "")
				count = userSearchRepository.countByUplineIdAndIsDownloaded(uplineId, isDownloaded);
			else
				count = userSearchRepository.countByUplineId(uplineId);
		} else {
			if (isDownloaded != null && isDownloaded != "")
				count = userSearchRepository.countByIsDownloaded(isDownloaded);
			else
				count = userSearchRepository.count();
		}
		
		return count;
	}

	public void updatesearchcount(@Valid SearchCountUpdateRequest searchCountUpdateRequest, Long searchId,
			Long accessedBy) {

		UserSearch existingUserSearch = userSearchRepository.findById(searchId).get();
		existingUserSearch.setTotalRecords(searchCountUpdateRequest.getTotalRecords());
		existingUserSearch.setModifiedDate(new Date());
		existingUserSearch.setModifiedBy(accessedBy);
		userSearchRepository.save(existingUserSearch);

	}

	public SuggestionListResponse listSuggestion(SuggestionRequest suggestionRequest, Long accessedBy)
			throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		SuggestionListResponse suggestionListResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (suggestionRequest.getCountryCode().get(0).equalsIgnoreCase("IND")) 
				proeName = QueryConstant.listSuggestionBySearchProcedure;
			else if (suggestionRequest.getCountryCode().get(0).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.listSuggestionBySearchProcedureSEZ;
			else
				proeName = QueryConstant.listSuggestionBySearchForeignProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, suggestionRequest.getTradeType().getValue());
			callableStatement.setString(2, "");
			//callableStatement.setString(2, suggestionRequest.getCountryCode());
			callableStatement.setString(3, suggestionRequest.getFromDate());
			callableStatement.setString(4, suggestionRequest.getToDate());
			callableStatement.setString(5, suggestionRequest.getSearchBy().getValue());
			callableStatement.setString(6, suggestionRequest.getSearchValue());
			callableStatement.setString(7, suggestionRequest.getMatchType());

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			suggestionListResponse = userSearchServiceHelper.creteListSuggestion(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return suggestionListResponse;
	}

	public void downloadsearch(Long searchId, Long accessedBy, Long recordsDownloaded) {
		// TODO Auto-generated method stub
		UserSearch existingUserSearch = userSearchRepository.findById(searchId).get();
		existingUserSearch.setIsDownloaded("Y");
		existingUserSearch.setDownloadedBy(accessedBy);
		existingUserSearch.setDownloadedDate(new Date());
		existingUserSearch.setRecordsDownloaded(recordsDownloaded);
		userSearchRepository.save(existingUserSearch);

	}

	public CountryWiseCountResponse countrywisecount(UserSearchRequest userSearchRequest, Long accessedBy)throws Exception {
		
		Connection connection = null;
		ResultSet rs = null;
		CountryWiseCountResponse countryWiseCountResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("IND"))
				proeName = QueryConstant.countSearchRecordsCountryWiseProcedure;
			else if (queryUtil.objectToString(userSearchRequest.getCountryCode()).equalsIgnoreCase("SEZ"))
				proeName = QueryConstant.countSearchRecordsCountryWiseProcedure;
			else
				proeName = QueryConstant.countSearchRecordsCountryWiseProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(32, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(33, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(34, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(36, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(38, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(41, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(42, accessedBy.toString());
			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			countryWiseCountResponse = userSearchServiceHelper.creteCountryWiseCount(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return countryWiseCountResponse;
	}

	public ListDistinctColumnValuesResponse listdistinctcolumnvalue(UserSearchRequest userSearchRequest,
			Long accessedBy) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		ListDistinctColumnValuesResponse listDistinctColumnValuesResponse = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			String proeName = null;

			
			proeName = QueryConstant.listDistinctValueByColumnNameProcedure;

			CallableStatement callableStatement = connection.prepareCall(proeName);
			callableStatement.setString(1, userSearchRequest.getSearchType().getValue());
			callableStatement.setString(2, userSearchRequest.getTradeType().getValue());
			callableStatement.setString(3, userSearchRequest.getFromDate());
			callableStatement.setString(4, userSearchRequest.getToDate());
			callableStatement.setString(5, userSearchRequest.getSearchBy().getValue());
			callableStatement.setString(6, userSearchRequest.getMatchType().getValue());
			callableStatement.setString(7, queryUtil.listToString(userSearchRequest.getSearchValue()));
			callableStatement.setString(8, queryUtil.objectToString(userSearchRequest.getCountryCode()));
			callableStatement.setString(9, queryUtil.listToString(userSearchRequest.getHsCodeList()));
			callableStatement.setString(10, queryUtil.listToString(userSearchRequest.getHsCode4DigitList()));
			callableStatement.setString(11, queryUtil.listToString(userSearchRequest.getExporterList()));
			callableStatement.setString(12, queryUtil.listToString(userSearchRequest.getImporterList()));
			callableStatement.setString(13, queryUtil.listToString(userSearchRequest.getCityOriginList()));
			callableStatement.setString(14, queryUtil.listToString(userSearchRequest.getCityDestinationList()));
			callableStatement.setString(15, queryUtil.listToString(userSearchRequest.getPortOriginList()));
			callableStatement.setString(16, queryUtil.listToString(userSearchRequest.getPortDestinationList()));
			
			
			
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
			callableStatement.setString(17, qbList.get(0).getRelation());
			callableStatement.setString(18, qbList.get(0).getMatchType());
			callableStatement.setString(19, qbList.get(0).getSearchBy());
			callableStatement.setString(20, queryUtil.listToString(qbList.get(0).getSearchValue()));

			callableStatement.setString(21, qbList.get(1).getRelation());
			callableStatement.setString(22, qbList.get(1).getMatchType());
			callableStatement.setString(23, qbList.get(1).getSearchBy());
			callableStatement.setString(24, queryUtil.listToString(qbList.get(1).getSearchValue()));
			
			callableStatement.setString(25, qbList.get(2).getRelation());
			callableStatement.setString(26, qbList.get(2).getMatchType());
			callableStatement.setString(27, qbList.get(2).getSearchBy());
			callableStatement.setString(28, queryUtil.listToString(qbList.get(2).getSearchValue()));
			
			callableStatement.setString(29, queryUtil.listToString(userSearchRequest.getShipModeList()));
			callableStatement.setString(30, queryUtil.listToString(userSearchRequest.getStdUnitList()));
			
			callableStatement.setString(31, userSearchRequest.getColumnName());
			
			callableStatement.setString(32, userSearchRequest.getRangeQuantityStart());
			callableStatement.setString(33, userSearchRequest.getRangeQuantityEnd());
			callableStatement.setString(34, queryUtil.listToString(userSearchRequest.getConsumptionType()));
			callableStatement.setString(35, userSearchRequest.getRangeValueUsdStart());
			callableStatement.setString(36, userSearchRequest.getRangeValueUsdEnd());
			callableStatement.setString(37, userSearchRequest.getRangeUnitPriceUsdStart());
			callableStatement.setString(38, userSearchRequest.getRangeUnitPriceUsdEnd());
			callableStatement.setString(39, queryUtil.listToString(userSearchRequest.getIncoterm()));
			callableStatement.setString(40, queryUtil.listToString(userSearchRequest.getNotifyParty()));
			callableStatement.setString(41, queryUtil.listToString(userSearchRequest.getProductDesc()));
			callableStatement.setString(42, userSearchRequest.getConditionProductDesc());
			
			callableStatement.setString(43, accessedBy.toString());
			

			callableStatement.execute();

			rs = callableStatement.getResultSet();

			listDistinctColumnValuesResponse = userSearchServiceHelper.creteListDistinctColumnValues(rs);

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (rs != null)
				rs.close();
			if (connection != null)
				connection.close();
		}
		return listDistinctColumnValuesResponse;
	}
	
	
	private SearchDetailsResponse convertCountryToList(SearchDetailsResponse searchDetailsResponse) {
		
					// Converting Country from String to List<String>
		Object countryCode = searchDetailsResponse.getQueryList().get(0).getUserSearchQuery().getCountryCode();
				
		if(countryCode instanceof String){
			ArrayList<String> list = new ArrayList<String>();
			list.add((String) countryCode);
			searchDetailsResponse.getQueryList().get(0).getUserSearchQuery().setCountryCode(list);
		} 
		
		return searchDetailsResponse;
	}
	


}
