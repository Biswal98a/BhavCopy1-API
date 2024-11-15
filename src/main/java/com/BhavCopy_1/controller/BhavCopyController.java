package com.BhavCopy_1.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BhavCopy_1.entity.BhavCopy;
import com.BhavCopy_1.entity.JobQueue;
import com.BhavCopy_1.exception.ResourceNotFoundException;
import com.BhavCopy_1.repository.JobQueueRepository;
import com.BhavCopy_1.service.BhavCopyService;
import com.BhavCopy_1.service.JobQueueService;
import com.BhavCopy_1.utill.BhavCopyDetails;

@RestController
@RequestMapping("/api/bhavcopy")
public class BhavCopyController {
	 private BhavCopyService bhavCopyService;
	    private JobQueueService jobQueueService;
	    private JobQueueRepository jobQueueRepository;
	    private ThreadPoolTaskExecutor taskExecutor;

	    public BhavCopyController(ThreadPoolTaskExecutor taskExecutor, JobQueueRepository jobQueueRepository, BhavCopyService bhavCopyService, JobQueueService jobQueueService) {
	        this.taskExecutor = taskExecutor;
	        this.jobQueueRepository = jobQueueRepository;
	        this.bhavCopyService = bhavCopyService;
	        this.jobQueueService = jobQueueService;
	    }

	    @PostMapping("/uploadBhavcopy")
	    public ResponseEntity<Map<String, Object>> uploadBhavcopy(@RequestBody BhavCopyDetails details) throws IOException {
	        Map<String, Object> request = new HashMap<>();
	        request.put("EncodedBhavCopy", details.getEnCodedBhavCopy().substring(0, 20));
	        JobQueue jobQueue = jobQueueService.createJob(request);
	        String filePath = bhavCopyService.decodeBhavCopy(details.getEnCodedBhavCopy());
	        bhavCopyService.saveAllTheDataOfBhavCopy(filePath);
	        Map<String, Object> response = new HashMap<>();
	        response.put("response", "The file has been uploaded to database successfully and the file path is:- " + filePath);
	        taskExecutor.execute(() -> updateJob(jobQueue, response));
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @PostMapping("/getParticularBhavcopyDataBySymbols")
	    public ResponseEntity<Map<String, Object>> getParticularSymbolBySeries(@RequestBody BhavCopyDetails details) {
	        Map<String, Object> request = new HashMap<>();
	        request.put("SYMBOL", details.getSymbol());
	        JobQueue jobQueue = jobQueueService.createJob(request);
	        BhavCopy particularBhavCopyDataBySymbol = bhavCopyService.getParticularSymbolBySeries(details.getSymbol());
	        Map<String, Object> response = new HashMap<>();
	        response.put("response", particularBhavCopyDataBySymbol);
	        taskExecutor.execute(() -> updateJob(jobQueue, response));
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

//	    @PostMapping("/symbol")
//	    public ResponseEntity<Map<String, Object>> getParticularSymbolBySymbol(@RequestBody BhavCopyDetails details) {
//	        Map<String, Object> response = new HashMap<>();
//	        String symbol = details.getSymbol();
//	        Map<String, Object> jobParams = new HashMap<>();
//	        jobParams.put("SYMBOL", symbol);
//	        JobQueue jobQueue = jobQueueService.createJob(jobParams);
//	        BhavCopy result;
//	        try {
//	            result = bhavCopyService.getParticularSymbolBySymbol(symbol);
//	            response.put("status", "COMPLETED");
//	            response.put("data", result);
//	            jobQueue.setStatus("COMPLETED");
//	            jobQueueRepository.save(jobQueue);
//	        } catch (Exception e) {
//	            response.put("status", "FAILED");
//	            response.put("error", e.getMessage());
//	            jobQueue.setStatus("FAILED");
//	            jobQueueRepository.save(jobQueue);
//	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//	    }

	    @PostMapping("/series/count")
	    public ResponseEntity<?> countSymbolsBySeries(@RequestBody BhavCopy request) {
	        String series = request.getSeries();
	        Integer count = bhavCopyService.countSymbolsBySeries(series);

	        if (count == null || count == 0) {
	            throw new ResourceNotFoundException("No symbols found for series: " + series);
	        }
	        
	        return ResponseEntity.ok(count);
	    }

	    @PostMapping("/findAllSymbolsBasedOnGain")
	    public ResponseEntity<List<String>> findAllSymbolsBasedOnGain(@RequestBody BhavCopyDetails details) {
	        List<String> allSymbolsBasedOnGain = bhavCopyService.findAllSymbolsBasedOnGain(details.getGain());
	        return new ResponseEntity<>(allSymbolsBasedOnGain, HttpStatus.OK);
	    }

	    @PostMapping("/findAllSymbolsBasedHighLow")
	    public ResponseEntity<List<String>> findAllSymbolsBasedHighLow(@RequestBody BhavCopyDetails details) {
	        List<String> findAllSymbolsBasedHighLow = bhavCopyService.findAllSymbolsBasedHighLow(details.getGain());
	        return new ResponseEntity<>(findAllSymbolsBasedHighLow, HttpStatus.OK);
	    }

	    @PostMapping("/findStandardDev")
	    public ResponseEntity<Double> findStandardDev(@RequestBody BhavCopyDetails details) {
	        double standardDev = bhavCopyService.findStandardDev(details.getSeries());
	        return new ResponseEntity<>(standardDev, HttpStatus.OK);
	    }

	    @PostMapping("/findTopNGainer")
	    public ResponseEntity<List<Map<Double, String>>> findTopNGainer(@RequestBody BhavCopyDetails details) {
	        List<Map<Double, String>> topNGainers = bhavCopyService.findTopNGainer(details.getNumber());
	        return new ResponseEntity<>(topNGainers, HttpStatus.OK);
	    }

	    @PostMapping("/findBottomNGainer")
	    public ResponseEntity<List<Map<Double, String>>> findBottomNGainer(@RequestBody BhavCopyDetails details) {
	        List<Map<Double, String>> bottomNGainers = bhavCopyService.findBottomNGainer(details.getNumber());
	        return new ResponseEntity<>(bottomNGainers, HttpStatus.OK);
	    }

	    @PostMapping("/findTopNTraded")
	    public ResponseEntity<List<Map<Double, String>>> findTopNTraded(@RequestBody BhavCopyDetails details) {
	        List<Map<Double, String>> topNTraded = bhavCopyService.findTopNTraded(details.getNumber());
	        return new ResponseEntity<>(topNTraded, HttpStatus.OK);
	    }

	    @PostMapping("/findHighestAndLowestTraded")
	    public ResponseEntity<List<String>> findHighestAndLowestTraded(@RequestBody BhavCopyDetails details) {
	        List<String> highestAndLowestTraded = bhavCopyService.findHighestAndLowestTraded(details.getSeries());
	        return new ResponseEntity<>(highestAndLowestTraded, HttpStatus.OK);
	    }

	    private void updateJob(JobQueue jobQueue, Map<String, Object> response) {
	        try {
	            int sleepTime = ThreadLocalRandom.current().nextInt(10, 91);
	            System.out.println("Job " + jobQueue.getReqid() + " sleeping for " + sleepTime + " seconds.");
	            Thread.sleep(sleepTime * 1000L);

	            jobQueue.setStartedat(LocalDateTime.now());
	            jobQueue.setEndedat(LocalDateTime.now().plusSeconds(sleepTime));
	            jobQueue.setDuration(Duration.between(jobQueue.getStartedat(), jobQueue.getEndedat()));
	            jobQueue.setStatus("done");
	            jobQueue.setResponse(response);
	            jobQueueRepository.save(jobQueue);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            jobQueue.setStatus("error");
	            jobQueueRepository.save(jobQueue);
	        }
	    }
	}