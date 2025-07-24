package com.besmartexim.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.besmartexim.database.entity.DownloadLog;
import com.besmartexim.database.repository.DownloadLogRepository;
import com.besmartexim.dto.request.DownloadLogRequest;
import com.besmartexim.dto.response.DownloadLogResponse;

@Service
public class DownloadLogService {
	
	@Autowired
	private DownloadLogRepository repository;

	
	public DownloadLogResponse saveDownloadLog(DownloadLogRequest request, Long accessedBy) {
		
		DownloadLog log = new DownloadLog();
		
		if(request.getSearchId() != null)
			log.setSearchId(request.getSearchId());
		if(request.getDownloadJson() != null)
			log.setDownloadJson(request.getDownloadJson());
		if(request.getRecordsDownloaded() != null)
			log.setRecordsDownloaded(request.getRecordsDownloaded());
		if(request.getInitialCredit() != null)
			log.setInitialCredit(request.getInitialCredit());
		if(request.getSearchId() != null)
			log.setSearchId(request.getSearchId());
		if(request.getRemainingCredit() != null)
			log.setRemainingCredit(request.getRemainingCredit());
		
		log.setCreatedBy(accessedBy);
		log.setCreatedDate(new Date());
		
		log = this.repository.save(log);
		
		return new DownloadLogResponse((log.getId() > 0) ? "Download log save successfully" : "Download log not saved");
		
	}
	
	public List<DownloadLog> getDownloadLogBySearchId(Long searchId) {
		
		return this.repository.findBySearchId(searchId);
	}
}
