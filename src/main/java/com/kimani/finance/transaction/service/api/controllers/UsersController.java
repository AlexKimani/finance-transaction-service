package com.kimani.finance.transaction.service.api.controllers;

import com.kimani.finance.transaction.service.api.models.request.UserDetailsRequest;
import com.kimani.finance.transaction.service.api.models.response.UserDetailsResponse;
import com.kimani.finance.transaction.service.api.service.UserService;
import com.kimani.finance.transaction.service.database.models.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping(path = "/create")
    public Mono<UserDetailsResponse> createNewUser(@RequestBody UserDetailsRequest request) {
        return userService.createNewUser(request);
    }

    @GetMapping(path = "/all")
    public Mono<Page<UserDetails>> getAllUsers(@RequestParam("page") int page,
                                               @RequestParam("size") int pageSize) {
        return userService.getAllUsers(PageRequest.of(page, pageSize));
    }
}
