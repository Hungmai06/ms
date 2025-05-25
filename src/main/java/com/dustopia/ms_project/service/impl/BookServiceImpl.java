package com.dustopia.ms_project.service.impl;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookKPIResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookStatResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.ChartResponse;
import com.dustopia.ms_project.model.entity.Book;
import com.dustopia.ms_project.model.entity.BookImporting;
import com.dustopia.ms_project.model.entity.BookTitle;
import com.dustopia.ms_project.repository.BookBorrowingRepository;
import com.dustopia.ms_project.repository.BookImportingRepository;
import com.dustopia.ms_project.repository.BookRepository;
import com.dustopia.ms_project.repository.BookTitleRepository;
import com.dustopia.ms_project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookBorrowingRepository bookBorrowingRepository;
    private final BookImportingRepository bookImportingRepository;
    private final BookTitleRepository bookTitleRepository;
    @Override
    public BorrowedBookKPIResponse getBorrowedBookKPI() {
        Object[] result = bookRepository.findBorrowedBookKPI().get(0);

        return BorrowedBookKPIResponse.builder()
                .total(((Number) result[0]).intValue())
                .totalBorrow(((Number) result[1]).intValue())
                .totalReturn(((Number) result[2]).intValue())
                .totalCompensate(((Number) result[3]).intValue())
                .build();
    }

    @Override
    public List<ChartResponse> getBorrowedBookChart(LocalDate startDate, LocalDate endDate) {
        List<Object[]> result = bookRepository.findBorrowedBookChart(startDate, endDate);

        return result.stream()
                .map(item -> ChartResponse.builder()
                        .label((String) item[0])
                        .value(((Number) item[1]).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<BorrowedBookStatResponse> getBorrowedBookStat(LocalDate startDate, LocalDate endDate) {
        List<Object[]> result = bookRepository.findBorrowedBookStats(startDate, endDate);

        return result.stream()
                .map(item -> BorrowedBookStatResponse.builder()
                        .bookTitleId((String) item[0])
                        .name((String) item[1])
                        .author((String) item[2])
                        .publicYear(((Number) item[3]).intValue())
                        .publisher((String) item[4])
                        .category((String) item[5])
                        .totalBorrow(((Number) item[6]).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<BorrowedBookResponse> getBorrowingsByBookTitle(LocalDate startDate, LocalDate endDate, String bookTitleId) {
        List<Object[]> result = bookBorrowingRepository.findBorrowedBookByBookTitle(bookTitleId, startDate, endDate);

        return result.stream()
                .map(item -> BorrowedBookResponse.builder()
                        .borrowTime(((Timestamp) item[0]).toLocalDateTime())
                        .expectedReturnTime(Objects.nonNull(item[1]) ? ((Timestamp) item[1]).toLocalDateTime() : null)
                        .returnTime(Objects.nonNull(item[2]) ? ((Timestamp) item[2]).toLocalDateTime() : null)
                        .deposit((Float) item[3])
                        .note((String) item[4])
                        .damageDescription((String) item[5])
                        .compensate((Float) item[6])
                        .build())
                .toList();
    }

    @Override
    public BookResponse findBookById(String id) throws InvalidException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new InvalidException("Book not existed");
        }
        Book book1 = book.get();
        BookImporting bookImporting = bookImportingRepository.findById(book1.getBookImporting().getId()).get();
        BookTitle bookTitle = bookTitleRepository.findById(book1.getBookTitle().getId()).get();
        return BookResponse.builder()
                .Id(book1.getId())
                .description(bookTitle.getDescription())
                .publisher(bookTitle.getPublisher())
                .author(bookTitle.getAuthor())
                .category(bookTitle.getCategory())
                .publicYear(bookTitle.getPublicYear())
                .name(bookTitle.getName())
                .nameSupplier(bookImporting.getSupplier().getName())
                .build();
    }

    @Override
    public List<BookResponse> getAllBook() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book x: books){
            BookImporting bookImporting = bookImportingRepository.findById(x.getBookImporting().getId()).get();
            BookTitle bookTitle = bookTitleRepository.findById(x.getBookTitle().getId()).get();
            bookResponses.add(BookResponse.builder()
                    .Id(x.getId())
                    .description(bookTitle.getDescription())
                    .publisher(bookTitle.getPublisher())
                    .author(bookTitle.getAuthor())
                    .category(bookTitle.getCategory())
                    .publicYear(bookTitle.getPublicYear())
                    .name(bookTitle.getName())
                    .nameSupplier(bookImporting.getSupplier().getName())
                    .build());
        }
        return bookResponses;
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        BookTitle bookTitle = bookTitleRepository.findById(bookRequest.getBook_title_id()).get();
        BookImporting bookImporting = bookImportingRepository.findById(bookRequest.getBook_importing_id()).get();
        Book book = Book.builder()
                .id(UUID.randomUUID().toString())
                .bookImporting(bookImporting)
                .bookTitle(bookTitle)
                .build();
        bookRepository.save(book);
        return BookResponse.builder()
                .Id(book.getId())
                .description(bookTitle.getDescription())
                .publisher(bookTitle.getPublisher())
                .author(bookTitle.getAuthor())
                .category(bookTitle.getCategory())
                .publicYear(bookTitle.getPublicYear())
                .name(bookTitle.getName())
                .nameSupplier(bookImporting.getSupplier().getName())
                .build();
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
