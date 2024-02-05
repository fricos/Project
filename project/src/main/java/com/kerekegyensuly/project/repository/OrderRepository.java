package com.kerekegyensuly.project.repository;

import com.kerekegyensuly.project.model.Order;
import com.kerekegyensuly.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
