package com.begin.bg.controllers;


import com.begin.bg.dto.request.IntrospectRequest;
import com.begin.bg.dto.request.InvalidatedTokenRequest;
import com.begin.bg.dto.response.IntrospectResponse;
import com.begin.bg.enums.UserRole;
import com.begin.bg.models.ResponseObject;
import com.begin.bg.models.User;
import com.begin.bg.enums.UserStatus;
import com.begin.bg.services.AuthenticationService;
import com.begin.bg.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserService userService;

    //Insert new User with POST method
    @PostMapping("/signup")
    ResponseEntity<ResponseObject> insertUser(@RequestBody User newUser) {
        Optional<User> foundUser = userService.findUserByName(newUser.getUsername().trim());
        newUser.setStatus(UserStatus.UNVERIFIED.name());
        HashSet<String> roles = new HashSet<>();
        roles.add(UserRole.CUSTOMER.name());
        newUser.setRole(roles);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return foundUser.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Insert User successful!", userService.saveUser(newUser)))
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("FAIL", "User name already taken", null));
    }

    @PostMapping("/auth")
    ResponseEntity<ResponseObject> authenticate(@RequestBody User user) throws Exception {
        var auth = authService.authenticate(user);
        return auth != null?(ResponseEntity.status(HttpStatus.OK).body(auth)):
                (ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "FAILED", "Username or password not correct!", null)))
        ;
    }

    @GetMapping("/introspect")
    ResponseEntity<ResponseObject> introspect(@RequestBody IntrospectRequest introspectRequest) throws Exception {
        IntrospectResponse introspect= authService.introspect(introspectRequest.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Logout successful!", introspect));
    }

    @GetMapping("/log-out")
    ResponseEntity<ResponseObject> logout(@RequestBody InvalidatedTokenRequest invalidatedTokenRequest) throws Exception {
        authService.logout(invalidatedTokenRequest.getToken());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Logout successful!", invalidatedTokenRequest
                .getToken()));
    }
}
