package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.BookImporting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookImportingRepository extends JpaRepository<BookImporting, String> {

}
