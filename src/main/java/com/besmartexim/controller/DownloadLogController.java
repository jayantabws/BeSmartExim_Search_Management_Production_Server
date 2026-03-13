package com.besmartexim.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DownloadLogResponse> saveDownloadLog(@RequestBody @Valid DownloadLogRequest downloadLogRequest, @RequestHeader(required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /download-log/save");
		
		return new ResponseEntity<>(this.logService.saveDownloadLog(downloadLogRequest, accessedBy), HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/get-log/{searchId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DownloadLog>> getDownloadLogUsingSearchId(@PathVariable @Valid Long searchId, @RequestHeader(required=true) Long accessedBy ) throws Exception{
		logger.info("Request : /download-log/get-log");
		
		return new ResponseEntity<>(this.logService.getDownloadLogBySearchId(searchId), HttpStatus.OK);
		
	}
	
	
}
