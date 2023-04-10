package com.emresahna.springsecurityjwtdemo.controller;

import com.emresahna.springsecurityjwtdemo.dto.RenewAccessTokenRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserDto;
import com.emresahna.springsecurityjwtdemo.dto.UserRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserResponse;
import com.emresahna.springsecurityjwtdemo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/save")
    public ResponseEntity<UserResponse> save(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.save(userDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserResponse> auth(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authenticationService.auth(userRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<UserResponse> refresh(@RequestBody RenewAccessTokenRequest renewAccessTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(renewAccessTokenRequest));
    }
}
