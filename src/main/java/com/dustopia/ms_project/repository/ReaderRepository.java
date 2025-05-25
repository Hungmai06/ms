package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReaderRepository extends JpaRepository<Reader, String> {
    @Query(value = """
        SELECT
            TO_CHAR(bb.time, 'MM/YYYY'),
            COUNT(*)
        FROM
            book_borrowing bb
        JOIN
            reader r ON bb.reader_id = r.id
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
    List<Object[]> findBorrowedReaderChart(LocalDate startDate, LocalDate endDate);

    @Query(value = """
        SELECT
            u.id,
            u.username,
            u.full_name,
            u.address,
            u.date_of_birth,
            u.email,
            COUNT(bb.id) AS total_borrow
        FROM
            reader r
        JOIN
            users u ON r.id = u.id
        LEFT JOIN
            book_borrowing bb ON bb.reader_id = r.id
        WHERE
            bb.time BETWEEN\s
                COALESCE(:startDate, (SELECT MIN(time) FROM book_borrowing))\s
                AND\s
                COALESCE(:endDate, (SELECT MAX(time) FROM book_borrowing))
        GROUP BY
            u.id, u.username, u.full_name, u.address, u.date_of_birth, u.email
        ORDER BY
            total_borrow DESC;
    """, nativeQuery = true)
    List<Object[]> findBorrowedReaderStats(LocalDate startDate, LocalDate endDate);

    Optional<Reader> findByUsername(String username);
}
