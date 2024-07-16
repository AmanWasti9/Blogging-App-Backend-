package com.amancode.blogappserver.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Config.AppConstants;
import com.amancode.blogappserver.Entities.*;
import com.amancode.blogappserver.Payloads.PostDTO;
import com.amancode.blogappserver.Payloads.UpdateUserImgDTO;
import com.amancode.blogappserver.Payloads.UpdateUserInfoDTO;
import com.amancode.blogappserver.Payloads.UserDTO;
import com.amancode.blogappserver.Repositories.*;
import com.amancode.blogappserver.Services.UserService;
import com.amancode.blogappserver.Exceptions.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.userToDTO(savedUser);
	}

	// @Override
	// public UserDTO updateUser(UserDTO userDTO, Integer userId) {

	// User user = this.userRepo.findById(userId).orElseThrow(()-> new
	// ResourceNotFoundException("User"," Id ",userId));

	// user.setName(userDTO.getName());
	// user.setEmail(userDTO.getEmail());
	// user.setPassword(userDTO.getPassword());
	// user.setAbout(userDTO.getAbout());

	// User updatedUser = this.userRepo.save(user);
	// UserDTO userDTO1 = this.userToDTO(updatedUser);
	// return userDTO1;
	// }

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());

		// Hashing the password before setting it
		if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
			String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
			user.setPassword(hashedPassword);
		}

		user.setAbout(userDTO.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDTO updatedUserDTO = this.userToDTO(updatedUser);
		return updatedUserDTO;
	}

	

	@Override
	public void updatePassword(String newPassword, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		user.setPassword(hashedPassword);

		this.userRepo.save(user);
	}

	// Update Email
	@Override
	public void updateEmail(String newEmail, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setEmail(newEmail);

		this.userRepo.save(user);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		return this.userToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDTOs = users.stream().map(user -> this.userToDTO(user)).collect(Collectors.toList());

		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		this.userRepo.delete(user);
	}

	public User dtoToUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);

		// User user = new User();
		// user.setId(userDTO.getId());
		// user.setName(userDTO.getName());
		// user.setEmail(userDTO.getEmail());
		// user.setAbout(userDTO.getAbout());
		// user.setPassword(userDTO.getPassword());
		return user;
	}

	public UserDTO userToDTO(User user) {
		UserDTO userDto = this.modelMapper.map(user, UserDTO.class);

		// UserDTO userDto = new UserDTO();
		// userDto.setId(user.getId());
		// userDto.setName(user.getName());
		// userDto.setEmail(user.getEmail());
		// userDto.setAbout(user.getAbout());
		// userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public UserDTO regieterNewUser(UserDTO userDTO) {

		User user = this.modelMapper.map(userDTO, User.class);

		// Encoded the Password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// Roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(updateUserInfoDTO.getName());
		user.setAbout(updateUserInfoDTO.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.userToDTO(updatedUser);
	
	}

	@Override
	public UserDTO updateImage(UpdateUserImgDTO updateUserImgDTO, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		if (updateUserImgDTO.getImgName() != null && !updateUserImgDTO.getImgName().isEmpty()) {
			user.setImgName(updateUserImgDTO.getImgName());
		}
		User updatedUser = this.userRepo.save(user);
		return this.userToDTO(updatedUser);
	}

	
        // @Override
        // public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        //         Post post = this.postRepo.findById(postId)
        //                         .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        //         Category category = this.categoryRepo.findById(postDTO.getCategory().getCategoryId()).get();
        //         if (postDTO.getImageName() != null && !postDTO.getImageName().isEmpty()) {
        //                 post.setImageName(postDTO.getImageName());
        //         }

        //         post.setTitle(postDTO.getTitle());
        //         post.setContent(postDTO.getContent());
        //         // post.setImageName(postDTO.getImageName());
        //         post.setCategory(category);
        //         post.setActive(postDTO.getActive());

        //         Post updatedPost = this.postRepo.save(post);
        //         return this.modelMapper.map(updatedPost, PostDTO.class);

        // }

	

}

// @Override
// 	public UpdateUserInfoDTO updateUserInfoDTO(UpdateUserInfoDTO updateUserInfoDTO, Integer userId) {
// 		User user = this.userRepo.findById(userId)
// 				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

// 		user.setName(updateUserInfoDTO.getName());
// 		user.setAbout(updateUserInfoDTO.getAbout());

// 		User updatedUser = this.userRepo.save(user);
// 		return this.userToDTO(updatedUser);
// 	}