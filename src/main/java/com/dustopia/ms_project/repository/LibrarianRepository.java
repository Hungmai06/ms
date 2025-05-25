package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LibrarianRepository extends JpaRepository<Librarian, String> {
}
