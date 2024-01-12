package com.example.test.mapper.impl;

import com.example.test.dto.worker.WorkerResponse;
import com.example.test.entity.Worker;
import com.example.test.mapper.WorkerMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class WorkerMapperImpl implements WorkerMapper {
    @Override
    public WorkerResponse toDto(Worker user) {
        WorkerResponse workerResponse = new WorkerResponse();
        workerResponse.setName(user.getName());
        workerResponse.setId(user.getId());

        return workerResponse;
    }

    @Override
    public List<WorkerResponse> toDtoS(List<Worker> all) {
        List<WorkerResponse> workerResponses = new ArrayList<>();
        for(Worker worker : all){
            workerResponses.add(toDto(worker));
        }

        return workerResponses;
    }
}
