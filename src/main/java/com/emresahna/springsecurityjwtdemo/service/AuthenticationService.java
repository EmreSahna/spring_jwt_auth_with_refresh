package com.emresahna.springsecurityjwtdemo.service;

import com.emresahna.springsecurityjwtdemo.dto.UserDto;
import com.emresahna.springsecurityjwtdemo.dto.UserRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserResponse;

public interface AuthenticationService {
    UserResponse save(UserDto userDto);
    UserResponse auth(UserRequest userRequest);
    UserResponse refreshToken(String refreshToken);
}
