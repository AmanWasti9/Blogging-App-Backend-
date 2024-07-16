package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Payloads.CommentsDTO;

public interface CommentsService {
    

    //Create
    CommentsDTO createComment(CommentsDTO commentsDTO,Integer userId,Integer postId);
    
    //Delete 
    void deleteComment(Integer commentId);
    
    //Get Single Comments 
    CommentsDTO getCommentById(Integer commentId);
    
    //Get All Comments by Post
    List<CommentsDTO> getCommentsByPost(Integer postId);

    //Get All Comments by User
    List<CommentsDTO> getCommentsByUser(Integer userId);


}
