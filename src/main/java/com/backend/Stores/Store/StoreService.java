package com.backend.Stores.Store;

import com.backend.Exception.Custom.UserNotFoundException;
import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Stores.Store.DTOs.*;
import com.backend.Stores.StoreDetails.StoreDetailsEntity;
import com.backend.Stores.StoreDetails.StoreDetailsRepo;
import com.backend.Stores.StoreProfile.StoreProfileEntity;
import com.backend.Stores.StoreProfile.StoreProfileRepo;
import com.backend.Stores.StoreType.StoreTypeEntity;
import com.backend.Stores.StoreType.StoreTypeRepo;
import com.backend.Stores.Templates.TemplateRepo;
import com.backend.Users.User.UserEntity;
import com.backend.Users.User.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StoreService {

    private static final String DEFAULT_ROLE = "STORE_APPLICANT";

    private final StoreRepo repo;
    private final StoreTypeRepo storeTypeRepo;
    private final UserRepo userRepo;

    public StoreService(StoreRepo repo, StoreTypeRepo storeTypeRepo, TemplateRepo templateRepo, UserRepo userRepo, StoreDetailsRepo detailsRepo, StoreProfileRepo profileRepo) {
        this.repo = repo;
        this.storeTypeRepo = storeTypeRepo;
        this.userRepo = userRepo;
    }

    //    ===== Create Store =====
    @Transactional
    public ApiResponseMsg createStore(RequestCreateStore request) {

        Long userId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        UserEntity applicant = userRepo.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (!"STORE_APPLICANT".equals(applicant.getRole().getRole())) {
            throw new RuntimeException("User is not a store applicant");
        }

        StoreEntity store = new StoreEntity();
        store.setStoreDomain(request.storeDomain());
        store.setUser(applicant);
        StoreTypeEntity storeType = storeTypeRepo.findById(request.storeType())
                .orElseThrow(()->new RuntimeException("Store Type not exist"));
        store.setStoreType(storeType);
        store.setStartAt(LocalDate.now());
        store.setEndAt(LocalDate.now().plusDays(30));
        store.setActive(true);
        repo.save(store);
        String storeCode = String.format(
                "ST%03d",
                store.getId()
        );
        store.setStoreCode(storeCode);

        return new ApiResponseMsg(
                "Store created successfully"
        );
    }

}
