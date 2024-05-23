package com.shoe.reponsitory;

import com.shoe.dto.CatetoryDTO;
import com.shoe.entity.Catetory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CatetoryRepo extends JpaRepository<Catetory, Integer> {
    Optional<Catetory> findByCatetoryID(int id);

    @Query("select new com.shoe.dto.CatetoryDTO(c.name) from Catetory c inner join Product p on c.catetoryID = p.catetory_id where p.productID = :id")
    CatetoryDTO getCatetoryName(@Param("id") int id);
}
