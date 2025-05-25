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
public class BookImporting {
    @Id
    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private Supplier supplier;

    @OneToMany(
            mappedBy = "bookImporting",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Book> books;
}
