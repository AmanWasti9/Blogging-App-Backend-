package com.amancode.blogappserver.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Follow;
import com.amancode.blogappserver.Entities.User;

public interface FollowRepo extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowing(User following);

    Follow findByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollowingId(Integer userId);

}
