package com.shoe.service;

import com.shoe.exception.DataNotFound;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    void paymentSuccess(String orderID) throws DataNotFound;
    String getPay(int orderID) throws DataNotFound, UnsupportedEncodingException;
}
