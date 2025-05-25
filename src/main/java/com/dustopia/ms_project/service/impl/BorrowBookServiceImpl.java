package com.dustopia.ms_project.service.impl;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BorrowRequest;
import com.dustopia.ms_project.model.dto.request.DamageRequest;
import com.dustopia.ms_project.model.dto.request.ReturnRequest;
import com.dustopia.ms_project.model.dto.response.BookBorrowResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.ReturnResponse;
import com.dustopia.ms_project.model.entity.Book;
import com.dustopia.ms_project.model.entity.BookBorrowing;
import com.dustopia.ms_project.model.entity.BookDamage;
import com.dustopia.ms_project.model.entity.BookReturning;
import com.dustopia.ms_project.model.entity.BookTitle;
import com.dustopia.ms_project.model.entity.BorrowedBook;
import com.dustopia.ms_project.model.entity.Librarian;
import com.dustopia.ms_project.model.entity.Reader;
import com.dustopia.ms_project.repository.BookBorrowingRepository;
import com.dustopia.ms_project.repository.BookDamageRepository;
import com.dustopia.ms_project.repository.BookRepository;
import com.dustopia.ms_project.repository.BookReturningRepository;
import com.dustopia.ms_project.repository.BookTitleRepository;
import com.dustopia.ms_project.repository.BorrowedBookRepository;
import com.dustopia.ms_project.repository.LibrarianRepository;
import com.dustopia.ms_project.repository.ReaderRepository;
import com.dustopia.ms_project.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowBookServiceImpl implements BorrowBookService {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final  BorrowedBookRepository borrowedBookRepository;
    private final BookReturningRepository bookReturningRepository;
    private final BookBorrowingRepository bookBorrowingRepository;
    private final BookDamageRepository bookDamageRepository;
    private final LibrarianRepository librarianRepository;
    private final BookTitleRepository bookTitleRepository;
    @Override
    public BookBorrowResponse borrowBooks(BorrowRequest req) {
         Reader reader = readerRepository.findById(req.getReaderId())
                .orElseThrow(() -> new InvalidException("Reader not found: " + req.getReaderId()));
          Librarian librarian = librarianRepository.findById(req.getLibrarianId())
                .orElseThrow(() -> new InvalidException("Librarian not found: " + req.getLibrarianId()));

        // 1. Tạo bản ghi BookBorrowing
        BookBorrowing borrowing = BookBorrowing.builder()
                .id(UUID.randomUUID().toString())
                .reader(reader)
                .librarian(librarian)
                .time(LocalDateTime.now())
                .deposit(req.getDeposit() != null ? req.getDeposit() : 100000f)
                .note(req.getNote())
                .build();
        bookBorrowingRepository.save(borrowing);

        // 2. Tạo danh sách BorrowedBook
        List<BorrowedBook> borrowedList = new ArrayList<>();
        for (String bookId : req.getBookIds()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new InvalidException("Book not found: " + bookId));
            // Kiểm tra sách có đang được mượn không
            boolean isBorrowed = borrowedBookRepository.existsByBook_IdAndBookReturningIsNull(bookId);
            if (isBorrowed) {
                throw new InvalidException("Book already borrowed: " + bookId);
            }
            BorrowedBook bb = BorrowedBook.builder()
                    .id(UUID.randomUUID().toString())
                    .book(book)
                    .bookBorrowing(borrowing)
                    .expectedReturnTime(req.getExpectedReturnTime())
                    .build();
            borrowedList.add(bb);
        }
        borrowedBookRepository.saveAll(borrowedList);

        borrowing.setBorrowedBooks(borrowedList);
        return BookBorrowResponse.builder()
                .id(borrowing.getId())
                .deposit(borrowing.getDeposit())
                .note(borrowing.getNote())
                .time(borrowing.getTime())
                .nameLibrarian(librarian.getFullName())
                .nameReader(reader.getFullName())
                .build();
    }

    @Override
    public ReturnResponse returnBooks(ReturnRequest req) {
        // 1. Tạo BookReturning
        Librarian librarian = librarianRepository.findById(req.getLibrarianId())
                .orElseThrow(() -> new InvalidException("Librarian not found: " + req.getLibrarianId()));

        BookReturning returning = BookReturning.builder()
                .id(UUID.randomUUID().toString())
                .librarian(librarian)
                .time(req.getReturnTime())
                .note(req.getReturnNote())
                .build();
        bookReturningRepository.save(returning);

        // 2. Lưu BookDamage nếu có trong req
        List<BookDamage> bookDamages = new ArrayList<>();
        if (req.getDamages() != null) {
            for (DamageRequest dr : req.getDamages()) {
                BorrowedBook bb = borrowedBookRepository.findById(dr.getBorrowedBookId())
                        .orElseThrow(() -> new InvalidException(
                                "BorrowedBook not found: " + dr.getBorrowedBookId()));
                BookDamage dmg = BookDamage.builder()
                        .id(UUID.randomUUID().toString())
                        .borrowedBook(bb)
                        .description(dr.getDescription())
                        .compensation(dr.getCompensation())
                        .build();
                bookDamageRepository.save(dmg);
                bookDamages.add(dmg);
            }
        }

        // 3. Cập nhật BorrowedBook: gắn BookReturning, tính phí muộn & tổng phí hỏng
        List<BorrowedBook> borrowed = borrowedBookRepository.findAllById(req.getBorrowedBookIds());
        float totalLateFee = 0f, totalDamageFee = 0f;
        for (BorrowedBook bb : borrowed) {
            bb.setBookReturning(returning);

            // Late fee: 5k/ngày
            LocalDateTime expected = bb.getExpectedReturnTime();
            if (req.getReturnTime().isAfter(expected)) {
                long daysLate = Duration.between(
                        expected.toLocalDate().atStartOfDay(),
                        req.getReturnTime().toLocalDate().atStartOfDay()
                ).toDays();
                totalLateFee += daysLate * 5000f;
            }
            // Damage fee: tổng compensation
            for (BookDamage d :bookDamages) {
                totalDamageFee += d.getCompensation();
            }

            // Lần đầu tiên set reader cho returning
            if (returning.getReader() == null) {
                returning.setReader(bb.getBookBorrowing().getReader());
            }
        }
        borrowedBookRepository.saveAll(borrowed);

        // 4. Hoàn thiện BookReturning
        returning.setBorrowedBooks(borrowed);
        String summaryNote = String.format("Late fee: %,.0f VND; Damage fee: %,.0f VND",
                totalLateFee, totalDamageFee);
        // Nếu user có note riêng, gộp vào
        if (returning.getNote() == null) {
            returning.setNote(summaryNote);
        } else {
            returning.setNote(returning.getNote() + " | " + summaryNote);
        }
        bookReturningRepository.save(returning);

        return  ReturnResponse.builder()
                .totalDamageFee(totalDamageFee)
                .totalLateFee(totalLateFee)
                .build();
    }

    @Override
    public List<BorrowedBookResponse> getCurrentBorrowedByReader(String userId) {
        List<BorrowedBook> books = borrowedBookRepository.findByBookBorrowing_Reader_IdAndBookReturningIsNull(userId);
        List<BorrowedBookResponse> list = new ArrayList<>();
        for(BorrowedBook x: books){
            BookBorrowing bookBorrowing = bookBorrowingRepository.findById(x.getBookBorrowing().getId()).orElseThrow();
            Book book = bookRepository.findById(x.getBook().getId()).orElseThrow();
            BookTitle bookTitle = bookTitleRepository.findById(book.getBookTitle().getId()).orElseThrow();
            list.add(BorrowedBookResponse.builder()
                            .expectedReturnTime(x.getExpectedReturnTime())
                            .borrowTime(bookBorrowing.getTime())
                            .compensate(0f)
                            .bookName(bookTitle.getName())
                            .damageDescription("")
                            .deposit(bookBorrowing.getDeposit())
                            .note(bookBorrowing.getNote())
                            .returnTime(null)
                    .build());
        }
        return list;
    }

}



