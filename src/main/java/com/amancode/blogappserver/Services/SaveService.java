package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Payloads.SaveDTO;

public interface SaveService {

    // Create
    SaveDTO createSave(SaveDTO saveDTO, Integer userId, Integer postId);

    // Delete
    void deleteSave(Integer saveId);

    // Get Single Like
    SaveDTO getSaveById(Integer saveId);

    // Get All Likes By Post
    List<SaveDTO> getSaveByPost(Integer postId);

    // Get All Likes by User
    List<SaveDTO> getSaveByUser(Integer userId);

    SaveDTO updateSave(Integer saveId, SaveDTO saveDTO);
}
