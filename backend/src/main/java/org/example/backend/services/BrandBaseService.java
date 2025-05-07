package org.example.backend.services;

import org.example.backend.entities.BrandBase;
import org.example.backend.repositories.BrandBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandBaseService {
    @Autowired
    private BrandBaseRepository BrandBaseRepository;

    public List<BrandBase> getAllBrands() {
        return BrandBaseRepository.findAllByOrderByCreatedOnAscIdAsc();
    }
}
