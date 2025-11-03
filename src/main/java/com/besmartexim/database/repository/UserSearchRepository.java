package com.besmartexim.database.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.besmartexim.database.entity.UserSearch;


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
	
	Page<UserSearch> findByCreatedBy(Long userId, Pageable pageable);
	Page<UserSearch> findByCreatedByAndIsDownloaded(Long userId,String isDownloaded, Pageable pageable);
	Page<UserSearch> findAll(Pageable pageable);
	Page<UserSearch> findByIsDownloaded(String isDownloaded, Pageable pageable);
	
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

	
	// new
			@Query(nativeQuery = true, value="SELECT * FROM user_search where is_downloaded = :isDownloaded and  created_by = :userId and search_json like :searchValue order by created_date desc offset :page rows fetch next :size rows only")// Q1
			List<UserSearch>  customByUserIdAndIsDownloadAndSearchValue(Long userId,String isDownloaded, String searchValue, int page, int size);
			
			@Query(nativeQuery = true, value="SELECT * FROM user_search where created_by = :userId and search_json like :searchValue order by created_date desc offset :page rows fetch next :size rows only") // Q2
			List<UserSearch>  customByUserIdAndSearchValue(Long userId,String searchValue, int page, int size);
			
			@Query(nativeQuery = true, value="SELECT * FROM user_search where created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId) and is_downloaded = :isDownloaded and search_json like :searchValue order by created_date desc offset :page rows fetch next :size rows only") // Q3
			List<UserSearch> customByUplineIdAndIsDownloadAndSearchValue(Long uplineId, String isDownloaded, String searchValue, int page, int size);
			
			@Query(nativeQuery = true, value="SELECT * FROM user_search where created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId) and search_json like :searchValue order by created_date desc offset :page rows fetch next :size rows only") // Q4
			List<UserSearch> customByUplineIdAndSearchValue(Long uplineId, String searchValue, int page, int size);
			
			@Query(nativeQuery = true, value="SELECT * FROM user_search where is_downloaded = :isDownloaded and search_json like :searchValue order by created_date desc offset :page rows fetch next :size rows only") // Q5
			List<UserSearch> customByIsDownloadedAndSearchValue(String isDownloaded, String searchValue, int page, int size);
			
			@Query(nativeQuery = true, value="SELECT * FROM user_search where search_json like :searchValue order by created_date desc offset :page rows fetch next :size rows only") // Q6
			List<UserSearch> customBySearchValue(String searchValue, int page, int size);
			
			// count
					@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where is_downloaded = :isDownloaded and  created_by = :userId and search_json like :searchValue")// Q1
					long  customCountByUserIdAndIsDownloadAndSearchValue(Long userId,String isDownloaded, String searchValue);
					
					@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where created_by = :userId and search_json like :searchValue") // Q2
					long  customCountByUserIdAndSearchValue(Long userId,String searchValue);
					
					@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId) and is_downloaded = :isDownloaded and search_json like :searchValue") // Q3
					long customCountByUplineIdAndIsDownloadAndSearchValue(Long uplineId, String isDownloaded, String searchValue);
					
					@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where created_by = :uplineId or created_by in (select id from users where upline_id = :uplineId) and search_json like :searchValue ") // Q4
					long customCountByUplineIdAndSearchValue(Long uplineId, String searchValue);
					
					@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where is_downloaded = :isDownloaded and search_json like :searchValue ") // Q5
					long customCountByIsDownloadedAndSearchValue(String isDownloaded, String searchValue);
					
					@Query(nativeQuery = true, value="SELECT count(*) FROM user_search where search_json like :searchValue ") // Q6
					long customCountBySearchValue(String searchValue);
		
		
	
}
