package org.example.backend.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.backend.entities.SaleItemBase;
import org.example.backend.exceptions.SaleItemNotFoundException;
import org.example.backend.repositories.SaleItemBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleItemService {
    @Autowired
    private SaleItemBaseRepository saleItemBaseRepository;

    @PersistenceContext
    private EntityManager em;

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

    @Transactional
    public SaleItemBase updateSaleItem(SaleItemBase saleItemBase) {
        SaleItemBase saved = saleItemBaseRepository.save(saleItemBase);
        em.refresh(saved);
        return saved;
    }


    public void deleteById(int id) {
        SaleItemBase saleItem = getById(id);
        saleItemBaseRepository.delete(saleItem);
    }
}
