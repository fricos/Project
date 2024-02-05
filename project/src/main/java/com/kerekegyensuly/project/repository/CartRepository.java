package com.kerekegyensuly.project.repository;

import com.kerekegyensuly.project.model.Cart;
import com.kerekegyensuly.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    List<Cart> deleteByUser(User user);
}
