package com.example.demo.controller;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtTokenProvider tokenProvider;

    public AuthController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User user=  userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        String token = tokenProvider.generateToken(user.getUsername(), user.getId());
        LoginResponse loginResponse= new LoginResponse(user.getId(), user.getUsername(), token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            String token = tokenProvider.generateToken(user.getUsername(), user.getId());
            LoginResponse loginResponse= new LoginResponse(user.getId(), user.getUsername(), token);
        return ResponseEntity.ok(loginResponse);
    }
}

@Getter
@Setter
class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private MultipartFile profileImage;

}
@Getter
@Setter
class LoginRequest {
    private String username;
    private String password;
}
@Getter
@Setter
@AllArgsConstructor
class LoginResponse
{
    private Long id;
    private String username;
    private String token;
}
@Getter
@Setter
@AllArgsConstructor
class JwtAuthenticationResponse {
    private String token;
}
