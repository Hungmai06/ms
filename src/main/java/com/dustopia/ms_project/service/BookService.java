package com.dustopia.ms_project.service;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookKPIResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookStatResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.ChartResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookService {
    BorrowedBookKPIResponse getBorrowedBookKPI();

    List<ChartResponse> getBorrowedBookChart(LocalDate startMonth, LocalDate endMonth);

    List<BorrowedBookStatResponse> getBorrowedBookStat(LocalDate startDate, LocalDate endDate);

    List<BorrowedBookResponse> getBorrowingsByBookTitle(LocalDate startDate, LocalDate endDate, String bookTitleId);

    BookResponse findBookById(String id) throws InvalidException;

    List<BookResponse> getAllBook();

    BookResponse createBook(BookRequest bookRequest);

    void deleteBook(String id);
}
