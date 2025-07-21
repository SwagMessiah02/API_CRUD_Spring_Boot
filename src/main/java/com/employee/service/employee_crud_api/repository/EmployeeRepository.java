package com.employee.service.employee_crud_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.stereotype.Repository;

import com.employee.service.employee_crud_api.employee.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	public Optional<Employee> findByEmail(String email);
}
