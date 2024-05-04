package com.shoe.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productimageID;

    @Column(name = "product_id")
    private int product_id;

    @Column(name = "image")
    private String image;

    @Column(name = "createtime")
    private LocalDateTime createtime;

    @Column(name = "updatetime")
    private LocalDateTime updatetime;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    public int getProductImageID() {
        return productimageID;
    }

    public void setProductImageID(int productImageID) {
        this.productimageID = productImageID;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
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
