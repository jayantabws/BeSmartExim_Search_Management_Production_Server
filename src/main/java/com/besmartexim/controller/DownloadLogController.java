package com.besmartexim.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.besmartexim.database.entity.DownloadLog;
import com.besmartexim.dto.request.DownloadLogRequest;
import com.besmartexim.dto.response.DownloadLogResponse;
import com.besmartexim.service.DownloadLogService;

@CrossOrigin
@RestController
@RequestMapping(path="/download-log")
public class DownloadLogController {

	private static final Logger logger = LoggerFactory.getLogger(DownloadLogController.class);
	
	@Autowired
	private DownloadLogService logService;

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DownloadLogResponse> saveDownloadLog(@RequestBody @Valid DownloadLogRequest downloadLogRequest, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /download-log/save");
		
		return new ResponseEntity<>(this.logService.saveDownloadLog(downloadLogRequest, accessedBy), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/get-log/{searchId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DownloadLog>> getDownloadLogUsingSearchId(@PathVariable @Valid Long searchId, @RequestHeader(value="accessedBy", required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /download-log/get-log");
		
		return new ResponseEntity<>(this.logService.getDownloadLogBySearchId(searchId), HttpStatus.OK);
		
	}
	
	
}
