package org.example.backend.repositories;

import org.example.backend.entities.BrandBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandBaseRepository extends JpaRepository<BrandBase, Integer> {
}