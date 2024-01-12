package com.example.test.controller;

import com.example.test.dto.delivery.DeliveryRequest;
import com.example.test.dto.delivery.DeliveryResponse;
import com.example.test.mapper.DeliveryMapper;
import com.example.test.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;
    @GetMapping("/{id}")
    public DeliveryResponse deliveryResponse(@PathVariable Long id){
        return deliveryService.getById(id);
    }
    @GetMapping("/all")
    public List<DeliveryResponse> getAll(){
        return deliveryService.getAll();
    }
}
