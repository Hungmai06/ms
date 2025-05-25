package com.dustopia.ms_project.service;

import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedReaderStatResponse;
import com.dustopia.ms_project.model.dto.response.BorrowingResponse;
import com.dustopia.ms_project.model.dto.response.ChartResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReaderService {
    List<ChartResponse> getBorrowedReaderChart(LocalDate startDate, LocalDate endDate);

    List<BorrowedReaderStatResponse> getBorrowedReaderStats(LocalDate startDate, LocalDate endDate);

    List<BorrowingResponse> getBorrowings(LocalDate startDate, LocalDate endDate, String id);

    List<BorrowedBookResponse> getBorrowedBooksByBorrowing(LocalDate startDate, LocalDate endDate, String bookBorrowingId);


}
