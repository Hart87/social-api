package com.hart.social.controller.v1;

import com.hart.social.model.User;
import com.hart.social.model.security.AuthenticationRequest;
import com.hart.social.model.security.AuthenticationResponse;
import com.hart.social.repository.UsersRepository;
import com.hart.social.security.JwtUtil;
import com.hart.social.security.SocialUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private SocialUserDetailService socialUserDetailService;


    // POST
    @PostMapping(value = "/login", produces = {"application/json"})
    public ResponseEntity<?> authenticateFirstFactor(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {


        //find the needed user
        User foundUser = usersRepository.findUserByEmail(
                authenticationRequest.getUsername());


        //compare passwords
        boolean verified = BCrypt.checkpw(
                authenticationRequest.getPassword(), foundUser.getPassword());
        logger.info(String.valueOf(verified));

        if ( verified )  {

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getUsername(),
                                foundUser.getPassword())
                );
            } catch (BadCredentialsException e) {
                throw new Exception("Incorrect username and/or password", e);
            }

            final UserDetails userDetails = socialUserDetailService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("credentials could not be verified", HttpStatus.BAD_REQUEST);

    }


    @GetMapping(value = "/validate", produces = {"application/json"})
    public ResponseEntity<String> validate() {
        return ResponseEntity.ok("Successfully Validated Token");
    }

}