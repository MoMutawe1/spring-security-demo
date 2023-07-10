package identity.controller;

import identity.dto.IdentityRequest;
import identity.entity.UserCredential;
import identity.service.IdentityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identity")
@Slf4j
public class IdentityController {

    @Autowired
    private IdentityService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        log.info("User {} has been registered - IdentityController", user);
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody IdentityRequest identityRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identityRequest.getUsername(), identityRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            log.info("A new token {} has been created for user - IdentityController", identityRequest.getUsername());
            return service.generateToken(identityRequest.getUsername());
        } else {
            log.info("invalid request, There's no user with username ", identityRequest.getUsername());
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        log.info("IdentityController - Token is valid: {} ", token);
        return "Token is valid";
    }
}
