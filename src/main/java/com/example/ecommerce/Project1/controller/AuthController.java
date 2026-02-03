package com.example.ecommerce.Project1.controller;
import com.example.ecommerce.Project1.model.AppRole;
import com.example.ecommerce.Project1.model.Role;
import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.repositories.RoleRepository;
import com.example.ecommerce.Project1.repositories.UserRepository;
import com.example.ecommerce.Project1.security.request.LoginRequest;
import com.example.ecommerce.Project1.security.request.SignUpRequest;
import com.example.ecommerce.Project1.security.response.MessageResponse;
import com.example.ecommerce.Project1.security.response.UserInfoResponse;
import com.example.ecommerce.Project1.security.JWT.JwtUtils;
import com.example.ecommerce.Project1.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @PostMapping("/signin")
    public ResponseEntity<?> AuthenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

        }catch (AuthenticationException e) {
            Map<String,Object> map  = new HashMap<>();
            map.put("message","Bad credentials");
            map.put("status",false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);


        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        UserInfoResponse response = new UserInfoResponse(userDetails.getUsername(),roles,userDetails.getId());
        // Remove Jwt Token from above constructor , we have different ways to do that.
//        return ResponseEntity.ok(response); // Token Way
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response); // Cookie way to response
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest request) {

         if(userRepository.existsByUsername(request.getUsername())) {
             return ResponseEntity.badRequest()
                     .body(new MessageResponse("Error : Username "+request.getUsername() +"Already Exist !!"));
         }
         if(userRepository.existsByEmail(request.getEmail())) {
             return ResponseEntity.badRequest()
                     .body(new MessageResponse("Error: Email "+ request.getEmail()+"already Exist !"));

         }
                User user = new User(request.getUsername(),
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword()));
        Set<String> strRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();
        if(strRoles == null) {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("Error : Role is not assigned!!"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role ->{
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error : Role is not assigned!!"));
                        roles.add(adminRole);
                    break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                        .orElseThrow(()->new RuntimeException("Error : Role is not assigned!!"));
                        roles.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error : Role is not assigned!!"));
                        roles.add(userRole);


                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }
    @GetMapping("/username")
    public  String currentUserName(Authentication authentication) {
       return authentication != null ? authentication.getName() :"NULL";
    }
    @GetMapping("/user")
    public  ResponseEntity<?> currentUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getUsername(),roles,userDetails.getId());
        return ResponseEntity.ok(userInfoResponse);

    }
    @PostMapping("/signout")
    public  ResponseEntity<?> signOut(Authentication authentication) {
        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtUtils.getCleanCookie().toString()).body(new MessageResponse("You've been signed out!"));
    }

}
