package com.shoe.reponsitory;

import com.shoe.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {
}
