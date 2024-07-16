package com.amancode.blogappserver.Payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowDTO {

    private int id;

    private boolean isFollow;

    private Date addedDate;

    private UserDTO follower;

    private UserDTO following;

}
