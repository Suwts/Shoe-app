package com.shoe.service;

import com.shoe.dto.ProductDTO;
import com.shoe.dto.ProductImageDTO;
import com.shoe.entity.Product;
import com.shoe.entity.ProductImage;
import com.shoe.exception.DataNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<Product> getProducts(String keywords, PageRequest pageRequest);
    Page<Product> getAdminProducts(PageRequest pageRequest);
    List<Product> getALlProducts();
    Product getProductById(int id);
    Product getProductByName(String name);
    Product createProduct(ProductDTO productDTO) throws DataNotFound;
    Product updateProduct(int id, ProductDTO productDTO, String auToken);
    Product deleteProduct(int id);
    List<ProductImage> createImage(int product_id, ProductImageDTO productImageDTO) throws Exception;
    boolean existsByName(String name);
    List<Product> generateProduct();
    List<Product> getProductByIDs(String ids);
}
