package com.myCompany.performance.dto;

import com.myCompany.performance.model.Department;
import com.myCompany.performance.model.Employee;
import com.myCompany.performance.model.PerformanceReview;
import com.myCompany.performance.model.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for detailed employee information including department, projects, and recent performance reviews
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDetailDTO {
    private Long id;
    private String name;
    private String email;
    private Date dateOfJoining;
    private BigDecimal salary;
    private DepartmentDTO department;
    private List<ProjectDTO> projects;
    private List<PerformanceReviewDTO> recentReviews;

    // Constructor from Employee entity
    public EmployeeDetailDTO(Employee employee, List<PerformanceReview> recentReviews) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.dateOfJoining = employee.getDateOfJoining();
        this.salary = employee.getSalary();
        
        if (employee.getDepartment() != null) {
            this.department = new DepartmentDTO(employee.getDepartment());
        }
        
        if (employee.getProjects() != null) {
            this.projects = employee.getProjects().stream()
                .map(ep -> new ProjectDTO(ep.getProject(), ep.getRole(), ep.getAssignedDate()))
                .collect(Collectors.toList());
        }
        
        if (recentReviews != null) {
            this.recentReviews = recentReviews.stream()
                .map(PerformanceReviewDTO::new)
                .collect(Collectors.toList());
        }
    }

    /**
     * Nested DTO for Department information
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class DepartmentDTO {
        private Long id;
        private String name;
        private BigDecimal budget;

        public DepartmentDTO(Department department) {
            this.id = department.getId();
            this.name = department.getName();
            this.budget = department.getBudget();
        }
    }

    /**
     * Nested DTO for Project information
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProjectDTO {
        private Long id;
        private String name;
        private Date startDate;
        private Date endDate;
        private String role;
        private Date assignedDate;

        public ProjectDTO(Project project, String role, Date assignedDate) {
            if (project != null) {
                this.id = project.getId();
                this.name = project.getName();
                this.startDate = project.getStartDate();
                this.endDate = project.getEndDate();
            }
            this.role = role;
            this.assignedDate = assignedDate;
        }
    }

    /**
     * Nested DTO for PerformanceReview information
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PerformanceReviewDTO {
        private Long id;
        private Date reviewDate;
        private Double score;
        private String reviewComments;

        public PerformanceReviewDTO(PerformanceReview review) {
            this.id = review.getId();
            this.reviewDate = review.getReviewDate();
            this.score = review.getScore();
            this.reviewComments = review.getReviewComments();
        }
    }
}
