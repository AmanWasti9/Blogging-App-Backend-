package com.amancode.blogappserver.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ApiException;
import com.amancode.blogappserver.Payloads.JwtAuthRequest;
import com.amancode.blogappserver.Payloads.JwtAuthResponse;
import com.amancode.blogappserver.Payloads.UserDTO;
import com.amancode.blogappserver.Security.JwtTokenHelper;
import com.amancode.blogappserver.Services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> createToken(@Valid 
            @RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUser(this.mapper.map((User) userDetails, UserDTO.class));

        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details");
            throw new ApiException("Invalid Username or password");
        }

    }



    //Register new user api
    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO)
    {
        UserDTO registerdUser = this.userService.regieterNewUser(userDTO);
        return new ResponseEntity<UserDTO>(registerdUser,HttpStatus.CREATED);
    }



}
