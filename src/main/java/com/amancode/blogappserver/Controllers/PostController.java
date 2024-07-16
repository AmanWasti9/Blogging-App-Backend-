package com.amancode.blogappserver.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amancode.blogappserver.Config.AppConstants;
import com.amancode.blogappserver.Entities.Category;
import com.amancode.blogappserver.Payloads.API_Response;
import com.amancode.blogappserver.Payloads.PostDTO;
import com.amancode.blogappserver.Payloads.PostResponse;
import com.amancode.blogappserver.Services.FileService;
import com.amancode.blogappserver.Services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create - post
    @Operation(summary = "Create a post", description = "This is post api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success | OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized"),
            @ApiResponse(responseCode = "201", description = "New User Created")
    })
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDTO createPost = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);

    }

    // GET All Posts by Page Number
    @Operation(summary = "Get all the posts with pagination")
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    // GET All Posts
    @GetMapping("/allposts")
    public ResponseEntity<List<PostDTO>> getAllPostsWithoutPage() {
        return ResponseEntity.ok(this.postService.getAllPostsWithOutPage());

    }

  
    // GET Single Posts By Id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable Integer postId) {
        // // Increment view count
        // postService.incrementViewCount(postId);

        return ResponseEntity.ok(this.postService.getPostById(postId));
    }

    // Get Posts By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }

    // Get Posts By User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
        List<PostDTO> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }

    // Update Post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,
            @PathVariable Integer postId) {
        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    // Delete Post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<API_Response> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<API_Response>(new API_Response("Post Deleted Successfully", true), HttpStatus.OK);
    }

    // Search Post
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(
            @PathVariable("keywords") String keywords) {

        List<PostDTO> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);

    }

    // Post Image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        PostDTO postDTO = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDTO.setImageName(fileName);
        PostDTO updatePost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

    }

    // Method to serve Files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

    // Get Active Posts
    @GetMapping("/active-posts")
    public List<PostDTO> getAllActivePosts() {
        return this.postService.getActivePosts();
    }

    // New API to get paginated active posts
    @GetMapping("/paginated-active-posts")
    public ResponseEntity<PostResponse> getPaginatedActivePosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse postResponse = this.postService.getPaginatedActivePosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Get Active Posts of category
    @GetMapping("/active-post/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getAllActivePostsOfCategory(@PathVariable Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId.intValue()); // Convert Long to Integer

        List<PostDTO> activePosts = this.postService.getActivePostsOfCategory(category);

        return new ResponseEntity<List<PostDTO>>(activePosts, HttpStatus.OK);
    }

    // Get Active Posts of like
    @GetMapping("/active-post-with-likes")
    public List<PostDTO> getAllActivePostsWithLikes() {
        return this.postService.getAllActivePostsWithLikes();

    }

    @GetMapping("/total-likes")
    public ResponseEntity<Integer> getTotalLikesOnActivePosts() {
        int totalLikes = postService.getTotalLikesOnActivePosts();
        return ResponseEntity.ok(totalLikes);
    }

}
