package com.shoe.service.impl;

import com.shoe.Utils.Containts;
import com.shoe.Utils.Language;
import com.shoe.dto.CartDTO;
import com.shoe.dto.OrderDTO;
import com.shoe.dto.OrderDetailDTO;
import com.shoe.entity.Order;
import com.shoe.entity.OrderDetail;
import com.shoe.entity.Product;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.OrderDetailRepo;
import com.shoe.reponsitory.OrderRepo;
import com.shoe.reponsitory.ProductRepo;
import com.shoe.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderSerivceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

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
    public Order createOrder(OrderDTO orderDTO) throws DataNotFound {
//        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setOrderID));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setTotalMoney(orderDTO.getTotal_money());
//        order.setActive(1);
//        order.setStatus(Containts.PENDING);
        order.setUserID(1);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(orderDTO.getAddress().equalsIgnoreCase("Hà Nội")){
            // Cộng 1 ngày
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        else{
            // Cộng 2 ngày
            calendar.add(Calendar.DAY_OF_MONTH, 2);
        }
        Date newDate = calendar.getTime();
        order.setShipping_date(newDate);
        orderRepo.save(order);
        for(CartDTO item : orderDTO.getCart_item()){
            OrderDetail o = new OrderDetail();
            o.setOrderID(order.getOrderID());
            int product_id = item.getProduct_id();
            Product product = productRepo.findByProductID(product_id).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
            o.setQuantity(item.getQuantity());
            o.setMoney(item.getQuantity() * product.getPrice());
            o.setProductID(product_id);
            orderDetailRepo.save(o);
        }
        return order;
    }

    @Override
    public Order updateOrder(int id, OrderDTO orderDTO) throws DataNotFound {
        orderRepo.findByOrderID(id).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setOrderID));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setActive(1);
        order.setStatus(Containts.PENDING);
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
            order.setActive(0);
            order.setStatus(Containts.CANCELLED);
            orderRepo.save(order);
        }
    }

    @Override
    public Integer getTotalMoney(int year, int month) {
        String a = orderRepo.sumTotalMoney(year, month);
        if(a == null){
            return 0;
        }
        return Integer.parseInt(a);
    }
}
