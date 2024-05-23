package com.shoe.controller;

import com.shoe.dto.CatetoryDTO;
import com.shoe.entity.Catetory;
import com.shoe.service.CatetoryService;
import com.shoe.service.impl.CatetoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/catetory")
public class CatetoryController {

    @Autowired
    private CatetoryService catetoryService;

    @GetMapping("/get")
    public ResponseEntity<List<Catetory>> getAllCatetory(){
        List<Catetory> catetories = catetoryService.getAllCatetories();
        return ResponseEntity.ok(catetories);
    }

    @GetMapping("/getById")
    public ResponseEntity<CatetoryDTO> getCatetory(@RequestParam int id){
        return ResponseEntity.ok(catetoryService.getCatetoryById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCatetory(@Valid @RequestBody CatetoryDTO catetoryDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> error = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(error);
        }
        catetoryService.createCatetory(catetoryDTO);
        return ResponseEntity.ok("Successfull" + catetoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCatetory(@PathVariable int id, @Valid @RequestBody CatetoryDTO catetoryDTO){
        catetoryService.updateCatetory(id, catetoryDTO);
        return ResponseEntity.ok("Successfull");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCatetory(@PathVariable int id){
        catetoryService.deleteCatetory(id);
        return ResponseEntity.ok("Successfull");
    }
}
