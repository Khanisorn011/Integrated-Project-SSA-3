package org.example.backend.services;

import org.example.backend.entities.SaleItemBase;
import org.example.backend.exceptions.SaleItemNotFoundException;
import org.example.backend.repositories.SaleItemBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SaleItemService {
    @Autowired
    private SaleItemBaseRepository saleItemBaseRepository;

    public List<SaleItemBase> getAll() {
        return saleItemBaseRepository.findAll();
    }

    public List<SaleItemBase> getAllByCreateTime() {
        return saleItemBaseRepository.findAllByOrderByCreatedOnAscIdAsc();
    }

    public SaleItemBase getById(int id) {
        return saleItemBaseRepository.findById(id).orElseThrow(
                () -> new SaleItemNotFoundException("SaleItem not found for this id :: " + id)
        );
    }

    public SaleItemBase createSaleItem(SaleItemBase saleItemBase){
        return saleItemBaseRepository.save(saleItemBase);
    }

    public SaleItemBase updateSaleItem(SaleItemBase saleItemBase) {
        SaleItemBase saved = saleItemBaseRepository.save(saleItemBase);
        return saved;
    }


    public void deleteById(int id) {
        SaleItemBase saleItem = getById(id);
        saleItemBaseRepository.delete(saleItem);
    }
}
