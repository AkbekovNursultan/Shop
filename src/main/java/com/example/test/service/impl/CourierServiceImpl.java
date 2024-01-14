package com.example.test.service.impl;

import com.example.test.dto.courier.CourierRequest;
import com.example.test.dto.courier.CourierResponse;
import com.example.test.entity.Courier;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.CourierMapper;
import com.example.test.repository.CourierRepository;
import com.example.test.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierMapper courierMapper;
    private final CourierRepository courierRepository;
    @Override
    public CourierResponse getById(Long id) {
        Optional<Courier> delivery = courierRepository.findById(id);
        if(delivery.isEmpty()){
            throw new NotFoundException("lol", HttpStatus.NOT_FOUND);
        }
        return courierMapper.toDto(delivery.get());
    }

    @Override
    public List<CourierResponse> getAll() {
        return courierMapper.toDtoS(courierRepository.findAll());
    }

    @Override
    public void register(CourierRequest courierRequest) {
        Courier courier = new Courier();
        courier.setName(courierRequest.getName());
        courier.setSalary(courierRequest.getSalary());
        courierRepository.save(courier);
    }

    @Override
    public void deleteCourier(Long id) {
        Optional<Courier> courier = courierRepository.findById(id);
        if(courier.isEmpty()){
            throw new NotFoundException("lol", HttpStatus.NOT_FOUND);
        }
        courierRepository.deleteById(id);
    }
}
