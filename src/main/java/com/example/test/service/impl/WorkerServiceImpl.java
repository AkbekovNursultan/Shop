package com.example.test.service.impl;
import com.example.test.dto.worker.WorkerRequest;
import com.example.test.dto.worker.WorkerResponse;
import com.example.test.entity.Worker;
import com.example.test.exception.BadRequestException;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.WorkerMapper;
import com.example.test.repository.WorkerRepository;
import com.example.test.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerMapper workerMapper;
    private final WorkerRepository workerRepository;
    @Override
    public WorkerResponse getById(Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        if(!worker.isPresent()){
            throw new NotFoundException("LOL", HttpStatus.NOT_FOUND);
        }
        return workerMapper.toDto(worker.get());
    }

    @Override
    public void register(WorkerRequest workerRequest) {
        Worker worker = new Worker();
        worker.setName(workerRequest.getName());
        workerRepository.save(worker);

    }

    @Override
    public void deleteWorker(Long id) {
        if(workerRepository.findById(id).isEmpty()){
            throw new NotFoundException("This worker doesn't exist", HttpStatus.NOT_FOUND);
        }
        workerRepository.deleteById(id);
    }

    @Override
    public List<WorkerResponse> getAll() {
        return workerMapper.toDtoS(workerRepository.findAll());
    }
}
