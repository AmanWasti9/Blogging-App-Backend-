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
import com.amancode.blogappserver.Payloads.SaveDTO;
import com.amancode.blogappserver.Services.SaveService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api")
public class SaveController {
    
    @Autowired
    private SaveService saveService;

    
    // create - Save
    @PostMapping("/user/{userId}/post/{postId}/save")
    public ResponseEntity<SaveDTO> createSave(@RequestBody SaveDTO saveDTO, @PathVariable Integer userId,
            @PathVariable Integer postId) {

        SaveDTO createSave = this.saveService.createSave(saveDTO, userId, postId);
        return new ResponseEntity<SaveDTO>(createSave, HttpStatus.CREATED);

    }

    // GET Single Save By Id
    @GetMapping("/save/{saveId}")
    public ResponseEntity<SaveDTO> getSaveById(@PathVariable Integer saveId) {
        return ResponseEntity.ok(this.saveService.getSaveById(saveId));
    }

    // Get Save By Post
    @GetMapping("/post/{postId}/save")
    public ResponseEntity<List<SaveDTO>> getSaveByPost(@PathVariable Integer postId) {
        List<SaveDTO> saves = this.saveService.getSaveByPost(postId);
        return new ResponseEntity<List<SaveDTO>>(saves, HttpStatus.OK);
    }

    // Get Save By User
    @GetMapping("/user/{userId}/save")
    public ResponseEntity<List<SaveDTO>> getSaveByUser(@PathVariable Integer userId) {
        List<SaveDTO> saves = this.saveService.getSaveByUser(userId);
        return new ResponseEntity<List<SaveDTO>>(saves, HttpStatus.OK);
    }

    // DELETE - delete Save
    @DeleteMapping("/save/{saveId}")
    public ResponseEntity<API_Response> deleteSave(@PathVariable("saveId") Integer sid) {
        this.saveService.deleteSave(sid);
        return new ResponseEntity<API_Response>(new API_Response("Save Deleted Successfully", true), HttpStatus.OK);
    }

    // PUT - update Save
	@PutMapping("/save/{saveId}")
	public ResponseEntity<SaveDTO> updateSave(@Valid @RequestBody SaveDTO saveDTO,
			@PathVariable Integer saveId) {
		SaveDTO updateSave = this.saveService.updateSave(saveId,saveDTO);
		return new ResponseEntity<SaveDTO>(updateSave, HttpStatus.OK);
	}
}
