package com.example.test.mapper;

import com.example.test.dto.courier.CourierResponse;
import com.example.test.entity.Courier;

import java.util.List;

public interface CourierMapper {
    CourierResponse toDto(Courier courier);
    List<CourierResponse> toDtoS(List<Courier> all);
}
