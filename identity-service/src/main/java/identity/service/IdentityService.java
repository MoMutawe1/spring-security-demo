package identity.service;

import identity.entity.UserCredential;
import identity.repository.UserCredentialRepository;
import identity.util.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IdentityService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        log.info("User with username {} has been added to the system - IdentityService", credential.getUserName());
        return "user added to the system";
    }

    public String generateToken(String username) {
        log.info("A new token {} has been created for user - IdentityService", username);
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        log.info("IdentityService - Token is valid: {} ", token);
        jwtService.validateToken(token);
    }
}
