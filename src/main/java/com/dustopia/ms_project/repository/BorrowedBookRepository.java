package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, String> {
    boolean existsByBook_IdAndBookReturningIsNull(String bookId);
    List<BorrowedBook> findByBookBorrowing_Reader_IdAndBookReturningIsNull(String readerId);
}
