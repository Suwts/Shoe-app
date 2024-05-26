package com.shoe.controller;

import com.github.javafaker.Faker;
import com.shoe.dto.ProductDTO;
import com.shoe.dto.ProductImageDTO;
import com.shoe.entity.Product;
import com.shoe.entity.ProductImage;
import com.shoe.exception.DataNotFound;
import com.shoe.response.ProductPageRespone;
import com.shoe.service.ProductService;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/getAll")
    public ResponseEntity<ProductPageRespone> getAllProducts(
            @RequestParam(defaultValue = "") String keywords,
            @RequestParam int page , @RequestParam int limit){
        PageRequest pageRequest = PageRequest.of(page-1, limit, Sort.by("productID").descending());
        Page<Product> products = productService.getProducts(keywords,pageRequest);
        int totalPage = products.getTotalPages();
        List<Product> productList = products.getContent();
        return ResponseEntity.ok(new ProductPageRespone(productList, totalPage));
    }
    @GetMapping("/gets")
    public ResponseEntity<ProductPageRespone> getAdminProducts(
            @RequestParam int page , @RequestParam int limit){
        PageRequest pageRequest = PageRequest.of(page-1, limit, Sort.by("productID").descending());
        Page<Product> products = productService.getAdminProducts(pageRequest);
        int totalPage = products.getTotalPages();
        List<Product> productList = products.getContent();
        return ResponseEntity.ok(new ProductPageRespone(productList, totalPage));
    }

    @GetMapping("/get/catetory")
    public ResponseEntity<Product> getProductByCatetory(int catetoryID){
        return ResponseEntity.ok(null);
    }


    @GetMapping("/get")
    public ResponseEntity<Product> getProductsById(@RequestParam int id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

//    @GetMapping("/get")
//    public ResponseEntity<Product> getProductByName(@RequestParam String name){
//        return ResponseEntity.ok(productService.getProductByName(name));
//    }

    @GetMapping("/get_ids")
    public ResponseEntity<List<Product>> getProductByIds(@RequestParam String ids){
        return ResponseEntity.ok(productService.getProductByIDs(ids));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult,@RequestHeader("Authorization") String auToken) throws DataNotFound {
        if(bindingResult.hasErrors()){
            List<String> error = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }
    @PostMapping(value = "/uploads/{product_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProductImage>> createProductImage(@PathVariable int product_id, @ModelAttribute ProductImageDTO productImageDTO) throws Exception {
//        ProductImageDTO productImageDTO = new ProductImageDTO(productID, files);
        List<ProductImage> productImageList = productService.createImage(product_id, productImageDTO);
        return ResponseEntity.ok(productImageList);
    }

    @GetMapping("/images/{images}")
    public ResponseEntity<?> getImages(@PathVariable String images) {
        try{
            Path path = Paths.get("src/main/resources/static/uploads/" + images);
            UrlResource urlResource = new UrlResource(path.toUri());
            if(urlResource.exists()){
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(urlResource);
            }else{
                return  ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @Valid @RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String auToken){
        return ResponseEntity.ok(productService.updateProduct(id, productDTO, auToken));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

//    @PostMapping("/faker")
//    public ResponseEntity<List<Product>> generateProduct(){
//        return ResponseEntity.ok(productService.generateProduct());
//    }
}
