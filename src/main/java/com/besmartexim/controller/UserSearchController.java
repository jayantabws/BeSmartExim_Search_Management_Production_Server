package com.besmartexim.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.besmartexim.dto.request.SearchCountUpdateRequest;
import com.besmartexim.dto.request.SuggestionRequest;
import com.besmartexim.dto.request.UserSearchRequest;
import com.besmartexim.dto.response.CountryWiseCountResponse;
import com.besmartexim.dto.response.GraphResponse;
import com.besmartexim.dto.response.HsCodeList;
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
@RequestMapping(path = "/search-management")
public class UserSearchController {

	private static final Logger logger = LoggerFactory.getLogger(UserSearchController.class);

	@Autowired
	private UserSearchService userSearchService;

	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> search(@RequestBody @Valid UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/search");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		if ("incoterm".equalsIgnoreCase(userSearchRequest.getOrderByColumn())
				&& "IMPORT".equalsIgnoreCase(userSearchRequest.getTradeType().getValue())) {
			userSearchRequest.setOrderByColumn("incoterms");
		}
		UserSearchResponse userSearchResponse = userSearchService.search(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);
		if (userSearchResponse == null)
			return new ResponseEntity<>("UNAUTHORIZED ACCESS", HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(userSearchResponse, HttpStatus.CREATED);
	}

	@PutMapping(value = "/updatesearchcount/{searchId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatesearchcount(@RequestBody @Valid SearchCountUpdateRequest searchCountUpdateRequest,
			@PathVariable Long searchId, @RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/updatesearchcount");
		userSearchService.updatesearchcount(searchCountUpdateRequest, searchId, accessedBy);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/downloadsearch")
	public ResponseEntity<?> downloadsearch(@RequestParam(required = true) Long searchId,
			@RequestParam(required = false) Long recordsDownloaded, @RequestHeader(required = true) Long accessedBy)
			throws Exception {
		logger.info("Request : /search-management/downloadsearch");
		userSearchService.downloadsearch(searchId, accessedBy, recordsDownloaded);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/searchcount", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchcount(@RequestBody @Valid UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/searchcount");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		Long total_shipment = userSearchService.searchcount(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(total_shipment, HttpStatus.OK);
	}

	@PostMapping(value = "/searchcountbycolumn", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchcountbycolumn(@RequestBody @Valid UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/searchcountbycolumn");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		Long total_count = userSearchService.searchcountbycolumn(userSearchRequest);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(total_count, HttpStatus.OK);
	}

	@PostMapping(value = "/listimporters", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listimporters(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listimporters");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListImportersResponse listImportersResponse = userSearchService.listimporters(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listImportersResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listexporters", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listexporters(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listexporters");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListExportersResponse listExportersResponse = userSearchService.listexporters(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listExportersResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listforeigncountries", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listforeigncountries(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listforeigncountries");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListCountriesResponse listCountriesResponse = userSearchService.listforeigncountries(userSearchRequest,
				accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listCountriesResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listindiancities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listindiancities(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listindiancities");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListCitiesResponse listCitiesResponse = userSearchService.listindiancities(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listCitiesResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listindianports", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listindianports(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listindianports");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListPortsResponse listPortsResponse = userSearchService.listindianports(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listPortsResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listforeignports", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listforeignports(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listforeignports");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListPortsResponse listPortsResponse = userSearchService.listforeignports(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listPortsResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listhscodes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listhscodes(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listhscodes");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListHscodesResponse listHscodesResponse = userSearchService.listhscodes(userSearchRequest, accessedBy);

		System.out.println(userSearchRequest);

		return new ResponseEntity<>(listHscodesResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listmonthwise", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listmonthwise(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listmonthwise");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListMonthwiseResponse listMonthwiseResponse = userSearchService.listmonthwise(userSearchRequest, accessedBy,
				"not");

		System.out.println(userSearchRequest);

		return new ResponseEntity<>(listMonthwiseResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listhscodes4digit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listhscodes4digit(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listhscodes4digit");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListHscodesResponse listHscodesResponse = userSearchService.listhscodes4digit(userSearchRequest, accessedBy);

		System.out.println(userSearchRequest);

		return new ResponseEntity<>(listHscodesResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listshipmentmode", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listshipmentmode(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listshipmentmode");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListShipmentModeResponse listShipmentModeResponse = userSearchService.listshipmentmode(userSearchRequest,
				accessedBy);

		System.out.println(userSearchRequest);

		return new ResponseEntity<>(listShipmentModeResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/liststdunit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> liststdunit(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/liststdunit");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		ListStdUnitResponse listStdUnitResponse = userSearchService.liststdunit(userSearchRequest, accessedBy);

		System.out.println(userSearchRequest);

		return new ResponseEntity<>(listStdUnitResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/search/details", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> SearchDetails(@RequestParam Long searchId, @RequestHeader(required = true) Long accessedBy)
			throws Exception {
		logger.info("accessedBy = " + accessedBy);

		SearchDetailsResponse searchDetailsResponse = userSearchService.searchDetails(searchId);

		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/search/topFiveQueries", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> topFiveQueries(@RequestParam Long usetId, @RequestHeader(required = true) Long accessedBy)
			throws Exception {
		logger.info("accessedBy = " + accessedBy);

		SearchDetailsResponse searchDetailsResponse = userSearchService.topFiveQueries(usetId);

		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/search/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> allQueries(@RequestParam(required = false) Long userId,
			@RequestParam(required = false) Long uplineId, @RequestParam(required = false) String isDownloaded,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestHeader(required = true) Long accessedBy) throws Exception {

		SearchDetailsResponse searchDetailsResponse = userSearchService.listAllQueries(userId, uplineId, isDownloaded,
				accessedBy, PageRequest.of(page, size));

		return new ResponseEntity<>(searchDetailsResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/search/countAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> countAllQueries(@RequestParam(required = false) Long userId,
			@RequestParam(required = false) Long uplineId, @RequestParam(required = false) String isDownloaded,
			@RequestHeader(required = true) Long accessedBy) throws Exception {

		long count = userSearchService.countAllQueries(userId, uplineId, isDownloaded, accessedBy);

		return new ResponseEntity<>(count, HttpStatus.OK);

	}

	@PostMapping(value = "/suggestionlist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listSuggestion(@RequestBody SuggestionRequest suggestionRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("accessedBy = " + accessedBy);
		SuggestionListResponse suggestionListResponse = userSearchService.listSuggestion(suggestionRequest, accessedBy);

		return new ResponseEntity<>(suggestionListResponse, HttpStatus.OK);

	}

	@PostMapping(value = "/countrywisecount", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> countrywisecount(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listimporters");
		// mstContinentService.continentCreate(mstContinentRequest, accessedBy);
		CountryWiseCountResponse countryWiseCountResponse = userSearchService.countrywisecount(userSearchRequest,
				accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(countryWiseCountResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/listdistinctcolumnvalue", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listdistinctcolumnvalue(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/listdistinctcolumnvalue");

		if ("incoterm".equalsIgnoreCase(userSearchRequest.getColumnName())
				&& "IMPORT".equalsIgnoreCase(userSearchRequest.getTradeType().getValue())) {
			userSearchRequest.setColumnName("incoterms");
		}

		ListDistinctColumnValuesResponse listDistinctColumnValuesResponse = userSearchService
				.listdistinctcolumnvalue(userSearchRequest, accessedBy);

		// System.out.println(userSearchRequest);

		return new ResponseEntity<>(listDistinctColumnValuesResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/search/listAllnew", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchDetailsResponse> allQueriesNew(@RequestParam(required = false) Long userId,
			@RequestParam(required = false) Long uplineId, @RequestParam(required = false) String isDownloaded,
			@RequestParam(required = false) String searchValue, @RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "20") int pageSize, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestHeader(required = true) Long accessedBy)
			throws Exception {

		logger.info("Request : /search-management/listAllnew");

		Date fd = null, td = null;

		if (fromDate != null && fromDate != "") {
			fd = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		}
		if (toDate != null && toDate != "") {
			toDate = toDate + " 23:59:59.999";
			td = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(toDate);
		}
		if (td == null && fd != null) {
			td = new Date();
		}

		SearchDetailsResponse searchDetailsResponse = userSearchService.listAllQueriesNew(userId, uplineId,
				isDownloaded, accessedBy, searchValue,
				PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending()), fd, td);

		return ResponseEntity.ok(searchDetailsResponse);

	}

	@GetMapping(value = "/search/countAllnew", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> countAllQueriesNew(@RequestParam(required = false) Long userId,
			@RequestParam(required = false) Long uplineId, @RequestParam(required = false) String isDownloaded,
			@RequestParam(required = false) String searchValue, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestHeader(required = true) Long accessedBy)
			throws Exception {

		logger.info("Request : /search-management/countAllnew");

		Date fd = null, td = null;

		if (fromDate != null && fromDate != "") {
			fd = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		}
		if (toDate != null && toDate != "") {
			toDate = toDate + " 23:59:59.999";
			td = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(toDate);
		}
		if (td == null && fd != null) {
			td = new Date();
		}

		Long count = userSearchService.countAllQueriesNew(userId, uplineId, isDownloaded, searchValue, accessedBy, fd,
				td);

		return ResponseEntity.ok(count);

	}

	@PostMapping(value = "/getvalue", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTotalValue(@RequestBody @Valid UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/getTotalValue");

		Long total_value = userSearchService.getValue(userSearchRequest, accessedBy);

		return ResponseEntity.ok(total_value);
	}

	@PostMapping(value = "/searchdepth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchForInDepth(@RequestBody @Valid UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/searchdepth");

		if (userSearchRequest.getSearchId() == null || userSearchRequest.getSearchId() == 0) {
			logger.error(
					"=========================== Invalid Search ID, Please Check =========================================");
			return new ResponseEntity<>("Invalid Search ID, Please Check", HttpStatus.BAD_REQUEST);
		}

		if ("incoterm".equalsIgnoreCase(userSearchRequest.getOrderByColumn())
				&& "IMPORT".equalsIgnoreCase(userSearchRequest.getTradeType().getValue())) {
			userSearchRequest.setOrderByColumn("incoterms");
		}

		UserSearchResponse userSearchResponse = userSearchService.searchInDepth(userSearchRequest, accessedBy);

		return ResponseEntity.ok(userSearchResponse);
	}

	@PostMapping(value = "/monthwiseindepth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListMonthwiseResponse> listmonthwiseDepth(@RequestBody UserSearchRequest userSearchRequest,
			@RequestHeader(required = true) Long accessedBy) throws Exception {
		logger.info("Request : /search-management/monthwiseindepth");

		ListMonthwiseResponse listMonthwiseResponse = userSearchService.listmonthwise(userSearchRequest, accessedBy,
				"depth");

		return ResponseEntity.ok(listMonthwiseResponse);
	}

	@GetMapping(value = "/industrygraph", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GraphResponse>> getIndustryGraphData(@RequestParam(required = true) String exImp,
			@RequestParam(required = true) String fromDate, @RequestParam(required = true) String toDate,
			@RequestParam(required = true) String hsCode, @RequestHeader(required = true) Long accessedBy)
			throws Exception {
		logger.info("Request : /search-management/industrygraph");

		List<GraphResponse> res = this.userSearchService.industryGraph(exImp, fromDate, toDate, hsCode);

		return ResponseEntity.ok(res);
	}

	@GetMapping(value = "/hscodelist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HsCodeList>> getHsCodeList(@RequestParam(required = true) String exImp,
			@RequestParam(required = true) Integer digit, @RequestHeader(required = true) Long accessedBy)
			throws Exception {

		logger.info("Request : /search-management/hscodelist");

		List<HsCodeList> listHscodesResponse = userSearchService.getListOfHsCode(exImp, digit, accessedBy);

		return ResponseEntity.ok(listHscodesResponse);
	}

//	@GetMapping(value = "/realtivegraph", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<GraphResponse>> getRelativeGraphData(@RequestParam(required = true) String exImp,@RequestParam(required = true) String countryCode,
//			@RequestParam(required = true) String fromDate, @RequestParam(required = true) String toDate,
//			@RequestParam(required = true) String hsCode, @RequestHeader(required = true) Long accessedBy) throws Exception {
//		
//		logger.info("Request : /search-management/realtivegraph");
//
//		List<GraphResponse> listHscodesResponse = userSearchService.getGraphData(exImp,countryCode, fromDate, toDate, hsCode,accessedBy);
//
//		return ResponseEntity.ok(listHscodesResponse);
//	}
}
