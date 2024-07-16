package com.amancode.blogappserver.Services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.Save;
import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.SaveDTO;
import com.amancode.blogappserver.Repositories.PostRepo;
import com.amancode.blogappserver.Repositories.SaveRepo;
import com.amancode.blogappserver.Repositories.UserRepo;
import com.amancode.blogappserver.Services.SaveService;

@Service
public class SaveServiceImpl implements SaveService {

    @Autowired
    private SaveRepo saveRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public SaveDTO createSave(SaveDTO saveDTO, Integer userId, Integer postId) {
        // Retrieve the User object
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        // Retrieve the Post object
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        // Check if a like already exists for the given user and post
        Save existingSave = saveRepo.findByUserAndPost(user, post);
        if (existingSave != null) {
            throw new IllegalStateException("User has already saved this post.");
        }

        // Create a new Like object
        Save savePost = modelMapper.map(saveDTO, Save.class);
        savePost.setAddedDate(new Date());
        savePost.setUser(user);
        savePost.setPost(post);
        savePost.setIsSave(saveDTO.getIsSave());

        // Save the new Like object
        Save newSave = saveRepo.save(savePost);

        // Map the new Like object to LikeDTO and return it
        return modelMapper.map(newSave, SaveDTO.class);
    }

    @Override
    public void deleteSave(Integer saveId) {
        Save save = this.saveRepo.findById(saveId)
                .orElseThrow(() -> new ResourceNotFoundException("Save", "Save Id", saveId));

        this.saveRepo.delete(save);
    }

    @Override
    public SaveDTO getSaveById(Integer saveId) {
        Save save = this.saveRepo.findById(saveId)
                .orElseThrow(() -> new ResourceNotFoundException("Save", "Save Id", saveId));

        return this.modelMapper.map(save, SaveDTO.class);
    }

    @Override
    public List<SaveDTO> getSaveByPost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        List<Save> saves = this.saveRepo.findByPost(post);

        List<SaveDTO> saveDTOs = saves.stream().map((save) -> this.modelMapper.map(save, SaveDTO.class))
                .collect(Collectors.toList());

        return saveDTOs;
    }

    @Override
    public List<SaveDTO> getSaveByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        List<Save> saves = this.saveRepo.findByUser(user);

        List<SaveDTO> saveDTOs = saves.stream().map((save) -> this.modelMapper.map(save, SaveDTO.class))
                .collect(Collectors.toList());

        return saveDTOs;

    }

    @Override
    public SaveDTO updateSave(Integer saveId, SaveDTO saveDTO) {
        Save save = this.saveRepo.findById(saveId)
                .orElseThrow(() -> new ResourceNotFoundException("Save", "Save Id", saveId));

        save.setIsSave(saveDTO.getIsSave());
        save.setAddedDate(new Date()); // You may want to update the date here if needed

        // Save the updated like object
        Save updatedSave = saveRepo.save(save);

        // Map the updated like object to LikeDTO and return it
        return modelMapper.map(updatedSave, SaveDTO.class);
    }

}
