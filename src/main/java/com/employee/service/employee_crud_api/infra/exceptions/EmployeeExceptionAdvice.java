package com.employee.service.employee_crud_api.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.service.employee_crud_api.dto.EmployeeNotFoundDTO;
import com.employee.service.employee_crud_api.exceptions.EmployeeNotFoundException;
import com.employee.service.employee_crud_api.exceptions.EmployerAlreadyExistsException;
import com.employee.service.employee_crud_api.exceptions.InvalidEmployerException;

@ControllerAdvice
public class EmployeeExceptionAdvice {
	
	@ExceptionHandler(exception = EmployeeNotFoundException.class)
	public ResponseEntity<EmployeeNotFoundDTO> employeeNotFoundExcetionHandler(EmployeeNotFoundException exception) {
		EmployeeNotFoundDTO employeeNotFoundDto = new EmployeeNotFoundDTO();
		
		employeeNotFoundDto.setStatus(HttpStatus.BAD_REQUEST.value());
		employeeNotFoundDto.setMessage(exception.getMessage());
		
		return new ResponseEntity<>(employeeNotFoundDto, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = InvalidEmployerException.class)
	public ResponseEntity<EmployeeNotFoundDTO> invalidEmployerExceptionHandler(InvalidEmployerException exception) {
		EmployeeNotFoundDTO employeeNotFoundDTO = new EmployeeNotFoundDTO();
		
		employeeNotFoundDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		employeeNotFoundDTO.setMessage(exception.getMessage());
		
		return new ResponseEntity<>(employeeNotFoundDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = EmployerAlreadyExistsException.class)
	public ResponseEntity<EmployeeNotFoundDTO> EmployerAlreadyExistsExceptionHandler(EmployerAlreadyExistsException exception) {
		EmployeeNotFoundDTO employeeNotFoundDTO = new EmployeeNotFoundDTO();
		
		employeeNotFoundDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		employeeNotFoundDTO.setMessage(exception.getMessage());
		
		return new ResponseEntity<>(employeeNotFoundDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<EmployeeNotFoundDTO> runTimeExceptionHandler(Exception exception) {
		EmployeeNotFoundDTO employeeNotFoundDTO = new EmployeeNotFoundDTO();
		
		employeeNotFoundDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		employeeNotFoundDTO.setMessage("Invalid request body");
		
		return new ResponseEntity<>(employeeNotFoundDTO, HttpStatus.BAD_REQUEST);
	}
}
