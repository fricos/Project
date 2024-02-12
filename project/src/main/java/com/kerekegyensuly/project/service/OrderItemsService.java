package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.model.OrderItem;
import com.kerekegyensuly.project.repository.OrderItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderItemsService {
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public void addOrderProducts(OrderItem orderItem){
        orderItemsRepository.save(orderItem);
    }
}
