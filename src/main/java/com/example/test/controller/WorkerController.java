package com.example.test.controller;

import com.example.test.dto.worker.WorkerRequest;
import com.example.test.dto.worker.WorkerResponse;
import com.example.test.repository.WorkerRepository;
import com.example.test.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/worker")
@RestController
@AllArgsConstructor
public class WorkerController {
    private final WorkerRepository workerRepository;
    private final WorkerService workerService;

    @GetMapping("/{id}")
    public WorkerResponse userResponse(@PathVariable Long id){
        return workerService.getById(id);
    }

    @GetMapping("/all")
    public List<WorkerResponse> getAll(){
        return workerService.getAll();
    }

    @PostMapping("/register")
    public void register(@RequestBody WorkerRequest workerRequest){
        workerService.register(workerRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        workerService.deleteWorker(id);
    }

}
