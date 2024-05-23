package com.shoe.reponsitory;

import com.shoe.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {
}
