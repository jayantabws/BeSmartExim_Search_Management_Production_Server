package com.besmartexim.database.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.besmartexim.database.entity.UserSearch;
import com.besmartexim.dto.response.ExpInd;


@Repository
public interface UserSearchRepository extends JpaRepository<UserSearch, Long> {
	
	/*
	@Procedure(value = "ListSearchResult")
	List<ExpInd> getListOfExportIND(String searchType,String tradeType,String frmDate, String toDate,String searchBy,
																	String searchValue, String countryCode,Integer pageNo,Integer numberOfRecords);
*/
	
	List<UserSearch> findTop5ByCreatedByOrderByCreatedDateDesc(Long createdBy);
	List<UserSearch> findAllByCreatedByOrderByCreatedDateDesc(Long createdBy);
	// List<UserSearch> findAllOrderByCreatedDateDesc();
//	List<UserSearch> findByCreatedByOrderByCreatedDateDesc(Long userId);
//	List<UserSearch> findByCreatedByAndIsDownloadedOrderByCreatedDateDesc(Long userId,String isDownloaded);
//	List<UserSearch> findAllByOrderByCreatedDateDesc();
//	List<UserSearch> findByIsDownloadedOrderByCreatedDateDesc(String isDownloaded);
	
	Page<UserSearch> findByCreatedByOrderByCreatedDateDesc(Long userId, Pageable pageable);
	Page<UserSearch> findByCreatedByAndIsDownloadedOrderByCreatedDateDesc(Long userId,String isDownloaded, Pageable pageable);
	Page<UserSearch> findAllByOrderByCreatedDateDesc(Pageable pageable);
	Page<UserSearch> findByIsDownloadedOrderByCreatedDateDesc(String isDownloaded, Pageable pageable);
	
	@Query(nativeQuery = true, value="SELECT * FROM user_search where created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId) order by created_date desc offset :page rows fetch next :size rows only ")
	List<UserSearch> findByUplineIdOrderByCreatedDateDesc(Long uplineId, int page, int size);
	
	@Query(nativeQuery = true, value="SELECT * FROM user_search where is_downloaded = :isDownloaded and (created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId)) order by created_date desc offset :page rows fetch next :size rows only") 
	List<UserSearch> findByUplineIdAndIsDownloadedOrderByCreatedDateDesc(Long uplineId, String isDownloaded, int page, int size);
	
	//   Count methods..........
	@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where created_by = :createdBy")
	long countByCreatedBy(Long createdBy);
	
	@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where created_by = :createdBy and is_downloaded = :isDownloaded")
	long countByCreatedByAndIsDownloaded(Long createdBy, String isDownloaded);
	
	@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where is_downloaded = :isDownloaded")
	long countByIsDownloaded(String isDownloaded);
	
	
	@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId)")
	long countByUplineId(Long uplineId);
	
	@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where is_downloaded = :isDownloaded and (created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId))") 
	long countByUplineIdAndIsDownloaded(Long uplineId, String isDownloaded);

	
	
	
}
