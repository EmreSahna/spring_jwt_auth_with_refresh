package com.emresahna.springsecurityjwtdemo.service.impl;

import com.emresahna.springsecurityjwtdemo.dto.RenewAccessTokenRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserDto;
import com.emresahna.springsecurityjwtdemo.dto.UserRequest;
import com.emresahna.springsecurityjwtdemo.dto.UserResponse;
import com.emresahna.springsecurityjwtdemo.entity.User;
import com.emresahna.springsecurityjwtdemo.repository.UserRepository;
import com.emresahna.springsecurityjwtdemo.service.AuthenticationService;
import com.emresahna.springsecurityjwtdemo.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        userRepository.save(user);

        var jwtToken = jwtTokenService.generateToken(user);
        var refreshToken = jwtTokenService.generateRefresh(user);

        return UserResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserResponse auth(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtTokenService.generateToken(user);
        var refreshToken = jwtTokenService.generateRefresh(user);

        return UserResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserResponse refreshToken(RenewAccessTokenRequest renewAccessTokenRequest) {
        final String refreshToken = renewAccessTokenRequest.getRefreshToken();
        final String username = jwtTokenService.findUsernameByToken(refreshToken);

        if (username == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var user = userRepository.findByUsername(username)
                .orElseThrow();

        if (!jwtTokenService.validateToken(refreshToken, user)) {
            throw new RuntimeException("Refresh token is not valid");
        }

        var newAccessToken = jwtTokenService.generateToken(user);
        var newRefreshToken = jwtTokenService.generateRefresh(user);
        return UserResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
