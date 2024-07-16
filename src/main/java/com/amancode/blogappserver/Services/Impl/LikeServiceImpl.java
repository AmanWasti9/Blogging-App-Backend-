package com.amancode.blogappserver.Services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.Like;
import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.LikeDTO;
import com.amancode.blogappserver.Repositories.LikeRepo;
import com.amancode.blogappserver.Repositories.PostRepo;
import com.amancode.blogappserver.Repositories.UserRepo;
import com.amancode.blogappserver.Services.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

        @Autowired
        private LikeRepo likeRepo;

        @Autowired
        private PostRepo postRepo;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private UserRepo userRepo;

        

        @Override
        public LikeDTO createLike(LikeDTO likeDTO, Integer userId, Integer postId) {
                // Retrieve the User object
                User user = userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

                // Retrieve the Post object
                Post post = postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

                // Check if a like already exists for the given user and post
                Like existingLike = likeRepo.findByUserAndPost(user, post);
                if (existingLike != null) {
                        throw new IllegalStateException("User has already liked this post.");
                }

                // Create a new Like object
                Like like = modelMapper.map(likeDTO, Like.class);
                like.setAddedDate(new Date());
                like.setUser(user);
                like.setPost(post);
                like.setIsLike(likeDTO.getIsLike());

                // Save the new Like object
                Like newLike = likeRepo.save(like);

                // Map the new Like object to LikeDTO and return it
                return modelMapper.map(newLike, LikeDTO.class);
        }

        @Override
        public void deleteLike(Integer likeId) {
                Like like = this.likeRepo.findById(likeId)
                                .orElseThrow(() -> new ResourceNotFoundException("Like", "Like Id", likeId));

                this.likeRepo.delete(like);

        }

        @Override
        public LikeDTO getLikeById(Integer likeId) {
                Like like = this.likeRepo.findById(likeId)
                                .orElseThrow(() -> new ResourceNotFoundException("Like", "Like Id", likeId));

                return this.modelMapper.map(like, LikeDTO.class);
        }

        @Override
        public List<LikeDTO> getLikeByPost(Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

                List<Like> likes = this.likeRepo.findByPost(post);

                List<LikeDTO> likeDTO = likes.stream().map((like) -> this.modelMapper.map(like, LikeDTO.class))
                                .collect(Collectors.toList());

                return likeDTO;

        }

        @Override
        public List<LikeDTO> getLikeByUser(Integer userId) {
                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

                List<Like> likes = this.likeRepo.findByUser(user);

                List<LikeDTO> likeDTOs = likes.stream().map((like) -> this.modelMapper.map(like, LikeDTO.class))
                                .collect(Collectors.toList());

                return likeDTOs;

        }


        @Override
        public LikeDTO updateLike(Integer likeId, LikeDTO likeDTO) {
            Like like = this.likeRepo.findById(likeId)
                                .orElseThrow(() -> new ResourceNotFoundException("Like", "Like Id", likeId));

            like.setIsLike(likeDTO.getIsLike());
            like.setAddedDate(new Date()); // You may want to update the date here if needed

            // Save the updated like object
            Like updatedLike = likeRepo.save(like);

            // Map the updated like object to LikeDTO and return it
            return modelMapper.map(updatedLike, LikeDTO.class);
        }

}
