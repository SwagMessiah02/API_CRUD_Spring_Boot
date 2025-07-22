package com.employee.service.employee_crud_api.controllers.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.employee.service.employee_crud_api.employee.Employee;
import com.employee.service.employee_crud_api.exceptions.EmployeeNotFoundException;
import com.employee.service.employee_crud_api.exceptions.EmployerAlreadyExistsException;
import com.employee.service.employee_crud_api.exceptions.InvalidEmployerException;
import com.employee.service.employee_crud_api.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeService {
	private EmployeeRepository repository;
	private ObjectMapper objectMapper;
	
	public ResponseEntity<List<Employee>> findAll() {
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Employee> findEmployeeById(int id) throws EmployeeNotFoundException {
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employer with id " + id + " not found"));
		
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<Employee> addEmployeer(Employee employee) throws InvalidEmployerException, EmployerAlreadyExistsException {
		if(!isValid(employee)) throw new InvalidEmployerException("Invalid employer data");	
		
		ExampleMatcher matcher = ExampleMatcher.matching()
					.withIgnorePaths("id", "first_name", "last_name")
					.withStringMatcher(StringMatcher.EXACT);
		 
		Example<Employee> example = Example.of(employee, matcher);
		
		Optional<Employee> tempEmployer = repository.findOne(example);
		
		if(!tempEmployer.isEmpty()) {
			throw new EmployerAlreadyExistsException("Employer with this email already exists");	
		}
		
		Employee newEmployer = repository.save(employee);
		
		return new ResponseEntity<Employee>(newEmployer, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<Employee> deleteEmployeeById(int id) {
		Employee employee = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee don't found"));
		
		repository.deleteById(id);
		
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<Employee> upadateEmployeeById(Employee employee, int id) {
		Employee updatedEmployee = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee don't found"));
		
		updatedEmployee.setFirst_name(employee.getFirst_name());
		updatedEmployee.setLast_name(employee.getLast_name());
		updatedEmployee.setEmail(employee.getEmail());
		
		repository.saveAndFlush(updatedEmployee);
		
		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<Employee> patchEmployeeById(Map<String, Object> mapper, int id) throws EmployeeNotFoundException {
		Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employer with id " + id + " not found"));
		
		if(mapper.containsKey("id")) {
			throw new RuntimeException("Invalid request");
		}
		
		Employee employeePatched = apply(mapper, employee);
		
		repository.saveAndFlush(employeePatched);
		
		return new ResponseEntity<Employee>(employeePatched, HttpStatus.OK);
	}
	
	private Employee apply(Map<String, Object> mapper, Employee employee) {
		ObjectNode mapperNode = objectMapper.convertValue(mapper, ObjectNode.class);
		ObjectNode employeeNode = objectMapper.convertValue(employee, ObjectNode.class);
		
		employeeNode.setAll(mapperNode);
		
		return objectMapper.convertValue(employeeNode, Employee.class);
	}
	
	private boolean isValid(Employee employee) {
		if(employee.getFirst_name() != null && employee.getLast_name() != null && 
				employee.getEmail() != null) {
			return true;
		}
		
		return false;
	}
}