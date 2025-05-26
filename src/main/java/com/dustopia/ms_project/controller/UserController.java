package com.dustopia.ms_project.controller;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.ReaderRequest;
import com.dustopia.ms_project.model.dto.response.ReaderResponse;
import com.dustopia.ms_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(originPatterns = "*")
public class UserController {
    private final UserService userService;
    @GetMapping("/users/")
    public ResponseEntity<List<ReaderResponse>> getAllReader(){
        return ResponseEntity.ok(userService.getAllReader());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ReaderResponse> getReaderById(@PathVariable String id){
        return ResponseEntity.ok(userService.getReaderById(id));
    }
    @PostMapping("/users/")
    public ResponseEntity<ReaderResponse>  registerReader(@RequestBody ReaderRequest request) throws InvalidException {
        return ResponseEntity.ok(userService.registerReader(request));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ReaderResponse> updateReader(@PathVariable String id, @RequestBody ReaderRequest request) throws InvalidException {
        return ResponseEntity.ok(userService.updateReader(id,request));
    }

    @DeleteMapping("/users/{id}")
    public void deleteReader(@PathVariable String id) throws InvalidException {
        userService.deleteReader(id);
    }
}
