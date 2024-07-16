package com.amancode.blogappserver.Payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDTO {
    
    private int id;

    private String content;

    private Date addedDate;

    private UserDTO user;

    private PostDTO post;

}
