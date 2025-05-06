package org.example.backend.controllers;

import org.example.backend.dtos.BrandBaseDTO;
import org.example.backend.entities.BrandBase;
import org.example.backend.services.BrandBaseService;
import org.example.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itb-mshop")
@CrossOrigin(origins = "http://localhost:5173")

public class BrandBaseController {
    @Autowired
    private BrandBaseService brandBaseService;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/v1/brands")
    public ResponseEntity<List<BrandBaseDTO>> getAllBrands() {
        List<BrandBase> brands = brandBaseService.getAllBrands();
        List<BrandBaseDTO> brandsDTO = listMapper.mapList(brands , BrandBaseDTO.class, modelMapper);
        return ResponseEntity.ok(brandsDTO);
    }
}
