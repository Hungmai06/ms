package com.dustopia.ms_project.service.impl;

import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedReaderStatResponse;
import com.dustopia.ms_project.model.dto.response.BorrowingResponse;
import com.dustopia.ms_project.model.dto.response.ChartResponse;
import com.dustopia.ms_project.repository.BookBorrowingRepository;
import com.dustopia.ms_project.repository.ReaderRepository;
import com.dustopia.ms_project.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;
    private final BookBorrowingRepository bookBorrowingRepository;

    @Override
    public List<ChartResponse> getBorrowedReaderChart(LocalDate startDate, LocalDate endDate) {
        List<Object[]> result = readerRepository.findBorrowedReaderChart(startDate, endDate);

        return result.stream()
                .map(item -> ChartResponse.builder()
                        .label((String) item[0])
                        .value(((Number) item[1]).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<BorrowedReaderStatResponse> getBorrowedReaderStats(LocalDate startDate, LocalDate endDate) {
        List<Object[]> result = readerRepository.findBorrowedReaderStats(startDate, endDate);

        return result.stream()
                .map(item -> BorrowedReaderStatResponse.builder()
                        .id((String) item[0])
                        .username((String) item[1])
                        .fullName((String) item[2])
                        .address((String) item[3])
                        .dateOfBirth(((Date) item[4]).toLocalDate())
                        .email((String) item[5])
                        .totalBorrow(((Number) item[6]).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<BorrowingResponse> getBorrowings(LocalDate startDate, LocalDate endDate, String id) {
        List<Object[]> result = bookBorrowingRepository.findByReaderAndTime(id, startDate, endDate);

        return result.stream()
                .map(item -> BorrowingResponse.builder()
                        .id((String) item[0])
                        .time(((Timestamp) item[1]).toLocalDateTime())
                        .deposit(((Number) item[2]).floatValue())
                        .note((String) item[3])
                        .build())
                .toList();
    }

    @Override
    public List<BorrowedBookResponse> getBorrowedBooksByBorrowing(LocalDate startDate, LocalDate endDate, String bookBorrowingId) {
        List<Object[]> result = bookBorrowingRepository.findBorrowedBookByBookBorrowing(bookBorrowingId, startDate, endDate);

        return result.stream()
                .map(item -> BorrowedBookResponse.builder()
                        .borrowTime(((Timestamp) item[0]).toLocalDateTime())
                        .expectedReturnTime(Objects.nonNull(item[1]) ? ((Timestamp) item[1]).toLocalDateTime() : null)
                        .returnTime(Objects.nonNull(item[2]) ? ((Timestamp) item[2]).toLocalDateTime() : null)
                        .deposit((Float) item[3])
                        .note((String) item[4])
                        .damageDescription((String) item[5])
                        .compensate((Float) item[6])
                        .bookName((String) item[7])
                        .build())
                .toList();
    }

}
