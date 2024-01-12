package com.example.test.service;

import com.example.test.dto.delivery.DeliveryResponse;

import java.util.List;

public interface DeliveryService {
    DeliveryResponse getById(Long id);

    List<DeliveryResponse> getAll();
}
