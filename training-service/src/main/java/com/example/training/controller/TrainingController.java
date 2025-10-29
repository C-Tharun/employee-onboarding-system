package com.example.training.controller;

import com.example.common.dto.ApiResponse;
import com.example.common.dto.TrainingDto;
import com.example.training.entity.Training;
import com.example.training.service.TrainingAppService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {
    private final TrainingAppService service;

    public TrainingController(TrainingAppService service) {
        this.service = service;
    }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<Training>> assign(@Valid @RequestBody TrainingDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("Training assigned", service.assign(dto)));
    }

    @GetMapping("/{empId}")
    public ResponseEntity<ApiResponse<List<Training>>> byEmployee(@PathVariable Long empId) {
        return ResponseEntity.ok(ApiResponse.ok("Training list", service.listByEmployee(empId)));
    }

    @PutMapping("/{empId}/complete")
    public ResponseEntity<ApiResponse<Training>> complete(@PathVariable Long empId) {
        return ResponseEntity.ok(ApiResponse.ok("Training completed", service.complete(empId)));
    }
}


