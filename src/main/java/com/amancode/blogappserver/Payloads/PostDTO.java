package com.amancode.blogappserver.Payloads;

import java.util.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

  private Integer postId;

    private String title;

    private String content;
    
    private String imageName;
    
    private Date addedDate;

    private Boolean active = true;

    // private Integer viewCount=0;

    private CategoryDTO category;

    private UserDTO user;  
    
    private List<LikeDTO> likes;
    
}
