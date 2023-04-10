package com.emresahna.springsecurityjwtdemo.service;

import com.emresahna.springsecurityjwtdemo.dto.RenewAccessTokenRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserDto;
import com.emresahna.springsecurityjwtdemo.dto.UserRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    UserResponse save(UserDto userDto);
    UserResponse auth(UserRequest userRequest);
    UserResponse refreshToken(RenewAccessTokenRequest renewAccessTokenRequest);
}
