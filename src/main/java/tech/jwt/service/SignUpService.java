package tech.jwt.service;

import java.util.Set;
import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.jwt.model.Role;
import tech.jwt.model.ERole;
import tech.jwt.payload.Response.MessageResponse;
import tech.jwt.repository.UserReposity;
import tech.jwt.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.jwt.payload.Request.SignUpRequest;
import tech.jwt.repository.*;

@Service
public class SignUpService {
    @Autowired
    UserReposity userReposity;

    @Autowired
    RoleReposity roleReposity;

    @Autowired
    PasswordEncoder encoder;

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest){
        if(userReposity.existsByUsername(signUpRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Username is already taken!"));
        }
        if(userReposity.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Email is already taken!"));
        }

        User user = new User(signUpRequest.getUsername(),signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleReposity.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userReposity.save(user);
        return ResponseEntity.ok(new MessageResponse("Error:Email is already taken!"));
    }
}
