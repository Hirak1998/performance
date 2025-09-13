package com.myCompany.performance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity representing the many-to-many relationship between Employee and Project.
 */
@Entity
@Table(name = "employee_projects")
@IdClass(EmployeeProject.EmployeeProjectId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employee", "project"})
@EqualsAndHashCode(exclude = {"employee", "project"})
public class EmployeeProject {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Temporal(TemporalType.DATE)
    @Column(name = "assigned_date")
    private Date assignedDate;

    private String role;

    /**
     * Composite primary key class for EmployeeProject.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class EmployeeProjectId implements Serializable {
        private Long employee;
        private Long project;
    }
}
