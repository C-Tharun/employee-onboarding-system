package com.example.employee.soap;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.xml.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.format.DateTimeFormatter;

@Endpoint
public class EmployeeSoapEndpoint {
    private static final String NAMESPACE = "http://example.com/employee/soap";
    private final EmployeeService employeeService;

    public EmployeeSoapEndpoint(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getEmployeeRequest")
    @ResponsePayload
    public GetEmployeeResponse getEmployee(@RequestPayload GetEmployeeRequest request) {
        Employee e = employeeService.get(request.getId());
        GetEmployeeResponse resp = new GetEmployeeResponse();
        resp.setId(e.getId());
        resp.setFirstName(e.getFirstName());
        resp.setLastName(e.getLastName());
        resp.setEmail(e.getEmail());
        resp.setStatus(e.getStatus());
        resp.setCreatedAt(DateTimeFormatter.ISO_INSTANT.format(e.getCreatedAt()));
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createEmployeeRequest")
    @ResponsePayload
    public CreateEmployeeResponse createEmployee(@RequestPayload CreateEmployeeRequest request) {
        com.example.common.dto.EmployeeDto dto = new com.example.common.dto.EmployeeDto();
        dto.setFirstName(request.getFirstName());
        dto.setLastName(request.getLastName());
        dto.setEmail(request.getEmail());
        Employee created = employeeService.create(dto);
        CreateEmployeeResponse resp = new CreateEmployeeResponse();
        resp.setId(created.getId());
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateEmployeeRequest")
    @ResponsePayload
    public UpdateEmployeeResponse updateEmployee(@RequestPayload UpdateEmployeeRequest request) {
        com.example.common.dto.EmployeeDto dto = new com.example.common.dto.EmployeeDto();
        dto.setFirstName(request.getFirstName());
        dto.setLastName(request.getLastName());
        dto.setEmail(request.getEmail());
        dto.setStatus(request.getStatus());
        employeeService.update(request.getId(), dto);
        UpdateEmployeeResponse resp = new UpdateEmployeeResponse();
        resp.setUpdated(true);
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteEmployeeRequest")
    @ResponsePayload
    public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
        employeeService.delete(request.getId());
        DeleteEmployeeResponse resp = new DeleteEmployeeResponse();
        resp.setDeleted(true);
        return resp;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "getEmployeeRequest", namespace = NAMESPACE)
    @XmlRootElement(name = "getEmployeeRequest", namespace = NAMESPACE)
    public static class GetEmployeeRequest {
        @XmlElement(required = true)
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "getEmployeeResponse", namespace = NAMESPACE)
    @XmlRootElement(name = "getEmployeeResponse", namespace = NAMESPACE)
    public static class GetEmployeeResponse {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String status;
        private String createdAt;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "createEmployeeRequest", namespace = NAMESPACE)
    @XmlRootElement(name = "createEmployeeRequest", namespace = NAMESPACE)
    public static class CreateEmployeeRequest {
        @XmlElement(required = true)
        private String firstName;
        @XmlElement(required = true)
        private String lastName;
        @XmlElement(required = true)
        private String email;
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "createEmployeeResponse", namespace = NAMESPACE)
    @XmlRootElement(name = "createEmployeeResponse", namespace = NAMESPACE)
    public static class CreateEmployeeResponse {
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "updateEmployeeRequest", namespace = NAMESPACE)
    @XmlRootElement(name = "updateEmployeeRequest", namespace = NAMESPACE)
    public static class UpdateEmployeeRequest {
        @XmlElement(required = true)
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String status;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "updateEmployeeResponse", namespace = NAMESPACE)
    @XmlRootElement(name = "updateEmployeeResponse", namespace = NAMESPACE)
    public static class UpdateEmployeeResponse {
        private boolean updated;
        public boolean isUpdated() { return updated; }
        public void setUpdated(boolean updated) { this.updated = updated; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "deleteEmployeeRequest", namespace = NAMESPACE)
    @XmlRootElement(name = "deleteEmployeeRequest", namespace = NAMESPACE)
    public static class DeleteEmployeeRequest {
        @XmlElement(required = true)
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "deleteEmployeeResponse", namespace = NAMESPACE)
    @XmlRootElement(name = "deleteEmployeeResponse", namespace = NAMESPACE)
    public static class DeleteEmployeeResponse {
        private boolean deleted;
        public boolean isDeleted() { return deleted; }
        public void setDeleted(boolean deleted) { this.deleted = deleted; }
    }
}


