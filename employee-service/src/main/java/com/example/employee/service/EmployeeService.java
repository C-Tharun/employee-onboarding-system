package com.example.employee.service;

import com.example.common.dto.EmployeeDto;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Employee create(EmployeeDto dto) {
        repository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("Employee with email already exists");
        });
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : "REGISTERED");
        return repository.save(e);
    }

    public Employee get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }

    @Transactional
    public Employee update(Long id, EmployeeDto dto) {
        Employee e = get(id);
        if (dto.getFirstName() != null) e.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) e.setLastName(dto.getLastName());
        if (dto.getEmail() != null) e.setEmail(dto.getEmail());
        if (dto.getStatus() != null) e.setStatus(dto.getStatus());
        return repository.save(e);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}


