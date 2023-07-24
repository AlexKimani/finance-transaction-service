package com.kimani.finance.transaction.service.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UtilitiesTest {
    private Utilities utilities;

    private String hashFunctionName;
    private String userIdentity;

    @BeforeEach
    void setUp() {
        utilities = new Utilities();
        hashFunctionName = "SHA3-256";
        userIdentity = "foo" +
                "foo bar" +
                123456L +
                1234567890L;
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createSHAHash() {
        String expectedValue = "1e29d8784736f18d0655cb341f761bf90eda9c3dae6d64c6a5460f06b62f91c3";
        String output = utilities.createSHAHash(hashFunctionName, userIdentity);
        assertNotNull(output);
        assertEquals(expectedValue, output);
    }
}