package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.BookReturning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookReturningRepository extends JpaRepository<BookReturning, String> {
}
