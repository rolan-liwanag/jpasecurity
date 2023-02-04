package com.liwanag.jpasecurity.controller;

import com.liwanag.jpasecurity.dto.UserDto;
import com.liwanag.jpasecurity.repository.AuthorityRepository;
import com.liwanag.jpasecurity.repository.UserRepository;
import com.liwanag.jpasecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @PostMapping(path="/create-user")
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        try {
            this.userService.createUser(userDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/user/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        Optional<UserDto> userDtoOpt = this.userService.findUserByUsername(username);
        if(userDtoOpt.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(userDtoOpt.get(), HttpStatus.OK);
        }
    }

    @GetMapping(path="/users")
    public ResponseEntity userPage() {
        List<UserDto> userDtos = this.userService.getAllUsers();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(path="/greeting")
    public String everybodyAllowedPage() {
        return "everybody allowed page";
    }
}
