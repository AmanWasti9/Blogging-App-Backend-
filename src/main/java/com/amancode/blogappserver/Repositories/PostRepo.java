package com.amancode.blogappserver.Repositories;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Category;
import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

    List<Post> findByActive(Boolean b);

    List<Post> findByCategoryAndActive(Category category, Boolean b);

    Page<Post> findAllByActiveTrue(Pageable pageable);

}
