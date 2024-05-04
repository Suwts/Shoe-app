package com.shoe.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    @NotEmpty(message = "Name in brand can not empty")
    private String name;
}
