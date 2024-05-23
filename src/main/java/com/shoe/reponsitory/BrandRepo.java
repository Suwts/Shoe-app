package com.shoe.reponsitory;

import com.shoe.dto.BrandDTO;
import com.shoe.dto.CatetoryDTO;
import com.shoe.entity.Brand;
import com.shoe.entity.Catetory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByBrandID(int id);
    @Query("select new com.shoe.dto.BrandDTO(c.name) from Brand c inner join Product p on c.brandID = p.brand_id where p.productID = :id")
    BrandDTO getBrandName(@Param("id") int id);
}
