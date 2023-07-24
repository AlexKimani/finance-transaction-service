package com.kimani.finance.transaction.service.database.service;

import com.kimani.finance.transaction.service.api.models.response.UserDetailsResponse;
import com.kimani.finance.transaction.service.database.models.UserDetails;
import com.kimani.finance.transaction.service.database.repositories.UserDetailsRepository;
import com.kimani.finance.transaction.service.utils.Utilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl {
    @Value("${security.hash}")
    private String hash;
    private final UserDetailsRepository userDetailsRepository;

    public Mono<UserDetails> createUserDetails(String surname, String otherNames, long idNumber, long phoneNumber) {
        Utilities utilities = new Utilities();
        try {
            var userIdentity = surname +
                    otherNames +
                    idNumber +
                    phoneNumber;
            String userIdentityKey = utilities.createSHAHash(hash, userIdentity);
            return userDetailsRepository.getUserDetailsDaoByUserIdentityEquals(userIdentityKey)
                    .flatMap(Mono::justOrEmpty)
                    .switchIfEmpty(createNewUserDetails(surname, otherNames, idNumber, phoneNumber, userIdentityKey));
        } catch (Exception e) {
            log.error("Error thrown {}", e.getMessage());
            return Mono.empty();
        }
    }

    private Mono<UserDetails> createNewUserDetails(String surname, String otherNames, long idNumber,
                                                   long phoneNumber, String userIdentityKey) {
        log.info("Creating user details for user {} with phoneNumber {} for hash: {}", surname, phoneNumber, userIdentityKey);
        UserDetails userDetails = UserDetails.builder()
                .surname(surname)
                .otherNames(otherNames)
                .idNumber(idNumber)
                .phoneNumber(phoneNumber)
                .userIdentity(userIdentityKey)
                .build();
        return userDetailsRepository.save(userDetails);
    }

    public Mono<Page<UserDetails>> getAllUsers(PageRequest pageRequest) {
        return userDetailsRepository.findAllBy(pageRequest.withSort(Sort.by("id")))
                .collectList()
                .zipWith(this.userDetailsRepository.count())
                .map(result -> new PageImpl<>(result.getT1(), pageRequest, result.getT2()));
    }

    public Mono<UserDetails> findUserDetailsByPhoneNumber(long phoneNumber) {
        return userDetailsRepository.findUserDetailsDaoByPhoneNumberEquals(phoneNumber);
    }

    public Mono<UserDetailsResponse> findUserDetailsByIdEquals(long id) {
        return userDetailsRepository.findUserDetailsByIdEquals(id)
                .flatMap(this::setUserDetailsResponse)
                .onErrorResume(error -> {
                    log.error("Error occurred get user details by id: ", error);
                    return Mono.empty();
                });
    }

    private Mono<UserDetailsResponse> setUserDetailsResponse(UserDetails userDetails) {
        return Mono.justOrEmpty(
                UserDetailsResponse.builder()
                        .surname(userDetails.getSurname())
                        .otherNames(userDetails.getOtherNames())
                        .userIdentity(userDetails.getUserIdentity())
                        .idNumber(userDetails.getIdNumber())
                        .phoneNumber(userDetails.getPhoneNumber())
                        .build());
    }
}
