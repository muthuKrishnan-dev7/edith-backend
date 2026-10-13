package com.backend.Products.Gender;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gender")
public class GenderController {

    private final GenderService serv;

    public GenderController(GenderService serv) {
        this.serv = serv;
    }

//    @GetMapping("/get-gender")
//    public ResponseEntity<> getGender(){
//
//    }

}
