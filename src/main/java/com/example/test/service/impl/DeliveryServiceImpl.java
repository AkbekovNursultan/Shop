package com.example.test.service.impl;

import com.example.test.dto.delivery.DeliveryResponse;
import com.example.test.entity.Delivery;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.DeliveryMapper;
import com.example.test.repository.DeliveryRepository;
import com.example.test.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryMapper deliveryMapper;
    private final DeliveryRepository deliveryRepository;
    @Override
    public DeliveryResponse getById(Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if(delivery.isEmpty()){
            throw new NotFoundException("lol", HttpStatus.NOT_FOUND);
        }
        return deliveryMapper.toDto(delivery.get());
    }

    @Override
    public List<DeliveryResponse> getAll() {
        return deliveryMapper.toDtoS(deliveryRepository.findAll());
    }
}
