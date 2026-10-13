package com.backend.Stores.Store;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Stores.Store.DTOs.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService serv;

    public StoreController(StoreService serv) {
        this.serv = serv;
    }

//      ============================== Store Creation ==============================
    @PostMapping("/storeCreation")
    public ResponseEntity<ApiResponseMsg> createStore(@RequestBody CreateStoreRequest request) {
        ApiResponseMsg response = serv.createStore(
                request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
