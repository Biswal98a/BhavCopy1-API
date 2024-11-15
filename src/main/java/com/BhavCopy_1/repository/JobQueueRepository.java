package com.BhavCopy_1.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BhavCopy_1.entity.BhavCopy;
import com.BhavCopy_1.entity.JobQueue;

public interface JobQueueRepository extends JpaRepository<JobQueue, Long> {

	JobQueue findByReqid(UUID reqid);
//	Optional<BhavCopy> findBySymbol(String symbol);



}
