package com.kimani.finance.transaction.service.database.service;

import com.kimani.finance.transaction.service.database.models.UserDetails;
import com.kimani.finance.transaction.service.database.repositories.UserDetailsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Mock
    private UserDetailsRepository userDetailsRepository;
    private UserDetails userDetails;
    private String expectedUserIdentity = "1e29d8784736f18d0655cb341f761bf90eda9c3dae6d64c6a5460f06b62f91c3";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userDetailsServiceImpl, "hash", "SHA3-256");
        userDetails = UserDetails.builder()
                .surname("foo")
                .otherNames("foo bar")
                .idNumber(123456L)
                .phoneNumber(1234567890L)
                .userIdentity(expectedUserIdentity)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    public static Object [] [] createTestData() {
        return new Object [] [] {
                new Object [] { "foo", "bar", 1L, 12345L, "" },
                new Object [] { "bar", "foo", 2L, 4567L, "" },
                new Object [] { "foorbar", "foo", 81L, 90485L, "" },
                new Object [] { "test", "user", 885955L, 12345L, "" },
        };
    }

    @ParameterizedTest
    @MethodSource("createTestData")
    void createUserDetails() {
    }

    @Test
    void createUserDetailsTest() {
        when(userDetailsServiceImpl.createUserDetails("foo",
                "foo bar", 123456L, 1234567890L))
                .thenReturn(Mono.just(any()));
        Mono<UserDetails> userIdentityMono = userDetailsRepository
                .getUserDetailsDaoByUserIdentityEquals(expectedUserIdentity);
        StepVerifier
                .create(userIdentityMono)
                .consumeNextWith(userDetailsDao -> {
                    assertEquals(userDetailsDao.getUserIdentity(), expectedUserIdentity);
                })
                .verifyComplete();
    }
}