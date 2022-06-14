package com.hart.social.controller.v1;

import com.hart.social.model.User;
import com.hart.social.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersRepository usersRepository;

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity getAUser(@PathVariable String id) {

        User user = usersRepository.findUserById(Long.valueOf(id));
        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/users/all", produces = {"application/json"})
    public ResponseEntity getAllUsers() {

        List<User> users = usersRepository.findAll();
        return new ResponseEntity(users, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity createAUser(@RequestBody User user) {

        user.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        user.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

        String hashedPassword = BCrypt.hashpw(
                user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        usersRepository.save(user);
        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }


    @PutMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity EditAUser(@RequestBody User user, @PathVariable String id) {

        user.setId(Long.valueOf(id));
        user.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

        String hashedPassword = BCrypt.hashpw(
                user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        usersRepository.save(user);
        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity deleteAUser(@PathVariable String id) {

        usersRepository.deleteById(Long.valueOf(id));
        return new ResponseEntity("Deleted",HttpStatus.ACCEPTED);
    }


}