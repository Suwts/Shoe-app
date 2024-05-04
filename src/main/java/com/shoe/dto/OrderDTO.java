package com.shoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
