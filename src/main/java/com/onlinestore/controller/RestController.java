package com.onlinestore.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onlinestore.model.User;
import com.onlinestore.model.Views;
import com.onlinestore.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserServiceImpl service;

    public RestController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/v1/user/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> getUserInfo(@PathVariable("id") int id) {
        User user = service.getUserInfo(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/v1/users")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getUserList();
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/v1/user/new")
    public ResponseEntity<User> createUser(@RequestParam String name, String phone, String email) {
        User newUser = service.createUser(name, phone, email);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @DeleteMapping("/v1/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id) {
        service.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/v1/user/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id,
                                           @RequestParam (required = false) String name,
                                           @RequestParam (required = false) String phone,
                                           @RequestParam (required = false) String email) {
        return new ResponseEntity<>(HttpStatus.OK); //обновление полей? уточнить
    }
}