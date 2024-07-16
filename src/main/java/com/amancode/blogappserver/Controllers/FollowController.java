package com.amancode.blogappserver.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amancode.blogappserver.Payloads.API_Response;
import com.amancode.blogappserver.Payloads.FollowDTO;
import com.amancode.blogappserver.Services.FollowService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api")
public class FollowController {

    @Autowired
    private FollowService followService;

    // create - Likes
    // @PostMapping("/user/{userId}/createFollow")
    // public ResponseEntity<FollowDTO> createFollow(@RequestBody FollowDTO
    // followDTO, @PathVariable Integer userId) {
    // FollowDTO createdFollow = followService.createFollow(followDTO, userId);
    // return new ResponseEntity<>(createdFollow, HttpStatus.CREATED);
    // }
    @PostMapping("/user/{followerUserId}/follow/{followingUserId}")
    public ResponseEntity<FollowDTO> followUser(@RequestBody FollowDTO followDTO,
            @PathVariable Integer followerUserId,
            @PathVariable Integer followingUserId) {
        FollowDTO createdFollow = followService.createFollow(followDTO, followerUserId, followingUserId);
        return new ResponseEntity<>(createdFollow, HttpStatus.CREATED);
    }

    // GET Single Likes By Id
    @GetMapping("/follow/{followId}")
    public ResponseEntity<FollowDTO> getFollowById(@PathVariable Integer followId) {
        return ResponseEntity.ok(this.followService.getFollowById(followId));
    }

    // Get Likes By User
    @GetMapping("/UserId/{userId}")
    public ResponseEntity<List<FollowDTO>> getAllFollowByUserId(@PathVariable Integer userId) {
        List<FollowDTO> followsByUserId = this.followService.getFollowByUser(userId);
        return new ResponseEntity<List<FollowDTO>>(followsByUserId, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowDTO>> getFollowersOfUser(@PathVariable Integer userId) {
        List<FollowDTO> followers = followService.getFollowersOfUser(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    // DELETE - delete Follow
    @DeleteMapping("/delete/{followId}")
    public ResponseEntity<API_Response> deleteFollow(@PathVariable("followId") Integer followId) {
        this.followService.deleteFollow(followId);
        return new ResponseEntity<API_Response>(new API_Response("Follow Deleted Successfully", true), HttpStatus.OK);
    }

}
