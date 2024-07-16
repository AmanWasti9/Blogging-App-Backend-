package com.amancode.blogappserver.Services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.Follow;
import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.FollowDTO;
import com.amancode.blogappserver.Repositories.FollowRepo;
import com.amancode.blogappserver.Repositories.UserRepo;
import com.amancode.blogappserver.Services.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepo followRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FollowDTO createFollow(FollowDTO followDTO, Integer followerUserId, Integer followingUserId) {
        User follower = userRepo.findById(followerUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", followerUserId));

        User following = userRepo.findById(followingUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", followingUserId));

        Follow existingFollow = followRepo.findByFollowerAndFollowing(follower, following);
        if (existingFollow != null) {
            throw new IllegalStateException("User is already following this user.");
        }

        Follow follow = modelMapper.map(followDTO, Follow.class);
        follow.setAddedDate(new Date());
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow.setIsFollow(true);

        Follow newFollow = followRepo.save(follow);

        return modelMapper.map(newFollow, FollowDTO.class);
    }

    @Override
    public void deleteFollow(Integer followId) {

        Follow follow = this.followRepo.findById(followId)
                .orElseThrow(() -> new ResourceNotFoundException("Follow", "Follow Id", followId));

        this.followRepo.delete(follow);

    }

    @Override
    public FollowDTO getFollowById(Integer followId) {
        Follow follow = this.followRepo.findById(followId)
                .orElseThrow(() -> new ResourceNotFoundException("Follow", "Follow Id", followId));

        return this.modelMapper.map(follow, FollowDTO.class);
    }

    @Override
    public List<FollowDTO> getFollowByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        List<Follow> follows = this.followRepo.findByFollower(user);

        List<FollowDTO> followDtos = follows.stream().map((follow) -> this.modelMapper.map(follow, FollowDTO.class))
                .collect(Collectors.toList());

        return followDtos;
    }

    @Override
    public List<FollowDTO> getFollowersOfUser(Integer userId) {
        // Retrieve the user by userId
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        // Retrieve the followers of the user
        List<Follow> followers = followRepo.findByFollowingId(userId);

        // Map the followers to FollowDTO objects
        List<FollowDTO> followDtos = followers.stream()
                .map(follower -> this.modelMapper.map(follower, FollowDTO.class))
                .collect(Collectors.toList());

        return followDtos;
    }

}
