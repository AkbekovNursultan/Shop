package com.example.test.controller;

import com.example.test.dto.courier.CourierRequest;
import com.example.test.dto.courier.CourierResponse;
import com.example.test.mapper.CourierMapper;
import com.example.test.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/courier")
public class CourierController {
    private final CourierService courierService;
    private final CourierMapper courierMapper;
    @GetMapping("/{id}")
    public CourierResponse deliveryResponse(@PathVariable Long id){
        return courierService.getById(id);
    }
    @GetMapping("/all")
    public List<CourierResponse> getAll(){
        return courierService.getAll();
    }
    @PostMapping("/register")
    public void register(@RequestBody CourierRequest courierRequest){
        courierService.register(courierRequest);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCourier(@PathVariable Long id){
        courierService.deleteCourier(id);
    }
}
