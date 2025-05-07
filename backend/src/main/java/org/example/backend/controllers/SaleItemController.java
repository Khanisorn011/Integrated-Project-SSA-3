package org.example.backend.controllers;

import org.example.backend.dtos.SaleItemDTO;
import org.example.backend.dtos.SaleItemDetailDTO;
import org.example.backend.entities.SaleItemBase;
import org.example.backend.services.BrandBaseService;
import org.example.backend.services.SaleItemService;
import org.example.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itb-mshop")
@CrossOrigin(origins = "http://localhost:5173")

public class SaleItemController {
    @Autowired
    private SaleItemService saleItemService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private BrandBaseService brandBaseService;

    @GetMapping("/v1/sale-items")
    public ResponseEntity<List<SaleItemDTO>> getSalesItems() {
        List<SaleItemBase> items = saleItemService.getAllByCreateTime();
        List<SaleItemDTO> dto = listMapper.mapList(items, SaleItemDTO.class, modelMapper);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/v1/sale-items/{id}")
    public ResponseEntity<SaleItemDetailDTO> getSaleItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(modelMapper.map(saleItemService.getById(id), SaleItemDetailDTO.class));
    }
    @PostMapping("/v1/sale-items")
    public ResponseEntity<SaleItemDetailDTO> createSaleItem(@RequestBody SaleItemDetailDTO dto){
        SaleItemBase saleItemBase = new SaleItemBase();
        saleItemBase.setModel(dto.getModel());
        saleItemBase.setPrice(dto.getPrice());
        saleItemBase.setDescription(dto.getDescription());
        saleItemBase.setQuantity(dto.getQuantity());
        saleItemBase.setRamGb(dto.getRamGb());
        saleItemBase.setScreenSizeInch(dto.getScreenSizeInch());
        saleItemBase.setStorageGb(dto.getStorageGb());
        saleItemBase.setColor(dto.getColor());
        saleItemBase.setBrand(brandBaseService.getBrandByName(dto.getBrandName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
