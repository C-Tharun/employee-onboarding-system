## EmployeeOnboardingSystem (SOA, Spring Boot)

### Overview
Multi-module Spring Boot 3.x system implementing a Service-Oriented Architecture for employee onboarding.

- Consumer Layer: REST controllers in each service and `onboarding-orchestrator` endpoints
- Business Process Layer: `onboarding-orchestrator` composes workflow
- Integration Layer: HTTP JSON between services (via RestTemplate)
- Service Layer: `employee-service`, `document-service`, `training-service`, `notification-service`
- Data Layer: MySQL (`employee_onboarding`) with JPA

### Modules
- `common` — shared DTOs and error models
- `employee-service` — CRUD employees (8081)
- `document-service` — upload/verify docs (8082)
- `training-service` — assign/complete training (8083)
- `notification-service` — mock email notifications persisted (8084)
- `onboarding-orchestrator` — workflow endpoints (8080)

### Prerequisites (Windows)
```powershell
winget install Oracle.JDK.21 -s winget
winget install Apache.Maven -s winget
winget install Oracle.MySQL -s winget
```
Create DB (via MySQL client/Workbench):
```sql
CREATE DATABASE employee_onboarding CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```
Verify tools:
```powershell
java -version
mvn -v
```

### Build
From `employee-onboarding-system/`:
```bash
mvn -q -DskipTests package
```

### Run
Start services in separate terminals:
```bash
mvn -pl employee-service spring-boot:run
mvn -pl document-service spring-boot:run
mvn -pl training-service spring-boot:run
mvn -pl notification-service spring-boot:run
mvn -pl onboarding-orchestrator spring-boot:run
```

Ensure MySQL credentials in each module `src/main/resources/application.yml` match your local setup (default `root/root`).

### Endpoints
- Employee Service (8081)
  - POST `/employees`
  - GET `/employees/{id}`
  - PUT `/employees/{id}`
  - DELETE `/employees/{id}`
- Document Service (8082)
  - POST `/documents/upload`
  - PUT `/documents/{id}/verify`
  - GET `/documents/{id}`
  - GET `/documents/employee/{empId}`
- Training Service (8083)
  - POST `/training/assign`
  - GET `/training/{empId}`
  - PUT `/training/{empId}/complete`
- Notification Service (8084)
  - POST `/notifications/email`
  - GET `/notifications/{empId}`
- Onboarding Orchestrator (8080)
  - POST `/onboarding/start`
  - POST `/onboarding/{empId}/document`
  - POST `/onboarding/{empId}/training/assign`
  - PUT `/onboarding/{empId}/training/complete`

### Sample cURL Flow
1) Start onboarding (creates employee and sends welcome notification):
```bash
curl -X POST http://localhost:8080/onboarding/start \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@example.com"}'
```
2) Upload and verify doc:
```bash
curl -X POST http://localhost:8080/onboarding/1/document \
  -H "Content-Type: application/json" \
  -d '{"type":"ID","location":"s3://bucket/id-1.pdf"}'
```
3) Assign training:
```bash
curl -X POST http://localhost:8080/onboarding/1/training/assign \
  -H "Content-Type: application/json" \
  -d '{"courseName":"Security 101"}'
```
4) Complete training:
```bash
curl -X PUT http://localhost:8080/onboarding/1/training/complete
```

### Notes
- Notifications are mocked: messages are logged and persisted.
- For demo simplicity, all services share one schema. In production, prefer per-service schemas and API gateways.



