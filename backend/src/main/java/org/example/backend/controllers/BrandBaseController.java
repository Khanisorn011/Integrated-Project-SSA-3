package org.example.backend.controllers;

import org.example.backend.dtos.*;
import org.example.backend.entities.BrandBase;
import org.example.backend.entities.SaleItemBase;
import org.example.backend.services.BrandBaseService;
import org.example.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @PostMapping("/v1/brands")
    public ResponseEntity<BrandDetailDTO> createBrand(@RequestBody BrandBaseCreateDTO dto) {
        try {
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            if (brandBaseService.existsByNameIgnoreCase(dto.getName().trim())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            BrandBase brandBase = modelMapper.map(dto, BrandBase.class);
            BrandBase saved = brandBaseService.createBrand(brandBase);
            BrandDetailDTO responseDto = modelMapper.map(saved, BrandDetailDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
