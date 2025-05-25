package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.BookBorrowing;
import com.dustopia.ms_project.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, String> {
    @Query(value = """
        SELECT
            bbor.time,
            bb.expected_return_time,
            br.time,
            bbor.deposit,
            bbor.note,
            bd.description,
            bd.compensation
        FROM
            borrowed_book bb
        JOIN
            book_borrowing bbor ON bb.book_borrowing_id = bbor.id
        LEFT JOIN
            book_returning br ON bb.book_returning_id = br.id
        JOIN
            book b ON bb.book_id = b.id
        JOIN
            book_title bt ON b.book_title_id = bt.id
        LEFT JOIN
            book_damage bd ON bd.borrowed_book_id = bb.id
        WHERE
            bt.id = :bookTitleId
            AND
            bbor.time BETWEEN
                COALESCE(:startDate, (SELECT MIN(time) FROM book_borrowing))
                AND
                COALESCE(:endDate, (SELECT MAX(time) FROM book_borrowing))
        ORDER BY
            bbor.time DESC
    """, nativeQuery = true)
    List<Object[]> findBorrowedBookByBookTitle(String bookTitleId, LocalDate startDate, LocalDate endDate);

    @Query(value = """
        SELECT
            bbor.time,
            bb.expected_return_time,
            br.time,
            bbor.deposit,
            bbor.note,
            bd.description,
            bd.compensation,
            bt.name
        FROM
            borrowed_book bb
        JOIN
            book_borrowing bbor ON bb.book_borrowing_id = bbor.id
        LEFT JOIN
            book_returning br ON bb.book_returning_id = br.id
        JOIN
            book b ON bb.book_id = b.id
        JOIN
            book_title bt ON b.book_title_id = bt.id
        LEFT JOIN
            book_damage bd ON bd.borrowed_book_id = bb.id
        WHERE
            bbor.id = :bookBorrowingId
            AND
            bbor.time BETWEEN
                COALESCE(:startDate, (SELECT MIN(time) FROM book_borrowing))
                AND
                COALESCE(:endDate, (SELECT MAX(time) FROM book_borrowing))
        ORDER BY
            bbor.time DESC
    """, nativeQuery = true)
    List<Object[]> findBorrowedBookByBookBorrowing(String bookBorrowingId, LocalDate startDate, LocalDate endDate);

    @Query(value = """
        SELECT 
            bbor.id,
            bbor.time,
            bbor.deposit,
            bbor.note
        FROM 
            book_borrowing bbor
        WHERE
            bbor.reader_id = :readerId
            AND
            bbor.time BETWEEN
                COALESCE(:startDate, (SELECT MIN(time) FROM book_borrowing))
                AND
                COALESCE(:endDate, (SELECT MAX(time) FROM book_borrowing))
    """, nativeQuery = true)
    List<Object[]> findByReaderAndTime(String readerId, LocalDate startDate, LocalDate endDate);
}
