package com.example.test.mapper;

import com.example.test.dto.worker.WorkerResponse;
import com.example.test.entity.Worker;

import java.util.List;

public interface WorkerMapper {
    WorkerResponse toDto(Worker worker);

    List<WorkerResponse> toDtoS(List<Worker> all);
}
