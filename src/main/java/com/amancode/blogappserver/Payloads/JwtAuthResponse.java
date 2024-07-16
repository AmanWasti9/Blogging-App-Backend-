package com.amancode.blogappserver.Payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
    
    private String token;

    private UserDTO user;

}
