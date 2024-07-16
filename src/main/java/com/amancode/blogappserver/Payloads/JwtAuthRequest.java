package com.amancode.blogappserver.Payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;

    private String password;

    
    
}
