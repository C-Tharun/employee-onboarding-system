package com.example.orchestrator.controller;

import com.example.common.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/onboarding")
public class OnboardingController {
    private final RestTemplate restTemplate;

    public OnboardingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${employee.service.url:http://localhost:8081}")
    private String employeeServiceUrl;

    @Value("${document.service.url:http://localhost:8082}")
    private String documentServiceUrl;

    @Value("${training.service.url:http://localhost:8083}")
    private String trainingServiceUrl;

    @Value("${notification.service.url:http://localhost:8084}")
    private String notificationServiceUrl;

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<Map<String, Object>>> start(@RequestBody EmployeeDto employee) {
        ApiResponse<?> created = restTemplate.postForObject(employeeServiceUrl + "/employees", employee, ApiResponse.class);

        NotificationDto n = new NotificationDto();
        n.setEmployeeId(((Number)((Map<?,?>)created.getData()).get("id")).longValue());
        n.setSubject("Welcome to the company");
        n.setBody("Your onboarding has started.");
        restTemplate.postForObject(notificationServiceUrl + "/notifications/email", n, ApiResponse.class);

        return ResponseEntity.ok(ApiResponse.ok("Onboarding started", Map.of("employee", created.getData())));
    }

    @PostMapping("/{empId}/document")
    public ResponseEntity<ApiResponse<Object>> uploadAndVerify(@PathVariable("empId") Long empId, @RequestBody DocumentDto doc) {
        doc.setEmployeeId(empId);
        ApiResponse<?> uploaded = restTemplate.postForObject(documentServiceUrl + "/documents/upload", doc, ApiResponse.class);
        Map<?,?> data = (Map<?,?>) uploaded.getData();
        Number idNum = (Number) data.get("id");
        restTemplate.put(documentServiceUrl + "/documents/" + idNum.longValue() + "/verify", null);

        NotificationDto n = new NotificationDto();
        n.setEmployeeId(empId);
        n.setSubject("Document verified");
        n.setBody("Your document has been verified.");
        restTemplate.postForObject(notificationServiceUrl + "/notifications/email", n, ApiResponse.class);

        return ResponseEntity.ok(ApiResponse.ok("Document uploaded and verified", uploaded.getData()));
    }

    @PostMapping("/{empId}/training/assign")
    public ResponseEntity<ApiResponse<Object>> assignTraining(@PathVariable("empId") Long empId, @RequestBody TrainingDto training) {
        training.setEmployeeId(empId);
        ApiResponse<?> assigned = restTemplate.postForObject(trainingServiceUrl + "/training/assign", training, ApiResponse.class);

        NotificationDto n = new NotificationDto();
        n.setEmployeeId(empId);
        n.setSubject("Training assigned");
        n.setBody("Please complete your onboarding training.");
        restTemplate.postForObject(notificationServiceUrl + "/notifications/email", n, ApiResponse.class);

        return ResponseEntity.ok(ApiResponse.ok("Training assigned", assigned.getData()));
    }

    @PutMapping("/{empId}/training/complete")
    public ResponseEntity<ApiResponse<Object>> completeTraining(@PathVariable("empId") Long empId) {
        restTemplate.put(trainingServiceUrl + "/training/" + empId + "/complete", null);

        NotificationDto n = new NotificationDto();
        n.setEmployeeId(empId);
        n.setSubject("Training completed");
        n.setBody("Congrats! Your onboarding training is complete.");
        restTemplate.postForObject(notificationServiceUrl + "/notifications/email", n, ApiResponse.class);

        return ResponseEntity.ok(ApiResponse.ok("Training completion recorded", null));
    }
}



