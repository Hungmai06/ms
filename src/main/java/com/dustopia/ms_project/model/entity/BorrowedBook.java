package com.dustopia.ms_project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class BorrowedBook {
    @Id
    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    private String id;

    @Column(nullable = false)
    private LocalDateTime expectedReturnTime;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "book_borrowing_id")
    @JsonBackReference
    private BookBorrowing bookBorrowing;

    @ManyToOne
    @JoinColumn(name = "book_returning_id")
    @JsonBackReference
    private BookReturning bookReturning;

    @OneToMany(
            mappedBy = "borrowedBook",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<BookDamage> bookDamage;
}
