package com.shoe.controller;

import com.shoe.dto.BrandDTO;
import com.shoe.dto.CatetoryDTO;
import com.shoe.entity.Brand;
import com.shoe.service.BrandService;
import com.shoe.service.impl.BrandServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/get")
    public ResponseEntity<List<Brand>> getAllBrand(){
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/getById")
    public ResponseEntity<BrandDTO> getBrand(@RequestParam int id){
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandDTO brandDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> error = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(error);
        }
        brandService.createBrand(brandDTO);
        return ResponseEntity.ok("Successfull");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBrand(@PathVariable int id, @Valid @RequestBody BrandDTO brandDTO){
        brandService.updateBrand(id, brandDTO);
        return ResponseEntity.ok("Successfull");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable int id){
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Successfull");
    }
}
