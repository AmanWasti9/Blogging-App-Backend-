package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Payloads.FollowDTO;

public interface FollowService {

    // Create
    // FollowDTO createFollow(FollowDTO followDTO, Integer userId);
    FollowDTO createFollow(FollowDTO followDTO, Integer followerUserId, Integer followingUserId); // Modified method signature


    // Delete
    void deleteFollow(Integer followId);

    // Get Single Like
    FollowDTO getFollowById(Integer followId);

    // Get All Likes by User
    List<FollowDTO> getFollowByUser(Integer userId);

    List<FollowDTO> getFollowersOfUser(Integer userId);

}
