package com.myCompany.performance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * DTO for employee filter parameters
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeFilterDTO {
    private Date reviewDate;
    private Double minScore;
    private List<String> departmentNames;
    private List<String> projectNames;

    // Constructor with fields
    public EmployeeFilterDTO(Date reviewDate, Double minScore, List<String> departmentNames, List<String> projectNames) {
        this.reviewDate = reviewDate;
        this.minScore = minScore;
        this.departmentNames = departmentNames;
        this.projectNames = projectNames;
    }
}
