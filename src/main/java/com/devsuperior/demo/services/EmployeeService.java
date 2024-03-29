package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.entities.Employee;
import com.devsuperior.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private DepartmentService departmentService;

    public Page<EmployeeDTO> findAll(Pageable pageable) {
        Page<Employee> employees = repository.findAll(pageable);
        return employees.map(employee -> new EmployeeDTO((employee)));
    }

    public EmployeeDTO insert(EmployeeDTO dto) {
        Employee employee = copyDTOtoEntity(dto);
        repository.save(employee);
        return new EmployeeDTO(employee);
    }

    private Employee copyDTOtoEntity(EmployeeDTO dto){
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());

        Department department = departmentService.findById(dto.getDepartmentId());
        employee.setDepartment(department);
        return employee;
    }
}
