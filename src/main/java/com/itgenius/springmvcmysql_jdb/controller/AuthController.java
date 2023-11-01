package com.itgenius.springmvcmysql_jdb.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itgenius.springmvcmysql_jdb.model.ERole;
import com.itgenius.springmvcmysql_jdb.model.Role;
import com.itgenius.springmvcmysql_jdb.model.Users;
import com.itgenius.springmvcmysql_jdb.payload.request.LoginRequest;
import com.itgenius.springmvcmysql_jdb.payload.request.SignupRequest;
import com.itgenius.springmvcmysql_jdb.payload.response.JwtResponse;
import com.itgenius.springmvcmysql_jdb.payload.response.MessageResponse;
import com.itgenius.springmvcmysql_jdb.repository.RoleRepository;
import com.itgenius.springmvcmysql_jdb.repository.UsersRepository;
import com.itgenius.springmvcmysql_jdb.security.jwt.JwtUtils;
import com.itgenius.springmvcmysql_jdb.security.services.UserDetailsImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    // Signup for new user
    @PostMapping(value="/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest){
        
        if(usersRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if(usersRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create Account
        Users user = new Users(
            signupRequest.getUsername(),
            signupRequest.getEmail(),
            encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository
            .findByName(ERole.ROLE_USER)
            .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }else{
            strRoles.forEach(role->{
                switch(role){
                    case "admin":
                        Role adminRole = roleRepository
                            .findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository
                            .findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        usersRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User register successfully"));
    }


    // Sign In
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
        .map(item->item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
            jwt, 
            "Bearer",
            userDetails.getId(), 
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles
        ));
    }
    

}