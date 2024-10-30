package com.BhavCopy_1.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BhavCopy_1.entity.BhavCopy;
import com.BhavCopy_1.exception.ResourceNotFoundException;
import com.BhavCopy_1.service.BhavCopyService;
import com.BhavCopy_1.utill.BhavCopyDetails;

@RestController
@RequestMapping("/api/bhavcopy")
public class BhavCopyController {
	 private static final Logger logger = LoggerFactory.getLogger(BhavCopyController.class);

	 private BhavCopyService bhavCopyService;

	    public BhavCopyController(BhavCopyService bhavCopyService) {
	        this.bhavCopyService = bhavCopyService;
	    }

	    @PostMapping("/upload")
	    public ResponseEntity<String> uploadBhavcopy(@RequestParam("csvFile") MultipartFile file) throws IOException {
	        String filePath = "C:\\Users\\Merceadm\\Desktop\\temp csv\\temp.csv";
	        try (FileOutputStream out = new FileOutputStream(filePath)) {
	            byte[] bytes = file.getBytes();
	            out.write(bytes);
	        } catch (IOException e) {
	            return new ResponseEntity<>("Failed to save file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        bhavCopyService.saveAllTheDataOfBhavCopy(filePath);
	        return new ResponseEntity<>("All the data has been saved to database", HttpStatus.OK);
	    }

	    @PostMapping("/symbol")
	    public ResponseEntity<List<BhavCopy>> getParticularSymbolBySymbol(@RequestBody BhavCopyDetails details) {
	        String symbol = details.getSymbol();
	        List<BhavCopy> results = bhavCopyService.getParticularSymbolBySymbol(symbol);
	        return new ResponseEntity<>(results, HttpStatus.OK);
	    }


	    @PostMapping("/series/count")
	    public ResponseEntity<?> countSymbolsBySeries(@RequestBody BhavCopy request) {
	        String series = request.getSeries();
	        Integer count = bhavCopyService.countSymbolsBySeries(series);

	        if (count == null || count == 0) {
	            logger.warn("No symbols found for series: {}", series);
	            throw new ResourceNotFoundException("No symbols found for series: " + series);
	        }
	        
	        logger.info("Symbol count for series '{}' is {}", series, count);
	        return ResponseEntity.ok(count);  // This returns just the number if found
	    }
	    @PostMapping("/findAllSymbolsBasedOnGain")
	    public ResponseEntity<List<String>> findAllSymbolsBasedOnGain(@RequestBody BhavCopyDetails details){
	        List<String> allSymbolsBasedOnGain = bhavCopyService.findAllSymbolsBasedOnGain(details.getGain());
	        return new ResponseEntity<>(allSymbolsBasedOnGain,HttpStatus.OK);
	    }
	    @PostMapping("/findAllSymbolsBasedHighLow")
	    public ResponseEntity<List<String>> findAllSymbolsBasedHighLow(@RequestBody BhavCopyDetails details){
	        List<String> findAllSymbolsBasedHighLow = bhavCopyService.findAllSymbolsBasedHighLow(details.getGain());
	        return new ResponseEntity<>(findAllSymbolsBasedHighLow,HttpStatus.OK);
	    }
	    @PostMapping("/findStandardDev")
	    public ResponseEntity<Double> findStandardDev(@RequestBody BhavCopyDetails details){
	        double standardDev = bhavCopyService.findStandardDev(details.getSeries());
	        return new ResponseEntity<>(standardDev,HttpStatus.OK);
        }
	    @PostMapping("/findTopNGainer")
	    public ResponseEntity<List<Map<Double, String>>> findTopNGainer(@RequestBody BhavCopyDetails details){
	        List<Map<Double, String>> topNGainers = bhavCopyService.findTopNGainer(details.getNumber());
	        return new ResponseEntity<>(topNGainers,HttpStatus.OK);
	    }
	    @PostMapping("/findBottomNGainer")
	    public ResponseEntity<List<Map<Double, String>>> findBottomNGainer(@RequestBody BhavCopyDetails details){
	        List<Map<Double, String>> bottomNGainers = bhavCopyService.findBottomNGainer(details.getNumber());
	        return new ResponseEntity<>(bottomNGainers,HttpStatus.OK);
	    }
	    @PostMapping("/findTopNTraded")
	    public ResponseEntity<List<Map<Double, String>>> findTopNTraded(@RequestBody BhavCopyDetails details){
	        List<Map<Double, String>> topNTraded = bhavCopyService.findTopNTraded(details.getNumber());
	        return new ResponseEntity<>(topNTraded,HttpStatus.OK);
	    }
	    @PostMapping("/findHighestAndLowestTraded")
	    public ResponseEntity<List<String>> findHighestAndLowestTraded(@RequestBody BhavCopyDetails details){
	        List<String> highestAndLowestTraded = bhavCopyService.findHighestAndLowestTraded(details.getSeries());
	        return new ResponseEntity<>(highestAndLowestTraded,HttpStatus.OK);
	    }


}
