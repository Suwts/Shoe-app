package com.shoe.reponsitory;

import com.shoe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderID(int id);

    @Query("select sum(o.totalMoney) from Order o where YEAR(o.createtime) = :year AND MONTH(o.createtime) = :month AND o.active =1")
    String sumTotalMoney(@Param("year") int year, @Param("month") int month);
}
