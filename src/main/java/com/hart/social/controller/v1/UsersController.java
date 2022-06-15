package com.hart.social.controller.v1;

import com.hart.social.model.User;
import com.hart.social.repository.UsersRepository;
import com.hart.social.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity EditAUser(@RequestBody User user, @PathVariable String id,
            HttpServletRequest request ) {

        User pathUser = usersRepository.findUserById(Long.valueOf(id));

        String token = getTokenFromRequest(request.getHeader("Authorization"));
        User requestingUser = usersRepository.findUserByEmail(jwtUtil.extractUsername(token));

        if (requestingUser.getRole().equals("admin") == true ||
                requestingUser.getEmail().equals(pathUser.getEmail()) == true) {

            user.setId(Long.valueOf(id));
            user.setUpdatedAt(String.valueOf(System.currentTimeMillis()));

            String hashedPassword = BCrypt.hashpw(
                    user.getPassword(), BCrypt.gensalt(12));
            user.setPassword(hashedPassword);

            usersRepository.save(user);
            return new ResponseEntity(user, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity(
                "you do not have permission to update this entity",
                HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity deleteAUser(@PathVariable String id,
                                      HttpServletRequest request) {

        User pathUser = usersRepository.findUserById(Long.valueOf(id));

        String token = getTokenFromRequest(request.getHeader("Authorization"));
        User requestingUser = usersRepository.findUserByEmail(jwtUtil.extractUsername(token));

        if (requestingUser.getRole().equals("admin") == true ||
                requestingUser.getEmail().equals(pathUser.getEmail()) == true) {

            usersRepository.deleteById(Long.valueOf(id));
            return new ResponseEntity("deleted the entity",HttpStatus.ACCEPTED);

        }

        return new ResponseEntity(
                "you do not have permission to delete this entity",
                HttpStatus.FORBIDDEN);
    }

    private static String getTokenFromRequest(String authorization) {
        String token = authorization.substring(7, authorization.length());
        return token;
    }


}