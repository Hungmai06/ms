package com.dustopia.ms_project.controller;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookKPIResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookStatResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.ChartResponse;
import com.dustopia.ms_project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books/borrowed/kpi")
    public ResponseEntity<BorrowedBookKPIResponse> getBorrowedBookKPI() {
        return ResponseEntity.ok(bookService.getBorrowedBookKPI());
    }

    @GetMapping("/books/borrowed/chart")
    public ResponseEntity<List<ChartResponse>> getBorrowedBookChart(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(bookService.getBorrowedBookChart(startDate, endDate));
    }

    @GetMapping("/books/borrowed/stats")
    public ResponseEntity<List<BorrowedBookStatResponse>> getBorrowedBookStats(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(bookService.getBorrowedBookStat(startDate, endDate));
    }

    @GetMapping("/book-title/{bookTitleId}/borrowings")
    public ResponseEntity<List<BorrowedBookResponse>> getBorrowingsByBookTitle(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PathVariable String bookTitleId
    ) {
        return ResponseEntity.ok(bookService.getBorrowingsByBookTitle(startDate, endDate, bookTitleId));
    }

    @GetMapping("/books/")
    public ResponseEntity<List<BookResponse>> getAllBook(){
        return ResponseEntity.ok(bookService.getAllBook());
    }
    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable String id) throws InvalidException {
        return ResponseEntity.ok(bookService.findBookById(id));
    }
    @PostMapping("/books/")
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request){
        return  ResponseEntity.ok(bookService.createBook(request));
    }
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable String id){
        bookService.deleteBook(id);
    }
}

