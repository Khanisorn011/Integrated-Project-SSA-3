package org.example.backend.repositories;

import org.example.backend.entities.SaleItemBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemBaseRepository extends JpaRepository<SaleItemBase, Integer> {
    List<SaleItemBase> findAllByOrderByCreatedOnAsc();
}