package com.backend.Stores.Store;

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

@Service
public class StoreService {

    private final StoreRepo repo;
    private final StoreTypeRepo storeTypeRepo;
    private final TemplateRepo templateRepo;
    private final UserRepo userRepo;
    private final StoreDetailsRepo detailsRepo;
    private final StoreProfileRepo profileRepo;

    public StoreService(StoreRepo repo, StoreTypeRepo storeTypeRepo, TemplateRepo templateRepo, UserRepo userRepo, StoreDetailsRepo detailsRepo, StoreProfileRepo profileRepo) {
        this.repo = repo;
        this.storeTypeRepo = storeTypeRepo;
        this.templateRepo = templateRepo;
        this.userRepo = userRepo;
        this.detailsRepo = detailsRepo;
        this.profileRepo = profileRepo;
    }

    //    ===== Create Store =====
    @Transactional
    public ApiResponseMsg createStore(CreateStoreRequest request) {

//      ============================== Get Authenticated User ==============================

        Long userId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        UserEntity applicant = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!"STORE_APPLICANT".equals(applicant.getRole().getRole())) {
            throw new RuntimeException("User is not a store applicant");
        }

//      ============================== Store Creation ==============================

        StoreEntity store = new StoreEntity();
        store.setStoreDomain(request.store().storeDomain());
        store.setStoreOwner(applicant);
        StoreTypeEntity storeType = storeTypeRepo.findById(request.store().storeType())
                .orElseThrow(()->new RuntimeException("Store Type not exist"));
        store.setStoreType(storeType);
        store.setActive(true);
        repo.save(store);
        String storeCode = String.format(
                "ST%03d",
                store.getId()
        );
        store.setStoreCode(storeCode);

//      ============================== Store Details Creation ==============================

        StoreDetailsRequest detailsRequest = request.details();
        StoreDetailsEntity details = new StoreDetailsEntity();

        details.setStore(store);
        details.setEmail(detailsRequest.email());
        details.setPhone(detailsRequest.phone());
        details.setWhatsapp(detailsRequest.whatsapp());
        details.setAddressLine1(detailsRequest.addressLine1());
        details.setAddressLine2(detailsRequest.addressLine2());
        details.setCity(detailsRequest.city());
        details.setDistrict(detailsRequest.district());
        details.setPin_code(detailsRequest.pin_code());
        details.setState(detailsRequest.state());
        details.setCountry(detailsRequest.country());
        details.setGst(detailsRequest.gst());
        details.setPan(detailsRequest.pan());
        details.setPaymentAPI(detailsRequest.paymentAPI());

        detailsRepo.save(details);

//      ============================== Store Profile Creation ==============================

        StoreProfileRequest profileRequest = request.profile();
        StoreProfileEntity profile = new StoreProfileEntity();
        profile.setStore(store);

        profile.setStoreName(profileRequest.storeName());
        profile.setLogoUrl(profileRequest.logoUrl());
        profile.setFaviconUrl(profileRequest.faviconUrl());
        profile.setPrimaryColor(profileRequest.primaryColor());
        profile.setSecondaryColor(profileRequest.secondaryColor());
        profile.setAccentColor(profileRequest.accentColor());
        profile.setMetaTitle(profileRequest.metaTitle());
        profile.setMetaDescription(profileRequest.metaDescription());
        profile.setOgImageUrl(profileRequest.ogImageUrl());
        profile.setInstagramUrl(profileRequest.instagramUrl());
        profile.setWhatsappUrl(profileRequest.whatsappUrl());
        profile.setFacebookUrl(profileRequest.facebookUrl());
        profile.setYoutubeUrl(profileRequest.youtubeUrl());

        profileRepo.save(profile);

        return new ApiResponseMsg(
                "Store created successfully"
        );
    }

}
