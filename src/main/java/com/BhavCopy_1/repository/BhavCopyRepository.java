package com.BhavCopy_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BhavCopy_1.entity.BhavCopy;

public interface BhavCopyRepository extends JpaRepository<BhavCopy,Integer> {
	
	 Optional<BhavCopy> findBySymbol(String symbol);
	 
	 List<BhavCopy> findAllBySeries(String series);
	 
	 @Query(value = "SELECT * FROM bhav_copy_data_date_wise WHERE symbol =?1", nativeQuery = true)
	 List<BhavCopy> getParticularSymbolBySeries(String symbol);


     @Query(value = "select count(series) from bhavCopy_data_date_wise where series= ?1", nativeQuery = true)
     Long countOfSymbolsBySeries(String series);

}
