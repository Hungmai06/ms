package com.dustopia.ms_project.repository;

import com.dustopia.ms_project.model.entity.BookDamage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookDamageRepository extends JpaRepository<BookDamage, String> {
}
