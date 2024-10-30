package com.BhavCopy_1.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.BhavCopy_1.entity.BhavCopy;
import com.BhavCopy_1.exception.ResourceNotFoundException;
import com.BhavCopy_1.repository.BhavCopyRepository;
import com.BhavCopy_1.utill.BhavCopyReader;

@Service
public class BhavCopyService {
	private static final Logger log = LoggerFactory.getLogger(BhavCopyService.class);
	
	private BhavCopyRepository bhavCopyRepository;

    public BhavCopyService(BhavCopyRepository bhavCopyRepository) {
        this.bhavCopyRepository = bhavCopyRepository;
    }

    public void saveAllTheDataOfBhavCopy(String filePath) {
        BhavCopyReader reader=new BhavCopyReader();
        List<BhavCopy> bhavCopies = reader.extractBhavCopyData(filePath);
        bhavCopyRepository.saveAll(bhavCopies);
    }

    public List<BhavCopy> getParticularSymbolBySymbol(String symbol) {
        List<BhavCopy> results = bhavCopyRepository.getParticularSymbolBySeries(symbol);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No data found for symbol: " + symbol);
        }
        return results;
    }


	public Integer countSymbolsBySeries(String series) {
		return bhavCopyRepository.findAllBySeries(series).size();
	}

	public List<String> findAllSymbolsBasedOnGain(double gain) {
		 List<BhavCopy> all = bhavCopyRepository.findAll();
	        return all.stream().filter(m -> getGainCalulation(m)> gain).map(n-> n.getSymbol()).collect(Collectors.toList());
	    }
	 private static double getGainCalulation(BhavCopy m) {
	        double openPrice = m.getOpen_price();
	        double closePrice = m.getClose_price();
	        return (closePrice-openPrice)/openPrice;
	    }

	public List<String> findAllSymbolsBasedHighLow(double gain) {
		return bhavCopyRepository.findAll().stream().filter(b->getCalulation(b)>-gain).map(m->m.getSymbol()).collect(Collectors.toList());
	}
	 private static double getCalulation(BhavCopy m) {
		    double highPrice = m.getHigh_price();
		    double lowPrice = m.getLow_price();
		    return (highPrice-lowPrice)/lowPrice;
		}

	public double findStandardDev(String series) {
		List<Double> allClosedPriceBySeries = bhavCopyRepository.findAllBySeries(series).stream().map(e -> e.getClose_price()).toList();
        return calculateStandardDev(allClosedPriceBySeries);
    }

	private static double calculateStandardDev(List<Double> closingPrices) {
		 double average = closingPrices.stream().collect(Collectors.averagingDouble(s -> s));
	        double sum=0;
	        for (int i = 0; i < closingPrices.size(); i++) {
	            sum=sum+(closingPrices.get(i)-average)*(closingPrices.get(i)-average);
	        }
	        double variance = sum / closingPrices.size();
	        return Math.sqrt(variance);
    }

	public List<Map<Double, String>> findTopNGainer(int number) {
		 List<Map<Double, String>> sorted = bhavCopyRepository.findAll().stream().map(c -> calculateGain(c))
	                .sorted(Comparator.comparingDouble(map -> map.keySet().iterator().next())).toList();
	        List<Map<Double, String>> result=new ArrayList<>();
	        for (int i = sorted.size()-1; i >=sorted.size()-number; i--) {
	            result.add(sorted.get(i));
	        }
	       return result;
	    }
	private static Map<Double,String> calculateGain(BhavCopy s) {
        double closePrice = s.getClose_price();
        double openPrice = s.getOpen_price();
        double gain=  (closePrice-openPrice)/openPrice;
        Map<Double,String> gainSymbolPairs=new HashMap<>();
        gainSymbolPairs.put(gain,s.getSymbol());
        return gainSymbolPairs;
    }

	public List<Map<Double, String>> findBottomNGainer(int number) {
		 return bhavCopyRepository.findAll().stream().map(c -> calculateGain(c))
	                .sorted(Comparator.comparingDouble(map -> map.keySet().iterator().next())).toList()
	                .stream().limit(number).toList();
	    }

	public List<Map<Double, String>> findTopNTraded(int number) {
		        List<Map<Double, String>> sorted = bhavCopyRepository.findAll().stream().map(BhavCopyService::convertToMap).sorted(Comparator.comparingDouble(map -> map.keySet().iterator().next())).toList();
		        List<Map<Double, String>> result=new ArrayList<>();
		        for (int i =sorted.size()-1; i >= sorted.size()-number ; i--) {
		            result.add(sorted.get(i));
		        }
		        return result;
		    }
	 public static Map<Double,String> convertToMap(BhavCopy bhavCopy){
	        Map<Double,String> qntySymbolPairs=new HashMap<>();
	        qntySymbolPairs.put(bhavCopy.getTtl_trd_qnty(),bhavCopy.getSymbol());
	        return qntySymbolPairs;
	    }

	public List<String> findHighestAndLowestTraded(String series) {
		List<Map<Double, String>> sortedMap = bhavCopyRepository.findAllBySeries(series).stream().map(b -> convertToMap(b)).
                sorted(Comparator.comparingDouble(map -> map.keySet().iterator().next())).collect(Collectors.toList());
        List<String>result=new ArrayList<>();
        result.add("The highest traded symbol for the given series: "+series+"is "+sortedMap.get(sortedMap.size()-1));
        result.add("The Lowest traded symbol for the given series: "+series+"is "+sortedMap.get(0));
        return result;
    }
}
