package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Payloads.LikeDTO;

public interface LikeService {
    
    //Create
    LikeDTO createLike(LikeDTO likeDTO,Integer userId,Integer postId);
    
    //Delete 
    void deleteLike(Integer likeId);
    
    //Get Single Like 
    LikeDTO getLikeById(Integer likeId);
    
    //Get All Likes By Post
    List<LikeDTO> getLikeByPost(Integer postId);

    //Get All Likes by User
    List<LikeDTO> getLikeByUser(Integer userId);

    LikeDTO updateLike(Integer likeId, LikeDTO likeDTO);

}
