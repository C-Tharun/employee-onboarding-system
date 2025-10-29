package com.example.document.controller;

import com.example.common.dto.ApiResponse;
import com.example.common.dto.DocumentDto;
import com.example.document.entity.Document;
import com.example.document.repository.DocumentRepository;
import com.example.document.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService service;
    private final DocumentRepository repository;

    public DocumentController(DocumentService service, DocumentRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Document>> upload(@Valid @RequestBody DocumentDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("Document uploaded", service.upload(dto)));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<ApiResponse<Document>> verify(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Document verified", service.verify(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Document>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Document fetched", service.get(id)));
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<ApiResponse<List<Document>>> byEmployee(@PathVariable Long empId) {
        return ResponseEntity.ok(ApiResponse.ok("Documents fetched", repository.findByEmployeeId(empId)));
    }
}


