package com.shoe.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private int productID;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "discount")
    private int discount;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "catetory_id")
    private int catetoryID;

    @Column(name = "brand_id")
    private int brandID;

    @Column(name = "createtime")
    private LocalDateTime createtime;

    @Column(name = "updatetime")
    private LocalDateTime updatetime;

//    @ManyToOne
//    @JoinColumn(name = "catetory_id", insertable=false, updatable=false)
//    private Catetory catetory;
//
//    @ManyToOne
//    @JoinColumn(name = "brand_id", insertable=false, updatable=false)
//    private Brand brand;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<ProductImage> productImageList;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCatetoryID() {
        return catetoryID;
    }

    public void setCatetoryID(int catetoryID) {
        this.catetoryID = catetoryID;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

//    public Catetory getCatetory() {
//        return catetory;
//    }
//
//    public void setCatetory(Catetory catetory) {
//        this.catetory = catetory;
//    }
//
//    public Brand getBrand() {
//        return brand;
//    }
//
//    public void setBrand(Brand brand) {
//        this.brand = brand;
//    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }
}

