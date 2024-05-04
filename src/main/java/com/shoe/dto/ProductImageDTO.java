package com.shoe.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    private int product_id;
    private List<MultipartFile> files;
}
