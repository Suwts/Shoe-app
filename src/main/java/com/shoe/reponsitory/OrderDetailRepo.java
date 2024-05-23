package com.shoe.reponsitory;

import com.shoe.dto.OrderDetailDTO;
import com.shoe.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
    @Query("select productID from OrderDetail where orderID = :orderID")
    List<Integer> getProductIDs(@Param("orderID") int orderID);
}
