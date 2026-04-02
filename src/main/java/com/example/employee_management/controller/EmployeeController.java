package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDto;
import com.example.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@PathVariable Long departmentId, @RequestBody EmployeeDto employeeDto){
       EmployeeDto savedEmployee =  employeeService.addEmployee(departmentId,employeeDto);
       return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);

    }

    @GetMapping("/{departmentId}/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long departmentId , @PathVariable("id") Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeeById(departmentId,employeeId);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesByDepartmentId(@PathVariable("id") Long departmentId){
       List<EmployeeDto> employeeDtos =  employeeService.getAllEmployeesByDepartmentId(departmentId);
       return new ResponseEntity<>(employeeDtos,HttpStatus.OK);
    }

    @PutMapping("/{id}/employees/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long departmentId,@PathVariable("employeeId") Long employeeId,@RequestBody EmployeeDto employeeDto ){
        EmployeeDto updateEmployee = employeeService.updateEmployee(departmentId,employeeId,employeeDto);
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/{departmentId}/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long departmentId ,@PathVariable Long employeeId){
        employeeService.deleteEmployee(departmentId,employeeId);
        return ResponseEntity.ok("Employee deleted sucessfully.");
    }
}
