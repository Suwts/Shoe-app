package com.shoe.reponsitory;

import com.shoe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductID(int id);
    Optional<Product> findByName(String name);
    boolean existsByName(String name);

    @Query("select p from Product p where (:keywords IS NULL OR :keywords = '' OR p.name LIKE %:keywords%)")
    Page<Product> searchProduct(@Param("keywords") String keywords, Pageable pageable);

    @Query("select p from Product p where p.productID in :productIDs")
    List<Product> findProductByIds(@Param("productIDs") List<Integer> productIDs);

//    @Query("select p from Product p inner join Catetory c where p.catetoryID = :catetoryID LIMIT 4")
//    List<Product> getProductByCatetory(@Param("catetoryID") int catetoryID);

    @Query("update Product p set p.image = :image where p.productID = :productID")
    Product updateImage(@Param("image") String image, @Param("productID") int productID);

}
