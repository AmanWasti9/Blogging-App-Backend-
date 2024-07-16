package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Payloads.UpdateUserImgDTO;
import com.amancode.blogappserver.Payloads.UpdateUserInfoDTO;
import com.amancode.blogappserver.Payloads.UserDTO;

public interface UserService {
    
    //Register normal user
    UserDTO regieterNewUser(UserDTO user);

	//Create
    UserDTO createUser(UserDTO user);
    
    //Update
    UserDTO updateUser(UserDTO user,Integer userId);
    
    //Get Users Id
    UserDTO getUserById(Integer userId);
    
    //Get All Users
    List<UserDTO> getAllUsers();
    
    //Delete 
    void deleteUser(Integer userId);

    
    UserDTO updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO, Integer userId);

    void updatePassword(String newPassword, Integer userId);
    
    void updateEmail(String newEmail, Integer userId);

    UserDTO updateImage(UpdateUserImgDTO updateUserImgDTO, Integer userId);


}
