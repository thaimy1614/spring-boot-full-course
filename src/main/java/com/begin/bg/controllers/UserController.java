package com.begin.bg.controllers;

import com.begin.bg.models.User;
import com.begin.bg.models.ResponseObject;
import com.begin.bg.enums.UserStatus;
import com.begin.bg.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    //Get detail User
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable UUID id) {
        Optional<User> foundUser = userService.findUserById(id);
        return foundUser.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "User found", foundUser))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAILED", "Cannot find User with id = " + id, null));
    }

    /*
    User getUserByID(@PathVariable UUID id){
        return userService.findById(id).orElseThrow(()->new RuntimeException("ERROR"));
    }
     */

    //Update User or insert User if not found
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody User newUser, @PathVariable UUID id) {
        User updatedUser = userService.findUserById(id)
                .map(User -> {
                    User.setUsername(newUser.getUsername());
                    User.setPassword(newUser.getPassword());
                    User.setStatus(UserStatus.UNVERIFIED.name());
                    User.setRole(newUser.getRole());
                    User.setFirstName(newUser.getFirstName());
                    User.setLastName(newUser.getLastName());
                    return userService.saveUser(User);
                }).orElseGet(() -> {
                    return userService.saveUser(newUser);
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Updated User succesful!", updatedUser));
    }

    //Delete a User
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable UUID id) {
        Boolean exists = userService.userExistsById(id);
        if(exists){
            User user = userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Deleted User with id = " + id + " successful!", user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAIL", "User not found", ""));
    }
}
