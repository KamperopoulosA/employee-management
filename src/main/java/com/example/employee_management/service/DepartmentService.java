package com.example.employee_management.service;

import com.example.employee_management.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto addDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartment(Long id);
    List<DepartmentDto> getAllDepartments();
    DepartmentDto updateDepartment(DepartmentDto departmentDto , Long id);
    void deleteDepartment(Long id);

}
