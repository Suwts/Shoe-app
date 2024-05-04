package com.shoe.reponsitory;

import com.shoe.entity.Catetory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatetoryRepo extends JpaRepository<Catetory, Integer> {
    Optional<Catetory> findByCatetoryID(int id);
}
