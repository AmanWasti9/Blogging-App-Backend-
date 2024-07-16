package com.amancode.blogappserver.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.API_Response;
import com.amancode.blogappserver.Payloads.PostDTO;
import com.amancode.blogappserver.Payloads.UpdateEmailRequest;
import com.amancode.blogappserver.Payloads.UpdatePasswordRequest;
import com.amancode.blogappserver.Payloads.UpdateUserImgDTO;
import com.amancode.blogappserver.Payloads.UpdateUserInfoDTO;
import com.amancode.blogappserver.Payloads.UserDTO;
import com.amancode.blogappserver.Services.FileService;
import com.amancode.blogappserver.Services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "This is user apis to fetch, update and delete users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// POST - create user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO createdUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
	}

	// PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
			@PathVariable("userId") Integer uid) {
		UserDTO updatedUser = this.userService.updateUser(userDTO, uid);
		return ResponseEntity.ok(updatedUser);
	}

	// UserInfo name, aboiut
	@PutMapping("/{userId}/info")
	public ResponseEntity<UserDTO> updateUserInfo(@Valid @RequestBody UpdateUserInfoDTO updateUserInfoDTO,
			@PathVariable("userId") Integer userId) {
		UserDTO updatedUser = this.userService.updateUserInfo(updateUserInfoDTO, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// PUT - update password
	@PutMapping("/{userId}/password")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
			@PathVariable("userId") Integer userId) {
		this.userService.updatePassword(updatePasswordRequest.getPassword(), userId);
		return ResponseEntity.ok().build();
	}

	// PUT - update Email
	@PutMapping("/{userId}/email")
	public ResponseEntity<?> updateEmail(@Valid @RequestBody UpdateEmailRequest updateEmailRequest,
			@PathVariable("userId") Integer userId) {
		this.userService.updateEmail(updateEmailRequest.getEmail(), userId);
		return ResponseEntity.ok().build();
	}

	// DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<API_Response> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<API_Response>(new API_Response("User Deleted Successfully", true), HttpStatus.OK);
	}

	// GET - get All users
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUser() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	// GET - get user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

	// Post Image Upload
	@PostMapping("/image/upload/{userId}")
	public ResponseEntity<UserDTO> uploadUserImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer userId) throws IOException {

		// Fetch the user
		UserDTO userDTO = this.userService.getUserById(userId);

		// Upload the image and get the file name
		String fileName = this.fileService.uploadImage(path, image);

		// Create UpdateUserImgDTO and set the image name
		UpdateUserImgDTO updateUserImgDTO = new UpdateUserImgDTO();
		updateUserImgDTO.setImgName(fileName);

		// Update the user's image
		UserDTO updatedUserImg = this.userService.updateImage(updateUserImgDTO, userId);

		// Return the updated user DTO in the response entity
		return new ResponseEntity<>(updatedUserImg, HttpStatus.OK);
	}

	// Method to serve Files
	@GetMapping(value = "/image/{imgName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imgName") String imgName,
			HttpServletResponse response) throws IOException {

		InputStream resource = this.fileService.getResource(path, imgName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}
