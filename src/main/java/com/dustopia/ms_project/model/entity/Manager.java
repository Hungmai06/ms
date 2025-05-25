package com.dustopia.ms_project.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Manager extends Employee {
    @OneToMany(
            mappedBy = "manager",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<BookImporting> bookImportings;
}
