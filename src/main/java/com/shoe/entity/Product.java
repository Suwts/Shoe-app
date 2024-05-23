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
    private int price;

    @Column(name = "discount")
    private int discount;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "catetory_id")
    private int catetory_id;

    @Column(name = "brand_id")
    private int brand_id;

    @Column(name = "createtime")
    private LocalDateTime createtime;

    @Column(name = "updatetime")
    private LocalDateTime updatetime;

    @Column(name = "size")
    private int size;

    @Column(name = "number_input")
    private int number_input;

    @Column(name = "number_buy")
    private int number_buy;

    @Column(name = "active")
    private int active;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getCatetory_id() {
        return catetory_id;
    }

    public void setCatetory_id(int catetory_id) {
        this.catetory_id = catetory_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getNumber_input() {
        return number_input;
    }

    public void setNumber_input(int number_input) {
        this.number_input = number_input;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public int getNumber_buy() {
        return number_buy;
    }

    public void setNumber_buy(int number_buy) {
        this.number_buy = number_buy;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}

