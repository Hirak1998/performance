package com.myCompany.performance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a department in the organization.
 */
@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employees", "projects"})
@EqualsAndHashCode(exclude = {"employees", "projects"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private BigDecimal budget;
    
    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();
    
    @OneToMany(mappedBy = "department")
    private Set<Project> projects = new HashSet<>();
}
