package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query(value = """
        SELECT
            (SELECT COUNT(*) FROM borrowed_book),
            (SELECT COUNT(*) FROM book_borrowing),
            (SELECT COUNT(*) FROM book_returning),
            (SELECT COALESCE(SUM(compensation), 0) FROM book_damage);
    """, nativeQuery = true)
    List<Object[]> findBorrowedBookKPI();

    @Query(value = """
        SELECT
            TO_CHAR(bb.time, 'MM/YYYY'),
            COUNT(*)
        FROM
            book_borrowing bb
        JOIN
            borrowed_book b ON bb.id = b.book_borrowing_id
        WHERE
            bb.time BETWEEN\s
                COALESCE(:startDate, (SELECT MIN(time) FROM book_borrowing))\s
                AND\s
                COALESCE(:endDate, (SELECT MAX(time) FROM book_borrowing))
        GROUP BY
            TO_CHAR(bb.time, 'MM/YYYY')
        ORDER BY
            TO_DATE(TO_CHAR(bb.time, 'MM/YYYY'), 'MM/YYYY')
    """, nativeQuery = true)
    List<Object[]> findBorrowedBookChart(LocalDate startDate, LocalDate endDate);

    @Query(value = """
        SELECT
            bt.id,
            bt.name,
            bt.author,
            bt.public_year,
            bt.publisher,
            bt.category,
            COUNT(bb.id) AS total_borrow
        FROM
            book_title bt
        JOIN
            book b ON bt.id = b.book_title_id
        JOIN
            borrowed_book bb ON b.id = bb.book_id
        JOIN
            book_borrowing bbor ON bb.book_borrowing_id = bbor.id
        WHERE
            bbor.time BETWEEN
                COALESCE(:startDate, (SELECT MIN(time) FROM book_borrowing))
                AND
                COALESCE(:endDate, (SELECT MAX(time) FROM book_borrowing))
        GROUP BY
            bt.id, bt.name, bt.author, bt.public_year, bt.publisher, bt.category
        ORDER BY
            total_borrow DESC
    """, nativeQuery = true)
    List<Object[]> findBorrowedBookStats(LocalDate startDate, LocalDate endDate);
}
