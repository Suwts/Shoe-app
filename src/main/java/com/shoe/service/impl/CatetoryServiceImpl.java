package com.shoe.service.impl;

import com.shoe.Utils.Language;
import com.shoe.dto.CatetoryDTO;
import com.shoe.entity.Catetory;
import com.shoe.reponsitory.CatetoryRepo;
import com.shoe.service.CatetoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatetoryServiceImpl implements CatetoryService {
    @Autowired
    private CatetoryRepo catetoryRepo;

    @Autowired
    private Language language;

    @Override
    public List<Catetory> getAllCatetories() {
        return catetoryRepo.findAll();
    }

    @Override
    public Catetory createCatetory(CatetoryDTO catetoryDTO) {
        Catetory catetory = new Catetory();
        catetory.setName(catetoryDTO.getName());
        return catetoryRepo.save(catetory);
    }


    @Override
    public Catetory updateCatetory(int id, CatetoryDTO catetoryDTO) {
        Catetory catetory = catetoryRepo.findById(id).orElseThrow(() -> new RuntimeException(language.getLocale("data.not.found")));
        catetory.setName(catetoryDTO.getName());
        return catetoryRepo.save(catetory);
    }
    @Override
    public void deleteCatetory(int id) {
        catetoryRepo.deleteById(id);
    }
}
