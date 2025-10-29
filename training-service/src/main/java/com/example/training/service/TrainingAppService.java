package com.example.training.service;

import com.example.common.dto.TrainingDto;
import com.example.training.entity.Training;
import com.example.training.repository.TrainingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TrainingAppService {
    private final TrainingRepository repository;

    public TrainingAppService(TrainingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Training assign(TrainingDto dto) {
        Training t = new Training();
        t.setEmployeeId(dto.getEmployeeId());
        t.setCourseName(dto.getCourseName());
        return repository.save(t);
    }

    public List<Training> listByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Transactional
    public Training complete(Long employeeId) {
        return repository.findByEmployeeId(employeeId).stream().findFirst()
                .map(t -> { t.setCompleted(true); t.setCompletedAt(Instant.now()); return repository.save(t); })
                .orElseThrow(() -> new IllegalArgumentException("No training assigned for employee"));
    }
}


