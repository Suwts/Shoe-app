package com.shoe.service.impl;

import com.shoe.Utils.Containts;
import com.shoe.Utils.Language;
import com.shoe.config.PaymentsConfig;
import com.shoe.dto.OrderDetailDTO;
import com.shoe.entity.Order;
import com.shoe.entity.Product;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.OrderDetailRepo;
import com.shoe.reponsitory.OrderRepo;
import com.shoe.reponsitory.ProductRepo;
import com.shoe.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private Language language;

    @Override
    public void paymentSuccess(String orderID) throws DataNotFound {
        int orderId =  Integer.parseInt(orderID);
        Order order =orderRepo.findByOrderID(orderId).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        order.setActive(1);
        order.setStatus(Containts.PENDING);
        orderRepo.save(order);

        Product product;
        List<Integer> productIDs = orderDetailRepo.getProductIDs(orderId);
        for(int i :  productIDs){
            product = productRepo.findByProductID(i).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
            product.setNumber_buy(product.getNumber_buy() + 1);
            productRepo.save(product);
        }
    }

    @Override
    public String getPay(int orderID) throws DataNotFound, UnsupportedEncodingException {
        Order order =orderRepo.findByOrderID(orderID).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_IpAddr = "127.0.0.1";
        String bankCode = "NCB";
        String locate = "vn";
        String vnp_TxnRef = PaymentsConfig.getRandomNumber(8);
        String vnp_TmnCode = PaymentsConfig.vnp_TmnCode;
        int amount = order.getTotalMoney() * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", PaymentsConfig.vnp_ReturnUrl+"?orderID="+orderID);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentsConfig.hmacSHA512(PaymentsConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentsConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }
}
