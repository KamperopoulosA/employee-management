package com.example.employee_management.controller;

import com.example.employee_management.dto.DepartmentDto;
import com.example.employee_management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto savedDepartmentDto = departmentService.addDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartmentDto, HttpStatus.CREATED);
    }

    @GetMapping("id")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("id") Long id){
        DepartmentDto dto = departmentService.getDepartment(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @PutMapping
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto , @PathVariable("id") Long departmentId){
        DepartmentDto updateDepartment = departmentService.updateDepartment(departmentDto,departmentId);
        return  ResponseEntity.ok(updateDepartment);
    }

    @DeleteMapping("id")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return  ResponseEntity.ok("Department deleted successfuly!");

    }
}
