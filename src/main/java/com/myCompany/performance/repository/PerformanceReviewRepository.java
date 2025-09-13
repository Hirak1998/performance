package com.myCompany.performance.repository;

import com.myCompany.performance.model.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    /**
     * Find the last N performance reviews for an employee ordered by review date (descending)
     * 
     * @param employeeId The employee ID
     * @return List of performance reviews
     */
    @Query("SELECT pr FROM PerformanceReview pr " +
           "WHERE pr.employee.id = :employeeId " +
           "ORDER BY pr.reviewDate DESC")
    List<PerformanceReview> findReviewsByEmployeeIdOrderByDateDesc(@Param("employeeId") Long employeeId);
}
