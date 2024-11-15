package com.BhavCopy_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BhavCopy_1.entity.BhavCopy;

public interface BhavCopyRepository extends JpaRepository<BhavCopy,Integer> {
	
	 Optional<BhavCopy> findBySymbol(String symbol);
	 
	 List<BhavCopy> findAllBySeries(String series);
	 
	 @Query(value = "SELECT * FROM bhav_copy WHERE symbol=(:symbol)", nativeQuery = true)
       BhavCopy getParticularSymbolBySeries(String symbol);

	 @Query(value = "SELECT CountSymbolsBySeries(:series) AS symbol_count", nativeQuery = true)
     Long countOfSymbolsBySeries(@Param("series") String series);
	 
	 @Query(value = "SELECT * FROM get_symbols_by_topBot(:topBot)", nativeQuery = true)
     List<String> findAllSymbolsBasedHighLow(@Param("topBot") double topBot);

     @Query(value = "select calculate_stddev_by_series(:series)", nativeQuery = true)
     double findStandardDev(@Param("series") String series);

     @Query(value = "SELECT * FROM public.get_top_n_gainers(:number)", nativeQuery = true)
     List<String> findTopNGainer(@Param("number") int number);

     @Query(value = "SELECT * FROM public.get_bottom_n_traded_symbols(:number)", nativeQuery = true)
     List<String> findBottomNGainer(@Param("number") int number);

     @Query(value = "SELECT * FROM public.get_top_n_traded_symbols(:number)", nativeQuery = true)
     List<String> findTopNTraded(@Param("number") int number);

     @Query(value = "select * from bhav_copy", nativeQuery = true)
     List<BhavCopy> findBottomNTraded();

     @Query(value = "SELECT * FROM public.get_highest_lowest_traded_symbols_by_series(:series)", nativeQuery = true)
     List<String> findHighestAndLowestTraded(@Param("series") String series);


}
