package com.example.employee_management.service.impl;

import com.example.employee_management.dto.DepartmentDto;
import com.example.employee_management.entity.Department;
import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {

        Department department = modelMapper.map(departmentDto,Department.class);

        Department savedDepartment = departmentRepository.save(department);

        DepartmentDto savedDepartmentDto = modelMapper.map(savedDepartment,DepartmentDto.class);


        return savedDepartmentDto;

    }

    @Override
    public DepartmentDto getDepartment(Long id) {
       Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department not found with id: " + id));
        return modelMapper.map(department,DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();

        return departments.stream().map(( Department department)-> modelMapper.map(department,DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto, Long id) {
      Department department =   departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department not found with id: "+ id));
      department.setDepartmentName(departmentDto.getDepartmentName());
      department.setDepartmentDescription(departmentDto.getDepartmentDescription());

     Department updatedDepartment =  departmentRepository.save(department);

        return modelMapper.map(updatedDepartment, DepartmentDto.class);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department not found with id:"));
        departmentRepository.deleteById(id);
    }
}
