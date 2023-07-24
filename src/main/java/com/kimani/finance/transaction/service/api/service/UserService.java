package com.kimani.finance.transaction.service.api.service;

import com.kimani.finance.transaction.service.api.models.request.UserDetailsRequest;
import com.kimani.finance.transaction.service.api.models.response.UserDetailsResponse;
import com.kimani.finance.transaction.service.database.models.UserDetails;
import com.kimani.finance.transaction.service.database.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public Mono<UserDetailsResponse> createNewUser(UserDetailsRequest userDetailsRequest) {
        return userDetailsServiceImpl.createUserDetails(userDetailsRequest.getFirstName(), userDetailsRequest.getOtherNames(),
                userDetailsRequest.getIdNumber(), userDetailsRequest.getPhoneNumber()).flatMap(userDetailsDao -> {
                    UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                            .surname(userDetailsDao.getSurname())
                            .otherNames(userDetailsDao.getOtherNames())
                            .idNumber(userDetailsDao.getIdNumber())
                            .phoneNumber(userDetailsDao.getPhoneNumber())
                            .userIdentity(userDetailsDao.getUserIdentity())
                            .build();
                    return Mono.justOrEmpty(userDetailsResponse);
        });
    }

    public Mono<Page<UserDetails>> getAllUsers(PageRequest pageRequest) {
        return userDetailsServiceImpl.getAllUsers(pageRequest);
    }
}
