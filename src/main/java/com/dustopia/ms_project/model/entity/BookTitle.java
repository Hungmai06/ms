package com.dustopia.ms_project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class BookTitle {
    @Id
    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    private String id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer publicYear;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "bookTitle")
    @JsonBackReference
    private List<Book> books;
}
