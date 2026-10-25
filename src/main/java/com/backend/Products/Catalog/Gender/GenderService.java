package com.backend.Products.Catalog.Gender;

import org.springframework.stereotype.Service;

@Service
public class GenderService {

    private final GenderRepo repo;

    public GenderService(GenderRepo repo) {
        this.repo = repo;
    }
}
