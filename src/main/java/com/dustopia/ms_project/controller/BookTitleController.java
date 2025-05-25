package com.dustopia.ms_project.controller;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookTitleRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;
import com.dustopia.ms_project.service.BookTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class BookTitleController {
     private final BookTitleService bookTitleService;

     @PostMapping("/book-titles/")
     public ResponseEntity<BookResponse> createBookTitle(@RequestBody BookTitleRequest bookTitleRequest){
         return ResponseEntity.ok(bookTitleService.createBookTitle(bookTitleRequest));
     }

     @PutMapping("/book-titles/{id}")
     public ResponseEntity<BookResponse> updateBookTitle(@PathVariable String id , @RequestBody BookTitleRequest bookTitleRequest) throws InvalidException {
         return ResponseEntity.ok(bookTitleService.updateBookTitle(id,bookTitleRequest));
     }

}
