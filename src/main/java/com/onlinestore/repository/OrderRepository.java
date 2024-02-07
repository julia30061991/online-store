package com.onlinestore.repository;

import com.onlinestore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <Order, Integer> {

    Order getAllById(int id);
}
