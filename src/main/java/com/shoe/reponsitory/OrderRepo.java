package com.shoe.reponsitory;

import com.shoe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderID(int id);
}
