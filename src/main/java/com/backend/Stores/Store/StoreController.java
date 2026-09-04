package com.backend.Stores.Store;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Stores.Store.DTOs.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StoreController {

    private final StoreService serv;

    public StoreController(StoreService serv) {
        this.serv = serv;
    }

    //    ===== Create Store =====
    @PostMapping
    public ResponseEntity<ApiResponseMsg> createStore(@RequestBody StoreRequest store_request, StoreDetailsRequest details_request, StoreProfileRequest profile_request) {
        ApiResponseMsg response = serv.createStore(store_request, details_request, profile_request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


//    ===== Delete Store =====

}
