package IssueTracker.controller;

import IssueTracker.dto.AuthRequest;
import IssueTracker.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/login")
    public String authRequest(@RequestBody AuthRequest authRequest){
        //1) validate request
        if("vedant".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())){
            //2) generate the token
             return jwtUtil.generateToken(authRequest.getUsername(), "ADMIN");
        }
        if("user".equals(authRequest.getUsername()) && "password123".equals(authRequest.getPassword())){
            return jwtUtil.generateToken(authRequest.getUsername(), "USER");
        }
        else{
            throw new RuntimeException("Invalid credentials");
        }

    }
}
