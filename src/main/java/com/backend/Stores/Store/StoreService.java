package com.backend.Stores.Store;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Stores.Store.DTOs.*;
import com.backend.Stores.StoreDetails.StoreDetailsEntity;
import com.backend.Stores.StoreDetails.StoreDetailsRepo;
import com.backend.Stores.StoreProfile.StoreProfileEntity;
import com.backend.Stores.StoreProfile.StoreProfileRepo;
import com.backend.Users.RoleType;
import com.backend.Users.UserEntity;
import com.backend.Users.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepo repo;
    private final UserRepo userRepo;
    private final StoreDetailsRepo detailsRepo;
    private final StoreProfileRepo profileRepo;

    public StoreService(StoreRepo repo, UserRepo userRepo, StoreDetailsRepo detailsRepo, StoreProfileRepo profileRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.detailsRepo = detailsRepo;
        this.profileRepo = profileRepo;
    }

    //    ===== Create Store =====
    @Transactional
    public ApiResponseMsg createStore(StoreRequest store_request, StoreDetailsRequest details_request, StoreProfileRequest profile_request) {

        Long userId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UserEntity applicant = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (applicant.getRole() != RoleType.STORE_APPLICANT) {
            throw new RuntimeException("User is not a store applicant");
        }

//        ===== Store Creation =====
        StoreEntity newStore = new StoreEntity();

        newStore.setStoreDomain(store_request.storeHost());
        newStore.setStoreOwner(applicant);
        repo.save(newStore);

        String storeCode = String.format("ST%03d", newStore.getStoreId());
        newStore.setStoreCode(storeCode);
        newStore.setActive(true);
        repo.save(newStore);

//        ===== Store Details Creation =====
        StoreDetailsEntity details = new StoreDetailsEntity();

        details.setStore(newStore);
        details.setTemplate(null);
        details.setEmail(details_request.email());
        details.setPhone(details_request.phone());
        details.setWhatsapp(details_request.whatsapp());
        details.setAddress(details_request.address());

        details.setCity(details_request.city());
        details.setDistrict(details_request.district());
        details.setPin_code(details_request.pin_code());
        details.setState(details_request.state());
        details.setCountry(details_request.country());

        details.setGst(details_request.gst());
        details.setPan(details_request.pan());
        details.setPaymentAPI(details_request.paymentAPI());

        detailsRepo.save(details);

//       ===== StoreProfile Creation =====
        StoreProfileEntity profile = new StoreProfileEntity();

        profile.setStore(newStore);

        profile.setStoreName(profile_request.storeName());
        profile.setLogoUrl(profile_request.logoUrl());
        profile.setFaviconUrl(profile_request.faviconUrl());

        profile.setPrimaryColor(profile_request.primaryColor());
        profile.setSecondaryColor(profile_request.secondaryColor());
        profile.setAccentColor(profile_request.accentColor());

        profile.setMetaTitle(profile_request.metaTitle());
        profile.setMetaDescription(profile_request.metaDescription());
        profile.setOgImageUrl(profile_request.ogImageUrl());

        profile.setInstagramUrl(profile_request.instagramUrl());
        profile.setWhatsappUrl(profile_request.whatsappUrl());
        profile.setFacebookUrl(profile_request.facebookUrl());
        profile.setYoutubeUrl(profile_request.youtubeUrl());

        profileRepo.save(profile);

        return new ApiResponseMsg(
                "Store created Successfully"
        );

    }

}
