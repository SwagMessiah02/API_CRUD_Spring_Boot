package com.employee.service.employee_crud_api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.service.employee_crud_api.controllers.service.EmployeeService;
import com.employee.service.employee_crud_api.employee.Employee;
import com.employee.service.employee_crud_api.exceptions.EmployeeNotFoundException;
import com.employee.service.employee_crud_api.exceptions.EmployerAlreadyExistsException;
import com.employee.service.employee_crud_api.exceptions.InvalidEmployerException;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class EmployeeController {
	private EmployeeService service;
	
	@GetMapping("/employee") 
	public ResponseEntity<List<Employee>> getEmployers() {
		return service.findAll();
	}
	
	@GetMapping("/employee/{id}") 
	public ResponseEntity<Employee> getEmployee(@PathVariable int id) throws EmployeeNotFoundException {
		return service.findEmployeeById(id);
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws InvalidEmployerException, EmployerAlreadyExistsException {
		return service.addEmployeer(employee);
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable int id) {
		return service.deleteEmployeeById(id);
	}
	
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployer(@PathVariable int id, @RequestBody Employee employee) {
		return service.upadateEmployeeById(employee, id);
	}
	
	@PatchMapping("/employee/{id}")
	public ResponseEntity<Employee> patchEmployeeById(@PathVariable int id, @RequestBody Map<String, Object> mapper) throws EmployeeNotFoundException {
		return service.patchEmployeeById(mapper, id);
	}
}
