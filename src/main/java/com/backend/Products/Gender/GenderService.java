package com.backend.Products.Gender;

import org.springframework.stereotype.Service;

@Service
public class GenderService {

    private final GenderRepo repo;

    public GenderService(GenderRepo repo) {
        this.repo = repo;
    }
}
