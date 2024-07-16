package com.amancode.blogappserver.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Like;
import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.User;

public interface LikeRepo extends JpaRepository<Like, Integer>{

    List<Like> findByUser(User user);

    List<Like> findByPost(Post post);

    Like findByUserAndPost(User user, Post post);

}
