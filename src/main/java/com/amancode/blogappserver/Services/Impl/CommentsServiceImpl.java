package com.amancode.blogappserver.Services.Impl;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.Comments;
import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.CommentsDTO;
import com.amancode.blogappserver.Repositories.CommentsRepo;
import com.amancode.blogappserver.Repositories.PostRepo;
import com.amancode.blogappserver.Repositories.UserRepo;
import com.amancode.blogappserver.Services.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentsDTO createComment(CommentsDTO commentsDTO, Integer userId, Integer postId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        Comments comments = this.modelMapper.map(commentsDTO, Comments.class);
        comments.setAddedDate(new Date());
        comments.setUser(user);
        comments.setPost(post);

        Comments newComment = this.commentsRepo.save(comments);

        return this.modelMapper.map(newComment, CommentsDTO.class);

    }

    @Override
    public void deleteComment(Integer commentId) {

        Comments comment = this.commentsRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        this.commentsRepo.delete(comment);

    }

    @Override
    public CommentsDTO getCommentById(Integer commentId) {

        Comments comment = this.commentsRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        return this.modelMapper.map(comment, CommentsDTO.class);
    }

    

    @Override
    public List<CommentsDTO> getCommentsByPost(Integer postId) {
       
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

                List<Comments> comments = this.commentsRepo.findByPost(post);

                List<CommentsDTO> commentsDTOs = comments.stream().map((comment) -> this.modelMapper.map(comment, CommentsDTO.class))
                .collect(Collectors.toList());

        return commentsDTOs;


    }

    @Override
    public List<CommentsDTO> getCommentsByUser(Integer userId) {
        
        User user = this.userRepo.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        List<Comments> comments = this.commentsRepo.findByUser(user);


        List<CommentsDTO> commentsDTOs = comments.stream().map((comment) -> this.modelMapper.map(comment, CommentsDTO.class))
                .collect(Collectors.toList());

        return commentsDTOs;
    
    }

}
