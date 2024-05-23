package com.shoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @NotEmpty(message = "Name not empty")
    @JsonProperty(value = "full_name")
    private String fullName;

    @NotEmpty(message = "Address not empty")
    private String address;

    @NotEmpty(message = "Phone number not empty")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String note;

    @NotEmpty(message = "Payment not empty")
    private String payment;

    @NotEmpty(message = "Shipping method not empty")
    private String shipping_method;

    @NotNull(message = "Total money in product can not empty")
    @Min(0)
    private int total_money;

    private List<CartDTO> cart_item;
}
