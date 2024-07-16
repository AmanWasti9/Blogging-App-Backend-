package com.amancode.blogappserver.Payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeDTO {

    private int id;

    private Date addedDate;

    private Boolean isLike;

    private UserDTO user;

    private PostDTO post;
}
