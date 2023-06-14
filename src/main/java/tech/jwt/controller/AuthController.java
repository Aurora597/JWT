package tech.jwt.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jwt.payload.Request.LoginRequest;
import tech.jwt.payload.Request.SignUpRequest;
import tech.jwt.service.LoginService;
import tech.jwt.service.SignUpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        return signUpService.registerUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Userlogin(@Valid @RequestBody LoginRequest loginRequest){
        return loginService.Userlogin(loginRequest);
    }

}
