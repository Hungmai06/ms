package com.dustopia.ms_project.controller;

import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedReaderStatResponse;
import com.dustopia.ms_project.model.dto.response.BorrowingResponse;
import com.dustopia.ms_project.model.dto.response.ChartResponse;
import com.dustopia.ms_project.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ReaderController {
    private final ReaderService readerService;

    @GetMapping("/readers/borrowed/chart")
    public ResponseEntity<List<ChartResponse>> getBorrowedBookChart(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(readerService.getBorrowedReaderChart(startDate, endDate));
    }

    @GetMapping("/readers/borrowed/stats")
    public ResponseEntity<List<BorrowedReaderStatResponse>> getBorrowedBookStats(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(readerService.getBorrowedReaderStats(startDate, endDate));
    }

    @GetMapping("/readers/{id}/borrowings")
    public ResponseEntity<List<BorrowingResponse>> getBorrowings(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PathVariable String id
    ) {
        return ResponseEntity.ok(readerService.getBorrowings(startDate, endDate, id));
    }

    @GetMapping("/readers/borrowings/{bookBorrowingId}")
    public ResponseEntity<List<BorrowedBookResponse>> getBorrowedBooksByBorrowing(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PathVariable String bookBorrowingId
    ) {
        return ResponseEntity.ok(readerService.getBorrowedBooksByBorrowing(startDate, endDate, bookBorrowingId));
    }

}
