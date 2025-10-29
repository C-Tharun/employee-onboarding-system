package com.example.document.service;

import com.example.common.dto.DocumentDto;
import com.example.document.entity.Document;
import com.example.document.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class DocumentService {
    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Document upload(DocumentDto dto) {
        Document d = new Document();
        d.setEmployeeId(dto.getEmployeeId());
        d.setType(dto.getType());
        d.setLocation(dto.getLocation());
        return repository.save(d);
    }

    @Transactional
    public Document verify(Long id) {
        Document d = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Document not found"));
        d.setVerified(true);
        d.setVerifiedAt(Instant.now());
        return repository.save(d);
    }

    public Document get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Document not found"));
    }
}


