package com.shoe.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotEmpty(message = "Name in product can not empty")
    private String name;

    @NotNull(message = "Price in product can not empty")
    @Min(0)
    private float price;

    @Min(0)
    @Max(100)
    private int discount;

    @NotEmpty(message = "Description in product can not empty")
    private String description;

//    @NotNull(message = "Image in product can not empty")
    private String image;

    @NotNull(message = "Catetory id in product can not empty")
    private int catetory_id;

    @NotNull(message = "Brand id in product can not empty")
    private int brand_id;

    private List<MultipartFile> file;
}
