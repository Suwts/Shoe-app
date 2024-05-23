package com.shoe.service.impl;

import com.github.javafaker.Faker;
import com.shoe.Utils.FileUtils;
import com.shoe.Utils.Language;
import com.shoe.dto.ProductDTO;
import com.shoe.dto.ProductImageDTO;
import com.shoe.entity.*;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.*;
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
    private CommentRepo commentRepo;

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
        if(id == 0){
            return null;
        }
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
        product.setSize(productDTO.getSize());
        product.setNumber_buy(productDTO.getNumber_buy());
        product.setNumber_input(productDTO.getNumber_input());
        product.setImage(productDTO.getImage());
        product.setCatetory_id(productDTO.getCatetory_id());
        product.setBrand_id(productDTO.getBrand_id());
        product.setActive(1);

        return productRepo.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(int id, ProductDTO productDTO, String auToken) {

        Product product = productRepo.findByProductID(id).orElseThrow();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setDescription(productDTO.getDescription());
        product.setSize(productDTO.getSize());
        product.setNumber_buy(productDTO.getNumber_buy());
        product.setNumber_input(productDTO.getNumber_input());
//        product.setImage(productDTO.getImage());
        product.setCatetory_id(productDTO.getCatetory_id());
        product.setBrand_id(productDTO.getBrand_id());;
        product.setActive(1);
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
    public Product deleteProduct(int id, String auToken) {
        String token = auToken.substring(7);
        Product product = productRepo.findByProductID(id).orElseThrow();
        product.setActive(0);
        return productRepo.save(product);
    }

    @Override
    @Transactional
    public List<ProductImage> createImage(int product_id, ProductImageDTO productImageDTO) throws IOException, DataNotFound {
        List<MultipartFile> files = productImageDTO.getFiles();
        Product product = productRepo.findByProductID(product_id).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        files = files == null ? new ArrayList<MultipartFile>() : files;
        List<ProductImage> productImageList = new ArrayList<>();
        List<String> imageList = new ArrayList<>();
        if(files.size() < 5){
            for(MultipartFile file: files){

                if(file.getSize() == 0){
                    continue;
                }
                if(file.getSize() < (10 * 1024 * 1024)){
                    String contentType = file.getContentType();
                    if(contentType != null && contentType.startsWith("image/")){
                        String fileName = FileUtils.StoreFile(file);
                        ProductImage productImage = new ProductImage();
                        productImage.setProductId(product.getProductID());
                        productImage.setImage(fileName);
                        imageList.add(fileName);
                        productImageRepo.save(productImage);
                        productImageList.add(productImage);
                    }
                }


            }
            product.setImage(imageList.get(0));
            productRepo.save(product);
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
            product.setCatetory_id(faker.number().numberBetween(1,4));
            product.setBrand_id(faker.number().numberBetween(1,6));
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
