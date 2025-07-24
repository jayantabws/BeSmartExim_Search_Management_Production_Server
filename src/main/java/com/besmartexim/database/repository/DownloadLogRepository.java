package com.besmartexim.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.besmartexim.database.entity.DownloadLog;

@Repository
public interface DownloadLogRepository extends JpaRepository<DownloadLog, Long>{

	public List<DownloadLog> findBySearchId(Long searchId);
}
