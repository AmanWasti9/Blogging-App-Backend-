package com.amancode.blogappserver.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UpdatePasswordRequest {
    
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must contain 3-10 characters.")
    private String password;
}
