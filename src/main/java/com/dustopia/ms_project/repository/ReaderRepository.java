package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, String> {

    @Query(value = """
        SELECT
            month_year,
            COUNT(*) AS borrow_count
        FROM (
            SELECT
                DATE_FORMAT(bb.time, '%m/%Y') AS month_year
            FROM
                book_borrowing bb
            JOIN
                reader r ON bb.reader_id = r.id
            WHERE
                DATE(bb.time) BETWEEN
                    COALESCE(:startDate, (SELECT MIN(DATE(time)) FROM book_borrowing))
                    AND
                    COALESCE(:endDate, (SELECT MAX(DATE(time)) FROM book_borrowing))
        ) AS sub
        GROUP BY
            month_year
        ORDER BY
            STR_TO_DATE(CONCAT('01/', month_year), '%d/%m/%Y')
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
            DATE(bb.time) BETWEEN
                COALESCE(:startDate, (SELECT MIN(DATE(time)) FROM book_borrowing))
                AND
                COALESCE(:endDate, (SELECT MAX(DATE(time)) FROM book_borrowing))
        GROUP BY
            u.id, u.username, u.full_name, u.address, u.date_of_birth, u.email
        ORDER BY
            total_borrow DESC
    """, nativeQuery = true)
    List<Object[]> findBorrowedReaderStats(LocalDate startDate, LocalDate endDate);

    Optional<Reader> findByUsername(String username);
}
