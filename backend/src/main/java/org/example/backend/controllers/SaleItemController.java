package org.example.backend.controllers;

import org.example.backend.dtos.SaleItemDTO;
import org.example.backend.entities.SaleItemBase;
import org.example.backend.services.SaleItemService;
import org.example.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/sales-items")
public class SaleItemController {
    @Autowired
    private SaleItemService saleItemService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    @GetMapping("")
    public ResponseEntity<List<SaleItemDTO>> getSalesItems() {
        List<SaleItemBase> items = saleItemService.getAllByCreateTime();
        List<SaleItemDTO> dto = listMapper.mapList(items, SaleItemDTO.class, modelMapper);

        return ResponseEntity.ok(dto);
    }
}
