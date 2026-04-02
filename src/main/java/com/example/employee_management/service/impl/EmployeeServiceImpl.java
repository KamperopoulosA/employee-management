package com.example.employee_management.service.impl;

import com.example.employee_management.dto.EmployeeDto;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.BadRequestException;
import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto addEmployee(Long departmentId, EmployeeDto employeeDto) {

       Department department = departmentRepository.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("Department not found with id " + departmentId));
        Employee employee = modelMapper.map(employeeDto,Employee.class);
        employee.setDepartment(department);
       Employee savedEmployee = employeeRepository.save(employee);

       EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);
       savedEmployeeDto.setDepartmentId(departmentId);
        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long departmentId, Long employeeId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+departmentId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+employeeId));

        if(!employee.getDepartment().getId().equals(department.getId())){
            throw new BadRequestException("This employee does not belong to the department with id " + departmentId);
        }
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setDepartmentId(employee.getDepartment().getId());
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployeesByDepartmentId(Long departmentId) {
      Department department = departmentRepository.findById(departmentId)
              .orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+departmentId));

           List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);

        return employees.stream().map((employee)-> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long departmentId, Long employeeId, EmployeeDto employeeDto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+departmentId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+employeeId));

        if(!employee.getDepartment().getId().equals(department.getId())){
            throw new BadRequestException("This employee does not belong to the department with id " + departmentId);
        }
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Employee updatedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = modelMapper.map(updatedEmployee, EmployeeDto.class);
        return savedEmployeeDto;
    }

    @Override
    public void deleteEmployee(Long departmentId, Long employeeId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+departmentId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id:"+employeeId));

        if(!employee.getDepartment().getId().equals(department.getId())){
            throw new BadRequestException("This employee does not belong to the department with id " + departmentId);
        }

        employeeRepository.delete(employee);
    }
}
