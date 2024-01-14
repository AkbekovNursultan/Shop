package com.example.test.service;

import com.example.test.dto.courier.CourierRequest;
import com.example.test.dto.courier.CourierResponse;

import java.util.List;

public interface CourierService {
    CourierResponse getById(Long id);

    List<CourierResponse> getAll();

    void register(CourierRequest courierRequest);

    void deleteCourier(Long id);
}
