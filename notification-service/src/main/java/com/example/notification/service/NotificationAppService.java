package com.example.notification.service;

import com.example.common.dto.NotificationDto;
import com.example.notification.entity.Notification;
import com.example.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationAppService {
    private static final Logger log = LoggerFactory.getLogger(NotificationAppService.class);
    private final NotificationRepository repository;

    public NotificationAppService(NotificationRepository repository) {
        this.repository = repository;
    }

    // Mock email: log + persist
    public Notification send(NotificationDto dto) {
        log.info("[MOCK-EMAIL] to empId={} subject='{}' body='{}'", dto.getEmployeeId(), dto.getSubject(), dto.getBody());
        Notification n = new Notification();
        n.setEmployeeId(dto.getEmployeeId());
        n.setChannel(dto.getChannel());
        n.setSubject(dto.getSubject());
        n.setBody(dto.getBody());
        return repository.save(n);
    }

    public List<Notification> listByEmployee(Long empId) {
        return repository.findByEmployeeId(empId);
    }
}


