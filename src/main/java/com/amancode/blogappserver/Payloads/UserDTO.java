package com.amancode.blogappserver.Payloads;

import java.util.HashSet;
import java.util.Set;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDTO {

    private int id;
    
    @NotEmpty
    @Size(min = 4,message = "Username must be min of 4 characters.")
    private String name;
    
    @NotEmpty
    @Email(message = "Email address is not valid.")
    private String email;
    
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)     
    @NotEmpty
    @Size(min = 3, max = 10,message = "Password must contain 3-10 characters.")
    private String password;

    private String about;

    private String imgName;


    private Set<RoleDTO> roles = new HashSet<>();


    
}
