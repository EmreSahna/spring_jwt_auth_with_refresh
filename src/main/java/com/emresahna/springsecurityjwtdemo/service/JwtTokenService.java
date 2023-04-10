package com.emresahna.springsecurityjwtdemo.service;

import com.emresahna.springsecurityjwtdemo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    String findUsernameByToken(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String generateToken(User user);
    String generateRefresh(User user);
}
