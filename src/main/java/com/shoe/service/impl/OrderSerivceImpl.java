package com.shoe.service.impl;

import com.shoe.Utils.Language;
import com.shoe.dto.OrderDTO;
import com.shoe.entity.Order;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.OrderRepo;
import com.shoe.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderSerivceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Language language;

    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }

    @Override
    public Order getByID(int id) throws DataNotFound {
        return orderRepo.findByOrderID(id).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
//        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setOrderID));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setTotalMoney(1000);
        order.setStatus(true);
        order.setUserID(1);
        if(orderDTO.getAddress().equalsIgnoreCase("Hà Nội")){
            order.setShipping_date(new Date());
        }
        else{
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // Cộng 1 ngày
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date newDate = calendar.getTime();
            order.setShipping_date(newDate);
        }
        return orderRepo.save(order);
    }

    @Override
    public Order updateOrder(int id, OrderDTO orderDTO) throws DataNotFound {
        orderRepo.findByOrderID(id).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setOrderID));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setTotalMoney(1000);
        order.setStatus(true);
        order.setUserID(1);
        if(orderDTO.getAddress().equalsIgnoreCase("Hà Nội")){
            order.setShipping_date(new Date());
        }
        else{
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // Cộng 1 ngày
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date newDate = calendar.getTime();
            order.setShipping_date(newDate);
        }
        return orderRepo.save(order);
    }

    @Override
    public void deleteOrder(int id) throws DataNotFound {
        Order order = orderRepo.findByOrderID(id).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        if(order != null){
            order.setStatus(false);
            orderRepo.save(order);
        }
    }
}
