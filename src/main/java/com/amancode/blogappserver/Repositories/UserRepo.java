package com.amancode.blogappserver.Repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.User;



public interface UserRepo extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);

    List<User> findFollowersById(int userId);

    List<User> findFollowingById(int userId);

}
