package com.example.test.service.impl;

import com.example.test.dto.product.ProductRequest;
import com.example.test.dto.product.ProductResponse;
import com.example.test.entity.Product;
import com.example.test.enums.Category;
import com.example.test.exception.NotFoundException;
import com.example.test.mapper.ProductMapper;
import com.example.test.repository.ProductRepository;
import com.example.test.entity.Worker;
import com.example.test.repository.WorkerRepository;
import com.example.test.entity.Courier;
import com.example.test.repository.CourierRepository;
import com.example.test.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final WorkerRepository workerRepository;
    private final CourierRepository courierRepository;
    @Override
    public ProductResponse getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new NotFoundException("Doesn't exist", HttpStatus.NOT_FOUND);
        }
        return productMapper.toDto(product.get());
    }

    @Override
    public List<ProductResponse> getAll() {
        return productMapper.toDtoS(productRepository.findAll());
    }

    @Override
    public void register(ProductRequest productRequest, Long workerId) {
        Product product = new Product();
        product.setName(productRequest.getName());
        if(!containsCategory(productRequest.getCategory())){
            throw new NotFoundException("This category doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.setCategory(Category.valueOf(productRequest.getCategory()));
        product.setQuantity(productRequest.getQuantity());

        Optional<Worker> worker = workerRepository.findById(workerId);
        if(worker.isEmpty()){
            throw new NotFoundException("LOL", HttpStatus.NOT_FOUND);
        }
        List<Product> products = new ArrayList<>();
        if(worker.get().getOrdersList() != null){
            products = worker.get().getOrdersList();
        }
        product.setOrderedBy(worker.get());
        products.add(product);
        worker.get().setOrdersList(products);
        worker.get().setSalary(worker.get().getSalary() + 50);
        workerRepository.save(worker.get());
        productRepository.save(product);
    }
    @Override
    public void deliver(Long productId, Long delivererId) {
        Optional<Courier> courier = courierRepository.findById(delivererId);
        if(courier.isEmpty()){
            throw new NotFoundException("This deliveryman doesn't exist", HttpStatus.NOT_FOUND);
        }
        Optional<Product> product = productRepository.findById(productId);
        List<Product> products = new ArrayList<>();
        if(product.isEmpty()){
            throw new NotFoundException("This product doesn't exist", HttpStatus.NOT_FOUND);
        }
        if(courier.get().getDeliveriesList() != null){
            products = courier.get().getDeliveriesList();
        }
        products.add(product.get());
        product.get().setDeliveredBy(courier.get());
        courier.get().setSalary(courier.get().getSalary() + 75);
        productRepository.save(product.get());
    }
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        if(productRepository.findById(id).isEmpty()){
            throw new NotFoundException("This product doesn't exist", HttpStatus.NOT_FOUND);
        }
        Worker worker = workerRepository.findById(product.getOrderedBy().getId()).get();
        Courier courier = courierRepository.findById(product.getDeliveredBy().getId()).get();
        deleteOrderedBy(worker, id);
        deleteDeliveredBy(courier, id);
        productRepository.deleteById(id);
    }
    public void deleteOrderedBy(Worker worker, Long id){
        List<Product> ordersList = worker.getOrdersList();
        List<Product> newOrdersList = new ArrayList<>();
        for(Product product : ordersList){
            if(!product.getId().equals(id)){
                newOrdersList.add(product);
            }
        }
        worker.setOrdersList(newOrdersList);
        workerRepository.save(worker);
    }
    public void deleteDeliveredBy(Courier courier, Long id){
        List<Product> deliveriesList = courier.getDeliveriesList();
        List<Product> newDeliveriesList = new ArrayList<>();
        for(Product product : deliveriesList){
            if(!product.getId().equals(id)){
                newDeliveriesList.add(product);
            }
        }
        courier.setDeliveriesList(newDeliveriesList);
        courierRepository.save(courier);
    }

    public boolean containsCategory(String s){
        for(Category category : Category.values()){
            if(category.name().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
}
