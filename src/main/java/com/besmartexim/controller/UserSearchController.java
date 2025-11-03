package com.besmartexim.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.besmartexim.dto.response.SearchDetailsResponse;
import com.besmartexim.dto.response.SuggestionListResponse;
import com.besmartexim.dto.response.UserSearchResponse;
import com.besmartexim.service.UserSearchService;

@CrossOrigin
@RestController
@RequestMapping(path="/search-management")
public class UserSearchController {

	private static final Logger logger = LoggerFactory.getLogger(UserSearchController.class);
	
	@Autowired
	private UserSearchService userSearchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> search(@RequestBody @Valid UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/search");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		if("incoterm".equalsIgnoreCase(userSearchRequest.getOrderByColumn()) && "IMPORT".equalsIgnoreCase(userSearchRequest.getTradeType().getValue())) {
			userSearchRequest.setOrderByColumn("incoterms");
		}
		UserSearchResponse userSearchResponse= userSearchService.search(userSearchRequest,accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(userSearchResponse, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updatesearchcount/{searchId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatesearchcount(@RequestBody @Valid SearchCountUpdateRequest searchCountUpdateRequest,@PathVariable Long searchId, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/updatesearchcount");
		userSearchService.updatesearchcount(searchCountUpdateRequest,searchId,accessedBy);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/downloadsearch", method = RequestMethod.PUT )
	public ResponseEntity<?> downloadsearch(@RequestParam(value = "searchId", required = true) Long searchId,@RequestParam (required=false) Long recordsDownloaded, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/downloadsearch");
		userSearchService.downloadsearch(searchId,accessedBy,recordsDownloaded);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/searchcount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchcount(@RequestBody @Valid UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/searchcount");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		Long total_shipment= userSearchService.searchcount(userSearchRequest,accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(total_shipment, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchcountbycolumn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchcountbycolumn(@RequestBody @Valid UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/searchcountbycolumn");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		Long total_count= userSearchService.searchcountbycolumn(userSearchRequest);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(total_count, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listimporters", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listimporters(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listimporters");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListImportersResponse listImportersResponse = userSearchService.listimporters(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listImportersResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listexporters", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listexporters(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listexporters");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListExportersResponse listExportersResponse= userSearchService.listexporters(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listExportersResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listforeigncountries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listforeigncountries(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listforeigncountries");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListCountriesResponse listCountriesResponse= userSearchService.listforeigncountries(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listCountriesResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listindiancities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listindiancities(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listindiancities");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListCitiesResponse listCitiesResponse= userSearchService.listindiancities(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listCitiesResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listindianports", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listindianports(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listindianports");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListPortsResponse listPortsResponse= userSearchService.listindianports(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listPortsResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listforeignports", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listforeignports(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listforeignports");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListPortsResponse listPortsResponse= userSearchService.listforeignports(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listPortsResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listhscodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listhscodes(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listhscodes");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListHscodesResponse listHscodesResponse= userSearchService.listhscodes(userSearchRequest, accessedBy);
		
		System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listHscodesResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listmonthwise", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listmonthwise(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listmonthwise");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListMonthwiseResponse listMonthwiseResponse= userSearchService.listmonthwise(userSearchRequest, accessedBy);
		
		System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listMonthwiseResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listhscodes4digit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listhscodes4digit(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listhscodes4digit");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListHscodesResponse listHscodesResponse= userSearchService.listhscodes4digit(userSearchRequest, accessedBy);
		
		System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listHscodesResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listshipmentmode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listshipmentmode(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listshipmentmode");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListShipmentModeResponse listShipmentModeResponse= userSearchService.listshipmentmode(userSearchRequest, accessedBy);
		
		System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listShipmentModeResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/liststdunit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> liststdunit(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/liststdunit");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListStdUnitResponse listStdUnitResponse= userSearchService.liststdunit(userSearchRequest, accessedBy);
		
		System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listStdUnitResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity SearchDetails(@RequestParam Long searchId, @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
		logger.info("accessedBy = "+accessedBy);
			
		SearchDetailsResponse searchDetailsResponse =  userSearchService.searchDetails(searchId);
		
		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/search/topFiveQueries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity topFiveQueries(@RequestParam Long usetId, @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
		logger.info("accessedBy = "+accessedBy);
			
		SearchDetailsResponse searchDetailsResponse = userSearchService.topFiveQueries(usetId);
		
		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/search/listAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity allQueries(@RequestParam (required=false) Long userId, @RequestParam (required=false) Long uplineId,@RequestParam (required=false) String isDownloaded, @RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size, @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
			
		SearchDetailsResponse searchDetailsResponse = userSearchService.listAllQueries(userId,uplineId,isDownloaded,accessedBy, PageRequest.of(page, size));
		
		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/search/countAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity countAllQueries(@RequestParam (required=false) Long userId, @RequestParam (required=false) Long uplineId,@RequestParam (required=false) String isDownloaded, @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
			
		long count = userSearchService.countAllQueries(userId,uplineId,isDownloaded,accessedBy);
		
		return new ResponseEntity<>(count, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/suggestionlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity listSuggestion(@RequestBody  SuggestionRequest suggestionRequest,  @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
		logger.info("accessedBy = "+accessedBy);	
		SuggestionListResponse suggestionListResponse = userSearchService.listSuggestion(suggestionRequest,accessedBy);
		
		return new ResponseEntity<>(suggestionListResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/countrywisecount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> countrywisecount(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listimporters");
		//mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		CountryWiseCountResponse countryWiseCountResponse = userSearchService.countrywisecount(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(countryWiseCountResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listdistinctcolumnvalue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listdistinctcolumnvalue(@RequestBody  UserSearchRequest userSearchRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /search-management/listdistinctcolumnvalue");
		
		if("incoterm".equalsIgnoreCase(userSearchRequest.getColumnName()) && "IMPORT".equalsIgnoreCase(userSearchRequest.getTradeType().getValue())) {
			userSearchRequest.setColumnName("incoterms");
		}
		
		ListDistinctColumnValuesResponse listDistinctColumnValuesResponse = userSearchService.listdistinctcolumnvalue(userSearchRequest, accessedBy);
		
		//System.out.println(userSearchRequest);
		
		return new ResponseEntity<>(listDistinctColumnValuesResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/search/listAllnew", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity allQueriesNew(@RequestParam (required=false) Long userId, @RequestParam (required=false) Long uplineId,@RequestParam (required=false) String isDownloaded,@RequestParam (required=false) String searchValue, @RequestParam (defaultValue = "0") int pageNumber, @RequestParam (defaultValue = "20") int pageSize, @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
			
		logger.info("Request : /search-management/listAllnew");
		SearchDetailsResponse searchDetailsResponse = userSearchService.listAllQueriesNew(userId,uplineId,isDownloaded,accessedBy,searchValue, PageRequest.of(pageNumber, pageSize));
		
		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/search/countAllnew", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity countAllQueriesNew(@RequestParam (required=false) Long userId, @RequestParam (required=false) Long uplineId,@RequestParam (required=false) String isDownloaded,@RequestParam (required=false) String searchValue, @RequestHeader(value="accessedBy", required=true) Long accessedBy) throws Exception{
			
		logger.info("Request : /search-management/countAllnew");
		long count = userSearchService.countAllQueriesNew(userId,uplineId,isDownloaded,searchValue,accessedBy);
		
		return new ResponseEntity<>(count, HttpStatus.OK);
		
	}
}
