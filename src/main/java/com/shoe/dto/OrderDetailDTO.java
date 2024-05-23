package com.shoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private int order_id;
    private int product_id;
    private int quantity;
    private int money;

    public OrderDetailDTO(int product_id){
        this.product_id = product_id;
    }
}
