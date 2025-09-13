package com.myCompany.performance.repository;

import com.myCompany.performance.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employees with filters for performance score, departments, and projects
     * 
     * @param reviewDate The date of the performance review
     * @param minScore The minimum performance score
     * @param departmentNames List of department names to filter by (contains match)
     * @param projectNames List of project names to filter by (contains match)
     * @return List of employees matching the criteria
     */
    @Query("SELECT DISTINCT e FROM Employee e " +
           "LEFT JOIN e.performanceReviews pr " +
           "LEFT JOIN e.department d " +
           "LEFT JOIN e.projects ep " +
           "LEFT JOIN ep.project p " +
           "WHERE (:reviewDate IS NULL OR pr.reviewDate = :reviewDate) " +
           "AND (:minScore IS NULL OR pr.score >= :minScore) " +
           "AND (:departmentNamesEmpty = true OR d.name IN :departmentNames) " +
           "AND (:projectNamesEmpty = true OR p.name IN :projectNames)")
    List<Employee> findEmployeesWithFilters(
            @Param("reviewDate") Date reviewDate,
            @Param("minScore") Double minScore,
            @Param("departmentNames") List<String> departmentNames,
            @Param("departmentNamesEmpty") boolean departmentNamesEmpty,
            @Param("projectNames") List<String> projectNames,
            @Param("projectNamesEmpty") boolean projectNamesEmpty);

    /**
     * Find employee by ID with department, projects, and last 3 performance reviews
     * 
     * @param id The employee ID
     * @return Optional containing the employee with related data if found
     */
    @Query("SELECT e FROM Employee e " +
           "LEFT JOIN FETCH e.department " +
           "LEFT JOIN FETCH e.projects ep " +
           "LEFT JOIN FETCH ep.project " +
           "WHERE e.id = :id")
    Optional<Employee> findEmployeeWithDetails(@Param("id") Long id);
}
