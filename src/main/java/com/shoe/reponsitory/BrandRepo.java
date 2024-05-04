package com.shoe.reponsitory;

import com.shoe.entity.Brand;
import com.shoe.entity.Catetory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByBrandID(int id);
}
