package com.dustopia.ms_project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class BookDamage {
    @Id
    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    private String id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float compensation;

    @ManyToOne
    @JoinColumn(name = "borrowed_book_id")
    @JsonBackReference
    private BorrowedBook borrowedBook;
}
