package com.amancode.blogappserver.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amancode.blogappserver.Payloads.API_Response;
import com.amancode.blogappserver.Payloads.LikeDTO;
import com.amancode.blogappserver.Services.LikeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // create - Likes
    @PostMapping("/user/{userId}/post/{postId}/like")
    public ResponseEntity<LikeDTO> createLike(@RequestBody LikeDTO likeDTO, @PathVariable Integer userId,
            @PathVariable Integer postId) {

        LikeDTO createLike = this.likeService.createLike(likeDTO, userId, postId);
        return new ResponseEntity<LikeDTO>(createLike, HttpStatus.CREATED);

    }

    // GET Single Likes By Id
    @GetMapping("/like/{likeId}")
    public ResponseEntity<LikeDTO> getLikeById(@PathVariable Integer likeId) {
        return ResponseEntity.ok(this.likeService.getLikeById(likeId));
    }

    // Get Likes By Post
    @GetMapping("/post/{postId}/like")
    public ResponseEntity<List<LikeDTO>> getLikeByPost(@PathVariable Integer postId) {
        List<LikeDTO> like = this.likeService.getLikeByPost(postId);
        return new ResponseEntity<List<LikeDTO>>(like, HttpStatus.OK);
    }

    // Get Likes By User
    @GetMapping("/user/{userId}/like")
    public ResponseEntity<List<LikeDTO>> getLikeByUser(@PathVariable Integer userId) {
        List<LikeDTO> likes = this.likeService.getLikeByUser(userId);
        return new ResponseEntity<List<LikeDTO>>(likes, HttpStatus.OK);
    }

    // DELETE - delete Like
    @DeleteMapping("/like/{likeId}")
    public ResponseEntity<API_Response> deleteLike(@PathVariable("likeId") Integer lid) {
        this.likeService.deleteLike(lid);
        return new ResponseEntity<API_Response>(new API_Response("Like Deleted Successfully", true), HttpStatus.OK);
    }

    // PUT - update Like
	@PutMapping("/like/{likeId}")
	public ResponseEntity<LikeDTO> updateLike(@Valid @RequestBody LikeDTO likeDTO,
			@PathVariable Integer likeId) {
		LikeDTO updateLikes = this.likeService.updateLike(likeId,likeDTO);
		return new ResponseEntity<LikeDTO>(updateLikes, HttpStatus.OK);
	}

}
