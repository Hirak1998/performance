package com.myCompany.performance.dto;

import com.myCompany.performance.model.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO for basic employee information in list responses
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeListDTO {
    private Long id;
    private String name;
    private String email;
    private Date dateOfJoining;
    private BigDecimal salary;
    private String departmentName;

    // Constructor from Employee entity
    public EmployeeListDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.dateOfJoining = employee.getDateOfJoining();
        this.salary = employee.getSalary();
        
        if (employee.getDepartment() != null) {
            this.departmentName = employee.getDepartment().getName();
        }
    }
}
