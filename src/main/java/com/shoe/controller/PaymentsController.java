package com.shoe.controller;

import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.OrderRepo;
import com.shoe.service.OrderService;
import com.shoe.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/api/pay")
public class PaymentsController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/get/{orderID}")
    public ResponseEntity<String> getPay(@PathVariable("orderID") int orderID) throws UnsupportedEncodingException, DataNotFound {
        return ResponseEntity.ok(paymentService.getPay(orderID));
    }

    @GetMapping("/call-back")
    public void paymentCallBack(@RequestParam Map<String,String > queryParams, HttpServletResponse response) throws DataNotFound, IOException {
        boolean checkAmount = true; // vnp_Amount is valid
        boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        String orderID = queryParams.get("orderID");
        if(checkAmount)
        {
            if(orderID != null && !orderID.equals(""))
            {
                if ("00".equals(vnp_ResponseCode))
                {
                    paymentService.paymentSuccess(orderID);
                    response.sendRedirect("http://localhost:4200/success");
                }
                }
                else
                {
                    response.sendRedirect("http://localhost:4200/unsucessfull");
                }
            }
    }
}
