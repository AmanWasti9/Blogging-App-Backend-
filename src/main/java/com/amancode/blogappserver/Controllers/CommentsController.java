package com.amancode.blogappserver.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amancode.blogappserver.Payloads.CommentsDTO;
import com.amancode.blogappserver.Services.CommentsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;
    
    // create - Comments
    @PostMapping("/user/{userId}/post/{postId}/comment")
    public ResponseEntity<CommentsDTO> createPost(@RequestBody CommentsDTO commentsDTO, @PathVariable Integer userId,
            @PathVariable Integer postId) {

        CommentsDTO createComment = this.commentsService.createComment(commentsDTO, userId, postId);
        return new ResponseEntity<CommentsDTO>(createComment, HttpStatus.CREATED);

    }



    // GET Single Comments By Id
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentsDTO> getSingleComment(@PathVariable Integer commentId) {
        return ResponseEntity.ok(this.commentsService.getCommentById(commentId));
    }


      // Get Comments By Post
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentsDTO>> getCommentsByPost(@PathVariable Integer postId) {
        List<CommentsDTO> comments = this.commentsService.getCommentsByPost(postId);
        return new ResponseEntity<List<CommentsDTO>>(comments, HttpStatus.OK);
    }

     // Get Comments By User
    @GetMapping("/user/{userId}/comments")
    public ResponseEntity<List<CommentsDTO>> getCommentsByUser(@PathVariable Integer userId) {
        List<CommentsDTO> comments = this.commentsService.getCommentsByUser(userId);
        return new ResponseEntity<List<CommentsDTO>>(comments, HttpStatus.OK);
    }



}
