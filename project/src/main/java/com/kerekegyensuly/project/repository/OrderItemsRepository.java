package com.kerekegyensuly.project.repository;

import com.kerekegyensuly.project.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {
}
