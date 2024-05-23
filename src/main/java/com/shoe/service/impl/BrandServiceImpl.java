package com.shoe.service.impl;

import com.shoe.Utils.Language;
import com.shoe.dto.BrandDTO;
import com.shoe.entity.Brand;
import com.shoe.reponsitory.BrandRepo;
import com.shoe.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private Language language;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepo.findAll();
    }

    @Override
    public Brand createBrand(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        return brandRepo.save(brand);
    }

    @Override
    public Brand updateBrand(int id, BrandDTO brandDTO) {
        Brand brand = brandRepo.findByBrandID(id).orElseThrow(() -> new RuntimeException(language.getLocale("data.not.found")));
        brand.setName(brandDTO.getName());
        return brandRepo.save(brand);
    }

    @Override
    public void deleteBrand(int id) {
        brandRepo.deleteById(id);
    }

    @Override
    public BrandDTO getBrandById(int id) {
        return brandRepo.getBrandName(id);
    }
}
