package com.amancode.blogappserver.Payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UpdateEmailRequest {
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true)
    @Email(message = "Invalid email format")
    private String email;
}
