package com.BhavCopy_1.service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.BhavCopy_1.entity.JobQueue;
import com.BhavCopy_1.repository.JobQueueRepository;

@Service
public class JobQueueService {
	@Autowired
    private JobQueueRepository jobQueueRepository;  // Repository to save and retrieve job data

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;  // ThreadPool for background task execution

    // Method to create a new job
    public JobQueue createJob(Map<String, Object> request) {
        JobQueue jobQueue = new JobQueue();
        UUID uuid = UUID.randomUUID();
        jobQueue.setReqid(uuid);
        jobQueue.setAddedat(LocalDateTime.now());
        jobQueue.setParams(request);
        jobQueue.setStatus("pending"); // Initial status set to pending
        jobQueueRepository.save(jobQueue);

        return jobQueue;
    }


    public void updateJob(JobQueue jobQueue,Map<String,Object> response) {
        try {
            // Simulate a long-running task (e.g., sleeping for a random time)
            int sleepTime = ThreadLocalRandom.current().nextInt(10, 91); // Random sleep between 10-90 seconds
            System.out.println("Job " + jobQueue.getReqid() + " sleeping for " + sleepTime + " seconds.");
            Thread.sleep(sleepTime * 1000L);

            // After completion, update the job status to "done"
            jobQueue.setStartedat(LocalDateTime.now());
            jobQueue.setEndedat(LocalDateTime.now().plusSeconds(sleepTime)); // simulate task completion time
            jobQueue.setDuration(Duration.between(jobQueue.getStartedat(), jobQueue.getEndedat())); // Calculate duration
            jobQueue.setStatus("done");
            jobQueue.setResponse(response);
            jobQueueRepository.save(jobQueue);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // In case of error, update the status
            jobQueue.setStatus("error");
            jobQueueRepository.save(jobQueue);
        }
    }


    // Method to check the status of a job
    public JobQueue checkJobStatus(UUID reqid) {
        return jobQueueRepository.findByReqid(reqid);
    }

   
}
