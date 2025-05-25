package com.dustopia.ms_project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Book {
    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "book_title_id")
    @JsonManagedReference
    private BookTitle bookTitle;

    @ManyToOne
    @JoinColumn(name = "book_importing_id")
    @JsonBackReference
    private BookImporting bookImporting;

    @OneToMany(
            mappedBy = "book",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonBackReference
    private List<BorrowedBook> borrowedBooks;
}
