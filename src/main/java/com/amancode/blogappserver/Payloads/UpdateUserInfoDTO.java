package com.amancode.blogappserver.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateUserInfoDTO {
    
    @NotEmpty
    @Size(min = 4,message = "Username must be min of 4 characters.")
    private String name;

    private String about;
}
