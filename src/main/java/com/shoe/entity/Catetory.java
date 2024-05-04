package com.shoe.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "catetory")
public class Catetory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int catetoryID;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "catetory", cascade = CascadeType.ALL)
//    private List<Product> productList;

    @Column(name = "createtime")
    private LocalDateTime createtime;

    @Column(name = "updatetime")
    private LocalDateTime updatetime;


    public int getCatetoryID() {
        return catetoryID;
    }

    public void setCatetoryID(int catetoryID) {
        this.catetoryID = catetoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
