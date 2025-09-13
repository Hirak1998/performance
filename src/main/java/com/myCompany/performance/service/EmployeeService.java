package com.myCompany.performance.service;

import com.myCompany.performance.dto.EmployeeDetailDTO;
import com.myCompany.performance.dto.EmployeeFilterDTO;
import com.myCompany.performance.dto.EmployeeListDTO;
import com.myCompany.performance.model.Employee;
import com.myCompany.performance.model.PerformanceReview;
import com.myCompany.performance.repository.EmployeeRepository;
import com.myCompany.performance.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    /**
     * Find employees with filters
     * 
     * @param filterDTO The filter parameters
     * @return List of employees matching the criteria
     */
    @Transactional(readOnly = true)
    public List<EmployeeListDTO> findEmployeesWithFilters(EmployeeFilterDTO filterDTO) {
        List<String> departmentNames = filterDTO.getDepartmentNames();
        List<String> projectNames = filterDTO.getProjectNames();
        
        boolean departmentNamesEmpty = departmentNames == null || departmentNames.isEmpty();
        boolean projectNamesEmpty = projectNames == null || projectNames.isEmpty();
        
        // Provide empty lists instead of null to avoid SQL errors
        if (departmentNames == null) {
            departmentNames = Collections.emptyList();
        }
        
        if (projectNames == null) {
            projectNames = Collections.emptyList();
        }
        
        List<Employee> employees = employeeRepository.findEmployeesWithFilters(
                filterDTO.getReviewDate(),
                filterDTO.getMinScore(),
                departmentNames,
                departmentNamesEmpty,
                projectNames,
                projectNamesEmpty);
        
        return employees.stream()
                .map(EmployeeListDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get detailed employee information by ID
     * 
     * @param id The employee ID
     * @return Optional containing the employee details if found
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDetailDTO> getEmployeeDetails(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findEmployeeWithDetails(id);
        
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            
            // Get the last 3 performance reviews
            List<PerformanceReview> recentReviews = 
                performanceReviewRepository.findReviewsByEmployeeIdOrderByDateDesc(id)
                    .stream()
                    .limit(3)
                    .collect(Collectors.toList());
            
            return Optional.of(new EmployeeDetailDTO(employee, recentReviews));
        }
        
        return Optional.empty();
    }
}
