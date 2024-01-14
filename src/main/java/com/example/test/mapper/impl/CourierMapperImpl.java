package com.example.test.mapper.impl;

import com.example.test.dto.courier.CourierResponse;
import com.example.test.entity.Courier;
import com.example.test.mapper.CourierMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourierMapperImpl implements CourierMapper {
    @Override
    public CourierResponse toDto(Courier courier) {
        CourierResponse courierResponse = new CourierResponse();
        courierResponse.setId(courier.getId());
        courierResponse.setName(courier.getName());
        courierResponse.setSalary(courier.getSalary());

        return courierResponse;
    }

    @Override
    public List<CourierResponse> toDtoS(List<Courier> all) {
        List<CourierResponse> courierRespons = new ArrayList<>();
        for(Courier courier : all){
            courierRespons.add(toDto(courier));
        }
        return courierRespons;
    }
}
