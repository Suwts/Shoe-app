package com.shoe.controller;

import com.shoe.dto.OrderDTO;
import com.shoe.entity.Order;
import com.shoe.exception.DataNotFound;
import com.shoe.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) throws DataNotFound {
        if(bindingResult.hasErrors()){
            List<String> error = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping("/revenue")
    public ResponseEntity<Integer> getTotalMoney(@RequestParam int year, @RequestParam int month,@RequestHeader("Authorization") String auToken){
        return ResponseEntity.ok(orderService.getTotalMoney(year, month));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getByID(@PathVariable int id) throws DataNotFound {
        return ResponseEntity.ok(orderService.getByID(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable int id, @Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) throws DataNotFound {
        if(bindingResult.hasErrors()){
            List<String> error = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(orderService.updateOrder(id, orderDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) throws DataNotFound{
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Sucss");
    }

}
