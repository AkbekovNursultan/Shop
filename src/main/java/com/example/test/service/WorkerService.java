package com.example.test.service;
import com.example.test.dto.worker.WorkerResponse;
import com.example.test.dto.worker.WorkerRequest;

import java.util.List;

public interface WorkerService {
    WorkerResponse getById(Long id);

    void register(WorkerRequest workerRequest);

    void deleteWorker(Long id);

    List<WorkerResponse> getAll();
}
