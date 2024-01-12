package com.example.test.mapper;

import com.example.test.dto.delivery.DeliveryResponse;
import com.example.test.entity.Delivery;

import java.util.List;

public interface DeliveryMapper {
    DeliveryResponse toDto(Delivery delivery);
    List<DeliveryResponse> toDtoS(List<Delivery> all);
}
