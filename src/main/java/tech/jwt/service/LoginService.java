package tech.jwt.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import tech.jwt.security.JwtUtils;
import tech.jwt.security.UserDetailsImpl;
import tech.jwt.payload.Request.LoginRequest;
import tech.jwt.repository.RoleReposity;
import tech.jwt.repository.UserReposity;
import tech.jwt.payload.Response.JwtResponse;

@Service
public class LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserReposity userReposity;

    @Autowired
    RoleReposity roleReposity;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> Userlogin(LoginRequest loginRequest){

        if(!userReposity.existsByUsername(loginRequest.getUsername())){
            return ResponseEntity.badRequest().body("Error:User does not exist!");
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail()));
        }catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body("Error:Password is wrong!");
        }catch (LockedException e){
            return ResponseEntity.badRequest().body("Error:User is locked!");
        }

    }
}
