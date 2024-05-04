package com.shoe.service;

import com.shoe.dto.OrderDTO;
import com.shoe.entity.Order;
import com.shoe.exception.DataNotFound;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    Order getByID(int id) throws DataNotFound;
    Order createOrder(OrderDTO orderDTO);
    Order updateOrder(int id, OrderDTO orderDTO) throws DataNotFound;
    void deleteOrder(int id) throws DataNotFound;
}
