package com.kimani.finance.transaction.service.database.repositories;

import com.kimani.finance.transaction.service.database.models.UserDetails;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserDetailsRepository extends R2dbcRepository<UserDetails, Long> {
    Mono<UserDetails> getUserDetailsDaoByUserIdentityEquals(@NonNull String userIdentity);

    Mono<UserDetails> findUserDetailsByIdEquals(long id);

    Mono<UserDetails> findUserDetailsDaoByPhoneNumberEquals(Long phoneNumber);

    Flux<UserDetails> findAllBy(Pageable pageable);
}
