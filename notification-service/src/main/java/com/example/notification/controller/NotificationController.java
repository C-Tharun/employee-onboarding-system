package com.example.notification.controller;

import com.example.common.dto.ApiResponse;
import com.example.common.dto.NotificationDto;
import com.example.notification.entity.Notification;
import com.example.notification.service.NotificationAppService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationAppService service;

    public NotificationController(NotificationAppService service) {
        this.service = service;
    }

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<Notification>> sendEmail(@Valid @RequestBody NotificationDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("Notification sent (mock)", service.send(dto)));
    }

    @GetMapping("/{empId}")
    public ResponseEntity<ApiResponse<List<Notification>>> byEmployee(@PathVariable("empId") Long empId) {
        return ResponseEntity.ok(ApiResponse.ok("Notifications fetched", service.listByEmployee(empId)));
    }
}


