package com.collin.booksocial.auth;

import com.collin.booksocial.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private RoleRepository roleRepository;

    public void register(RegistrationRequest request) {
        var userRole roleRepository.findByName("USER")
                // todo better exception handling
                .orElseThrow(() -> new IllegalStateException())
    }
}
