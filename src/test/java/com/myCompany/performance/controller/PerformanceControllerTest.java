package com.myCompany.performance.controller;

import com.myCompany.performance.dto.EmployeeDetailDTO;
import com.myCompany.performance.dto.EmployeeFilterDTO;
import com.myCompany.performance.dto.EmployeeListDTO;
import com.myCompany.performance.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the PerformanceController class
 */
@WebMvcTest(PerformanceController.class)
@Import(PerformanceControllerTest.TestConfig.class)
public class PerformanceControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public EmployeeService employeeService() {
            return Mockito.mock(EmployeeService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    private SimpleDateFormat dateFormat;
    private EmployeeListDTO employeeListDTO;
    private EmployeeDetailDTO employeeDetailDTO;

    @BeforeEach
    void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Setup test data for employee list
        employeeListDTO = new EmployeeListDTO();
        employeeListDTO.setId(1L);
        employeeListDTO.setName("Name1");
        employeeListDTO.setEmail("Name1@test.com");
        employeeListDTO.setDateOfJoining(dateFormat.parse("2020-01-15"));
        employeeListDTO.setSalary(new BigDecimal("75000.00"));
        employeeListDTO.setDepartmentName("Engineering");

        // Setup test data for employee details
        employeeDetailDTO = new EmployeeDetailDTO();
        employeeDetailDTO.setId(1L);
        employeeDetailDTO.setName("Name2");
        employeeDetailDTO.setEmail("name2@test.com");
        employeeDetailDTO.setDateOfJoining(dateFormat.parse("2020-01-15"));
        employeeDetailDTO.setSalary(new BigDecimal("75000.00"));

        // Setup department
        EmployeeDetailDTO.DepartmentDTO departmentDTO = new EmployeeDetailDTO.DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("Engineering");
        departmentDTO.setBudget(new BigDecimal("1000000.00"));
        employeeDetailDTO.setDepartment(departmentDTO);

        // Setup projects
        List<EmployeeDetailDTO.ProjectDTO> projects = new ArrayList<>();
        EmployeeDetailDTO.ProjectDTO project = new EmployeeDetailDTO.ProjectDTO();
        project.setId(1L);
        project.setName("Project test");
        project.setStartDate(dateFormat.parse("2023-01-01"));
        project.setEndDate(dateFormat.parse("2023-12-31"));
        project.setRole("Developer");
        project.setAssignedDate(dateFormat.parse("2023-01-15"));
        projects.add(project);
        employeeDetailDTO.setProjects(projects);

        // Setup performance reviews
        List<EmployeeDetailDTO.PerformanceReviewDTO> reviews = new ArrayList<>();
        EmployeeDetailDTO.PerformanceReviewDTO review = new EmployeeDetailDTO.PerformanceReviewDTO();
        review.setId(1L);
        review.setReviewDate(dateFormat.parse("2023-12-15"));
        review.setScore(4.5);
        review.setReviewComments("Excellent performance");
        reviews.add(review);
        employeeDetailDTO.setRecentReviews(reviews);
    }

    /**
     * Test for API 1: getEmployeesWithFilters - Success scenario
     */
    @Test
    void testGetEmployeesWithFiltersSuccess() throws Exception {
        // Given
        List<EmployeeListDTO> employees = Collections.singletonList(employeeListDTO);
        when(employeeService.findEmployeesWithFilters(any(EmployeeFilterDTO.class))).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/employees/filter")
                .param("reviewDate", "2023-12-15")
                .param("minScore", "4.0")
                .param("departmentNames", "Engineering")
                .param("projectNames", "Project test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[0].departmentName", is("Engineering")));
    }

    /**
     * Test for API 1: getEmployeesWithFilters - Empty result scenario
     */
    @Test
    void testGetEmployeesWithFiltersEmptyResult() throws Exception {
        // Given
        List<EmployeeListDTO> employees = Collections.emptyList();
        when(employeeService.findEmployeesWithFilters(any(EmployeeFilterDTO.class))).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/api/employees/filter")
                .param("minScore", "5.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Test for API 2: getEmployeeDetails - Success scenario
     */
    @Test
    void testGetEmployeeDetailsSuccess() throws Exception {
        // Given
        when(employeeService.getEmployeeDetails(eq(1L))).thenReturn(Optional.of(employeeDetailDTO));

        // When & Then
        mockMvc.perform(get("/api/employees/1/details")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Name2")))
                .andExpect(jsonPath("$.email", is("name2@test.com")))
                .andExpect(jsonPath("$.department.name", is("Engineering")))
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].name", is("Project test")))
                .andExpect(jsonPath("$.recentReviews", hasSize(1)))
                .andExpect(jsonPath("$.recentReviews[0].score", is(4.5)));
    }

    /**
     * Test for API 2: getEmployeeDetails - Not found scenario
     */
    @Test
    void testGetEmployeeDetailsNotFound() throws Exception {
        // Given
        when(employeeService.getEmployeeDetails(eq(859L))).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/employees/859/details")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
