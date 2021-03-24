package com.sc.auth.Controller;

import com.sc.auth.model.AuthToken;
import com.sc.auth.model.JwtUserDetails;
import com.sc.auth.model.LoginDto;
import com.sc.auth.model.User;
import com.sc.auth.repo.UserRepo;
import com.sc.auth.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtils jwtUtils;

@PostMapping("/register")
@ResponseStatus(HttpStatus.CREATED)
    public AuthToken register(@RequestBody  User user)
    {
        User user1=userRepo.save(user);
        return  jwtUtils.generateToken(user1);
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthToken login(@RequestBody LoginDto loginDto) {
        Optional<User> userOpt = userRepo.findByUserName(loginDto.getUserName());
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            if (loginDto.getPassword().equals(existingUser.getPassword())) {
                return jwtUtils.generateToken(userRepo.save(existingUser));
            }
        }

        throw new RuntimeException("UserName not exist");
    }

    @GetMapping("/validate")
    public JwtUserDetails validator(@RequestHeader("x-auth-token") String authToken)
    {
        return  jwtUtils.validateToken(authToken);
    }
}
