package com.example.employee.controller;

import com.example.common.dto.ApiResponse;
import com.example.common.dto.EmployeeDto;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> create(@Valid @RequestBody EmployeeDto dto) {
        Employee e = service.create(dto);
        return ResponseEntity.ok(ApiResponse.ok("Employee created", e));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Employee fetched", service.get(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("Employee updated", service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Employee deleted", null));
    }
}


