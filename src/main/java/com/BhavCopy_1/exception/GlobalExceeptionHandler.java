 package com.BhavCopy_1.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.BhavCopy_1.entity.JobQueue;
import com.BhavCopy_1.repository.JobQueueRepository;
import com.BhavCopy_1.service.JobQueueService;

@ControllerAdvice
public class GlobalExceeptionHandler {
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private JobQueueRepository jobQueueRepository;

    @Autowired
    private JobQueueService jobQueueService;

    // Handle ResourceNotFoundException and trigger asynchronous job update
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Resource not found");
        errorResponse.put("details", ex.getMessage());

        // Extracting UUID from the exception message for job status update
        String[] split = ex.getMessage().split(",");
        if (split.length > 1) {
            try {
                UUID uuid = UUID.fromString(split[1]);
                JobQueue job = jobQueueService.checkJobStatus(uuid);

                // Define the response map for the job update
                Map<String, Object> response = new HashMap<>();
                response.put("response", split[0]);

                // Execute job update asynchronously
                taskExecutor.execute(() -> updateJob(job, response));
            } catch (IllegalArgumentException e) {
                // Log error: UUID parsing failed
                errorResponse.put("UUID parsing error", "Invalid UUID format in exception message");
            }
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Asynchronous method to update job status and simulate long-running task
    private void updateJob(JobQueue job, Map<String, Object> response) {
        try {
            // Simulate long-running task with a random delay
            int sleepTime = ThreadLocalRandom.current().nextInt(10, 91);
            System.out.println("Job " + job.getReqid() + " sleeping for " + sleepTime + " seconds.");
            Thread.sleep(sleepTime * 1000L);

            // Update job status and save response
            job.setStartedat(LocalDateTime.now());
            job.setEndedat(LocalDateTime.now().plusSeconds(sleepTime));
            job.setDuration(java.time.Duration.between(job.getAddedat(), job.getEndedat()).toMillis());
            job.setStatus("done");
            job.setResponse(response);
            jobQueueRepository.save(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            job.setStatus("error");
            jobQueueRepository.save(job);
        }
    }

}
