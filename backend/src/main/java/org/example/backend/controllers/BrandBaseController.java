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

    @GetMapping("/v1/brands/{id}")
    public ResponseEntity<BrandDetailDTO> getBrandById(@PathVariable Integer id) {
        try {
            BrandBase brand = brandBaseService.getById(id);
            if (brand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            BrandDetailDTO responseDto = modelMapper.map(brand, BrandDetailDTO.class);
            responseDto.setName(brand.getName());
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    @PutMapping("/v1/brands/{id}")
    public ResponseEntity<BrandDetailDTO> updateBrand(@PathVariable Integer id, @RequestBody BrandBaseEditDTO dto) {
        try {
            BrandBase existingItem = brandBaseService.getById(id);
            if (existingItem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String trimmedName = dto.getName().trim();
            if (!existingItem.getName().equalsIgnoreCase(trimmedName) &&
                    brandBaseService.existsByNameIgnoreCase(trimmedName)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            modelMapper.map(dto, existingItem);
            existingItem.setId(id);
            BrandBase updated = brandBaseService.updateBrand(existingItem);
            BrandDetailDTO responseDto = modelMapper.map(updated, BrandDetailDTO.class);
            responseDto.setName(updated.getName());
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            if (e.getMessage().contains("Brand not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
