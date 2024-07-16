package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Entities.Category;
import com.amancode.blogappserver.Payloads.PostDTO;
import com.amancode.blogappserver.Payloads.PostResponse;

public interface PostService {
     
	//Create
    PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
    
    //Update
    PostDTO updatePost(PostDTO postDTO,Integer postId);
    
    //Delete 
    void deletePost(Integer postId);
    
    //Get Single Post 
    PostDTO getPostById(Integer postId);
    
    //Get All Posts with paginaton
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    
    //Get All Posts without paginaton
     List<PostDTO> getAllPostsWithOutPage();

    //Get All Posts By Category
    List<PostDTO> getPostsByCategory(Integer categoryId);

    //Get All Posts By User
    List<PostDTO> getPostsByUser(Integer userId);

    //Search Post
    List<PostDTO> searchPosts(String keyword);

    
    
    List<PostDTO> getActivePosts();

    List<PostDTO> getActivePostsOfCategory(Category category);

    PostResponse getPaginatedActivePosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    // View Counts
    // void incrementViewCount(Integer postId);
    List<PostDTO> getAllActivePostsWithLikes();

    int getTotalLikesOnActivePosts();


}
