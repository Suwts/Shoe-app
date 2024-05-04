package com.shoe.service;

import com.shoe.dto.CatetoryDTO;
import com.shoe.entity.Catetory;

import java.util.List;

public interface CatetoryService {
    List<Catetory> getAllCatetories();
    Catetory createCatetory(CatetoryDTO catetoryDTO);
    Catetory updateCatetory(int id, CatetoryDTO catetoryDTO);
    void deleteCatetory(int id);
}
