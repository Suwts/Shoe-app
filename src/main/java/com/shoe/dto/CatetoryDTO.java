package com.shoe.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatetoryDTO {

    @NotEmpty(message = "Name in catetory can not null")
    private String name;
}
