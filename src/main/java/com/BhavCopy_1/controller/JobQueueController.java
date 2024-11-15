//package com.BhavCopy_1.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.BhavCopy_1.entity.BhavCopy;
//import com.BhavCopy_1.entity.JobQueue;
//import com.BhavCopy_1.service.BhavCopyService;
//import com.BhavCopy_1.service.JobQueueService;
//import com.BhavCopy_1.utill.BhavCopyDetails;
//
//@RestController
//@RequestMapping("/api/jobq")
//public class JobQueueController {
//	private final JobQueueService jobQueueService;
//    private final BhavCopyService bhavCopyService;
//
//    @Autowired
//    public JobQueueController(JobQueueService jobQueueService, BhavCopyService bhavCopyService) {
//        this.jobQueueService = jobQueueService;
//        this.bhavCopyService = bhavCopyService;
//    }
//
//    @PostMapping("/getParticularBhavcopyDataBySymbols")
//    public ResponseEntity<BhavCopy> getParticularSymbolBySeries(@RequestBody BhavCopyDetails details) {
//        Map<String, Object> request = new HashMap<>();
//        request.put("Symbol", details.getSymbol());
//        
//        // Create a job entry in the job queue
//        JobQueue job = jobQueueService.createJob(request);
//
//        // Fetch BhavCopy data for the given symbol and series
//        BhavCopy particularBhavCopyDataBySymbol = bhavCopyService.getParticularSymbolBySeries(details.getSymbol());
//
//        // Update the job with the response
//        Map<String, Object> response = new HashMap<>();
//        response.put("response", particularBhavCopyDataBySymbol);
//        jobQueueService.updateJob(job, response);
//
//        return new ResponseEntity<>(particularBhavCopyDataBySymbol, HttpStatus.OK);
//    }
//
//    @PostMapping("/countSymbolsBySeries")
//    public ResponseEntity<String> countSymbolsBySeries(@RequestBody BhavCopyDetails details) {
//        long count = bhavCopyService.countSymbolsBySeries(details.getSeries());
//        String message = "The count of given series \"" + details.getSeries() + "\" is " + count;
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }
//
//    // Endpoint to update job status to done
//    @PutMapping("/update/{reqid}")
//    public ResponseEntity<JobQueue> updateJobStatus(@PathVariable UUID reqid, @RequestBody String response) {
//        JobQueue updatedJob = jobQueueService.updateJobStatus(reqid, response);
//        return ResponseEntity.ok(updatedJob);
//    }
//}
