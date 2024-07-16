package com.amancode.blogappserver.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.Save;
import com.amancode.blogappserver.Entities.User;

public interface SaveRepo extends JpaRepository<Save, Integer>{

    List<Save> findByUser(User user);

    List<Save> findByPost(Post post);

    Save findByUserAndPost(User user, Post post);
    
}
