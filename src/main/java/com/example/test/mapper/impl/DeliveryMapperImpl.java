package com.example.test.mapper.impl;

import com.example.test.dto.delivery.DeliveryResponse;
import com.example.test.entity.Delivery;
import com.example.test.mapper.DeliveryMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryMapperImpl implements DeliveryMapper {
    @Override
    public DeliveryResponse toDto(Delivery delivery) {
        DeliveryResponse deliveryResponse = new DeliveryResponse();
        deliveryResponse.setId(delivery.getId());
        deliveryResponse.setName(delivery.getName());

        return deliveryResponse;
    }

    @Override
    public List<DeliveryResponse> toDtoS(List<Delivery> all) {
        List<DeliveryResponse> deliveryResponses = new ArrayList<>();
        for(Delivery delivery : all){
            deliveryResponses.add(toDto(delivery));
        }
        return deliveryResponses;
    }
}
