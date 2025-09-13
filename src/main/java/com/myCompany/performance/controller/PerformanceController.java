package com.myCompany.performance.controller;

import com.myCompany.performance.dto.EmployeeDetailDTO;
import com.myCompany.performance.dto.EmployeeFilterDTO;
import com.myCompany.performance.dto.EmployeeListDTO;
import com.myCompany.performance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller for employee performance management APIs
 */
@RestController
@RequestMapping("/api/employees")
public class PerformanceController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * API 1: Get a list of employees with filters for performance score, departments, and projects
     * 
     * @param reviewDate The date of the performance review (optional)
     * @param minScore The minimum performance score (optional)
     * @param departmentNames List of department names to filter by (optional, contains match)
     * @param projectNames List of project names to filter by (optional, contains match)
     * @return List of employees matching the criteria
     */
    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeListDTO>> getEmployeesWithFilters(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date reviewDate,
            @RequestParam(required = false) Double minScore,
            @RequestParam(required = false) List<String> departmentNames,
            @RequestParam(required = false) List<String> projectNames) {
        
        EmployeeFilterDTO filterDTO = new EmployeeFilterDTO(
                reviewDate, minScore, departmentNames, projectNames);
        
        List<EmployeeListDTO> employees = employeeService.findEmployeesWithFilters(filterDTO);
        return ResponseEntity.ok(employees);
    }

    /**
     * API 2: Fetch detailed employee information by ID, including department, projects, 
     * and last 3 performance reviews
     * 
     * @param id The employee ID
     * @return Detailed employee information if found
     */
    @GetMapping("/{id}/details")
    public ResponseEntity<EmployeeDetailDTO> getEmployeeDetails(@PathVariable Long id) {
        Optional<EmployeeDetailDTO> employeeDetails = employeeService.getEmployeeDetails(id);
        
        return employeeDetails
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
