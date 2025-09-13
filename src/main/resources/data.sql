-- Insert departments
INSERT INTO departments (id, name, budget) VALUES 
(1, 'Engineering', 1000000.00),
(2, 'Marketing', 500000.00),
(3, 'Human Resources', 300000.00),
(4, 'Finance', 400000.00),
(5, 'Sales', 600000.00);

-- Insert employees (first without manager relationships)
INSERT INTO employees (id, name, email, department_id, date_of_joining, salary) VALUES 
(1, 'John Smith', 'john.smith@company.com', 1, '2020-01-15', 120000.00),
(2, 'Emily Johnson', 'emily.johnson@company.com', 1, '2020-03-20', 110000.00),
(3, 'Michael Brown', 'michael.brown@company.com', 1, '2020-05-10', 105000.00),
(4, 'Sarah Davis', 'sarah.davis@company.com', 2, '2020-02-05', 95000.00),
(5, 'David Wilson', 'david.wilson@company.com', 2, '2020-04-12', 90000.00),
(6, 'Jennifer Miller', 'jennifer.miller@company.com', 3, '2020-06-18', 85000.00),
(7, 'Robert Taylor', 'robert.taylor@company.com', 3, '2020-07-22', 80000.00),
(8, 'Lisa Anderson', 'lisa.anderson@company.com', 4, '2020-08-30', 95000.00),
(9, 'James Thomas', 'james.thomas@company.com', 4, '2020-09-15', 90000.00),
(10, 'Patricia Martinez', 'patricia.martinez@company.com', 5, '2020-10-05', 100000.00),
(11, 'Richard Robinson', 'richard.robinson@company.com', 5, '2020-11-10', 95000.00);

-- Update employees with manager relationships
UPDATE employees SET manager_id = 1 WHERE id IN (2, 3);
UPDATE employees SET manager_id = 4 WHERE id IN (5);
UPDATE employees SET manager_id = 6 WHERE id IN (7);
UPDATE employees SET manager_id = 8 WHERE id IN (9);
UPDATE employees SET manager_id = 10 WHERE id IN (11);

-- Insert projects
INSERT INTO projects (id, name, start_date, end_date, department_id) VALUES 
(1, 'Mobile App Development', '2023-01-01', '2023-06-30', 1),
(2, 'Website Redesign', '2023-02-15', '2023-08-15', 1),
(3, 'Marketing Campaign', '2023-03-01', '2023-05-31', 2),
(4, 'Employee Training Program', '2023-04-01', '2023-07-31', 3),
(5, 'Financial Reporting System', '2023-05-01', '2023-11-30', 4),
(6, 'Sales Strategy Development', '2023-06-01', '2023-09-30', 5);

-- Insert employee-project relationships
INSERT INTO employee_projects (employee_id, project_id, assigned_date, role) VALUES 
(1, 1, '2023-01-01', 'Project Manager'),
(2, 1, '2023-01-05', 'Developer'),
(3, 1, '2023-01-10', 'Developer'),
(1, 2, '2023-02-15', 'Technical Lead'),
(2, 2, '2023-02-20', 'Developer'),
(3, 2, '2023-02-25', 'Developer'),
(4, 3, '2023-03-01', 'Marketing Lead'),
(5, 3, '2023-03-05', 'Marketing Specialist'),
(6, 4, '2023-04-01', 'HR Lead'),
(7, 4, '2023-04-05', 'HR Specialist'),
(8, 5, '2023-05-01', 'Finance Lead'),
(9, 5, '2023-05-05', 'Finance Analyst'),
(10, 6, '2023-06-01', 'Sales Lead'),
(11, 6, '2023-06-05', 'Sales Representative');

-- Insert performance reviews
INSERT INTO performance_reviews (id, employee_id, review_date, score, review_comments) VALUES 
(1, 2, '2023-06-30', 4.5, 'Excellent performance on the Mobile App Development project. Consistently delivered high-quality code ahead of schedule.'),
(2, 3, '2023-06-30', 4.2, 'Very good performance on the Mobile App Development project. Showed great problem-solving skills.'),
(3, 5, '2023-05-31', 4.0, 'Good performance on the Marketing Campaign. Contributed creative ideas that improved campaign effectiveness.'),
(4, 7, '2023-07-31', 3.8, 'Good performance on the Employee Training Program. Helped develop comprehensive training materials.'),
(5, 9, '2023-11-30', 4.3, 'Very good performance on the Financial Reporting System. Demonstrated strong analytical skills.'),
(6, 11, '2023-09-30', 4.1, 'Very good performance on the Sales Strategy Development. Exceeded sales targets by 15%.');
