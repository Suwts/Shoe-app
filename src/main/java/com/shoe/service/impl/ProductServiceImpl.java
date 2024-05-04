package com.shoe.service.impl;

import com.github.javafaker.Faker;
import com.shoe.Utils.FileUtils;
import com.shoe.Utils.Language;
import com.shoe.dto.ProductDTO;
import com.shoe.dto.ProductImageDTO;
import com.shoe.entity.Brand;
import com.shoe.entity.Catetory;
import com.shoe.entity.Product;
import com.shoe.entity.ProductImage;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.BrandRepo;
import com.shoe.reponsitory.CatetoryRepo;
import com.shoe.reponsitory.ProductImageRepo;
import com.shoe.reponsitory.ProductRepo;
import com.shoe.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CatetoryRepo catetoryRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ProductImageRepo productImageRepo;

    @Autowired
    private Language language;

    @Override
    public Page<Product> getProducts(String keywords, PageRequest pageRequest) {
        if(keywords == null || keywords == ""){
            return productRepo.findAll(pageRequest);
        }
        return productRepo.searchProduct(keywords, pageRequest);
    }

    @Override
    public List<Product> getALlProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepo.findByProductID(id).orElseThrow(()
                -> new RuntimeException("Product not found"));
    }

    @Override
    public Product getProductByName(String name) {
        return productRepo.findByName(name).orElseThrow(()
                -> new RuntimeException("Product not found"));
    }

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFound {
        Optional<Catetory> catetory = catetoryRepo.findByCatetoryID(productDTO.getCatetory_id());
        Optional<Brand> brand = brandRepo.findByBrandID(productDTO.getBrand_id());

        if(!catetory.isPresent() || !brand.isPresent()){
            throw new DataNotFound(language.getLocale("data.not.found"));
        }
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setCatetoryID(productDTO.getCatetory_id());
        product.setBrandID(productDTO.getBrand_id());
        return productRepo.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(int id, ProductDTO productDTO) {
        Product product = productRepo.findByProductID(id).orElseThrow();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setDescription(productDTO.getDescription());
//        product.setImage(productDTO.getImage());
        product.setCatetoryID(productDTO.getCatetory_id());
        product.setBrandID(productDTO.getBrand_id());
        Optional<Catetory> catetory = catetoryRepo.findByCatetoryID(productDTO.getCatetory_id());
        Optional<Brand> brand = brandRepo.findByBrandID(productDTO.getBrand_id());
        if(!catetory.isPresent() || !brand.isPresent()){
            try {
                throw new DataNotFound(language.getLocale("data.not.found"));
            } catch (DataNotFound dataNotFound) {
                dataNotFound.printStackTrace();
            }
        }
        return productRepo.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Override
    @Transactional
    public List<ProductImage> createImage(ProductImageDTO productImageDTO) throws IOException, DataNotFound {
        List<MultipartFile> files = productImageDTO.getFiles();
        files = files == null ? new ArrayList<MultipartFile>() : files;
        List<ProductImage> productImageList = new ArrayList<>();
        if(files.size() < 5){
            for(MultipartFile file: files){

                if(file.getSize() == 0){
                    continue;
                }
                if(file.getSize() < (10 * 1024 * 1024)){
                    String contentType = file.getContentType();
                    if(contentType != null && contentType.startsWith("image/")){
                        Product product = productRepo.findByProductID(productImageDTO.getProduct_id()).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
                        String fileName = FileUtils.StoreFile(file);
                        ProductImage productImage = new ProductImage();
                        productImage.setProduct_id(product.getProductID());
                        productImage.setImage(fileName);
                        productImageRepo.save(productImage);
                        productImageList.add(productImage);
                    }
                }


            }
            return productImageList;
        }
        return null;
    }
    @Override
    public boolean existsByName(String name){
        return productRepo.existsByName(name);
    }

    @Override
    @Transactional
    public List<Product> generateProduct() {
        Faker faker = new Faker();
        for(int i = 0; i< 10; i++){
            Product product = new Product();
            String name = faker.commerce().productName();
            if(existsByName(name)){
                continue;
            }
            product.setName(name);
            product.setPrice(faker.number().numberBetween(200000, 10000000));
            product.setDiscount(faker.number().numberBetween(0, 60));
            product.setDescription(faker.lorem().sentence());
//        product.setImage(productDTO.getImage());
            product.setCatetoryID(faker.number().numberBetween(1,4));
            product.setBrandID(faker.number().numberBetween(1,6));
            productRepo.save(product);
        }
        return null;
    }

    @Override
    public List<Product> getProductByIDs(String ids) {
        List<Integer> id = Arrays.stream(ids.split(","))
                .map(Integer :: parseInt)
                .collect(Collectors.toList());
        return productRepo.findProductByIds(id);
    }
}
