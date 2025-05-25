package com.dustopia.ms_project.controller;

import com.dustopia.ms_project.model.dto.request.BorrowRequest;
import com.dustopia.ms_project.model.dto.request.ReturnRequest;
import com.dustopia.ms_project.model.dto.response.BookBorrowResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.ReturnResponse;
import com.dustopia.ms_project.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class BorrowBookController {
    private final BorrowBookService borrowBookService;

    @PostMapping("/borrows/")
    public ResponseEntity<BookBorrowResponse> borrowBook(@RequestBody BorrowRequest request){
        return ResponseEntity.ok(borrowBookService.borrowBooks(request));
    }
    @PostMapping("/returns/")
    public ResponseEntity<ReturnResponse> returnBook(@RequestBody ReturnRequest request){
        return ResponseEntity.ok(borrowBookService.returnBooks(request));
    }

    @GetMapping("/borrowed-not-return/{userId}")
    public ResponseEntity<List<BorrowedBookResponse>> getBookNotReturn(@PathVariable String userId){
        return ResponseEntity.ok(borrowBookService.getCurrentBorrowedByReader(userId));
    }
}
