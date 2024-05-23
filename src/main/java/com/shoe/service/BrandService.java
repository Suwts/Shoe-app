package com.shoe.service;

import com.shoe.dto.BrandDTO;
import com.shoe.entity.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand createBrand(BrandDTO brandDTO);
    Brand updateBrand(int id, BrandDTO brandDTO);
    void deleteBrand(int id);
    BrandDTO getBrandById(int id);
}
