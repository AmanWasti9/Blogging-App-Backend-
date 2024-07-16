package com.amancode.blogappserver.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Comments;
import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.User;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

    List<Comments> findByUser(User user);

    List<Comments> findByPost(Post post);


    
}
