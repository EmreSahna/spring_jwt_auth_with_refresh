package com.emresahna.springsecurityjwtdemo.controller;

import com.emresahna.springsecurityjwtdemo.dto.RenewAccessTokenRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserDto;
import com.emresahna.springsecurityjwtdemo.dto.UserRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserResponse;
import com.emresahna.springsecurityjwtdemo.entity.User;
import com.emresahna.springsecurityjwtdemo.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        UserResponse userResponse = authenticationService.auth(userRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Set-Cookie", "refreshToken=" + userResponse.getRefreshToken());
        return new ResponseEntity<>(userResponse, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<UserResponse> refresh(@CookieValue(name = "refreshToken") String refreshToken) {
        UserResponse userResponse = authenticationService.refreshToken(refreshToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Set-Cookie", "refreshToken=" + userResponse.getRefreshToken());
        return new ResponseEntity<>(userResponse, httpHeaders, HttpStatus.OK);
    }
}
