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

### CORS
APIs allow requests from the React dev server at `http://localhost:5173`.

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

### SOAP (Employee Service)
- WSDL: `http://localhost:8081/ws/employee.wsdl`
- SOAP endpoint URL (POST): `http://localhost:8081/ws`

Samples (Content-Type: text/xml):

GetEmployee
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://example.com/employee/soap">
  <soapenv:Header/>
  <soapenv:Body>
    <emp:getEmployeeRequest>
      <id>1</id>
    </emp:getEmployeeRequest>
  </soapenv:Body>
  </soapenv:Envelope>
```

CreateEmployee
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://example.com/employee/soap">
  <soapenv:Header/>
  <soapenv:Body>
    <emp:createEmployeeRequest>
      <firstName>Ada</firstName>
      <lastName>Lovelace</lastName>
      <email>ada.soap@example.com</email>
    </emp:createEmployeeRequest>
  </soapenv:Body>
  </soapenv:Envelope>
```

UpdateEmployee
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://example.com/employee/soap">
  <soapenv:Header/>
  <soapenv:Body>
    <emp:updateEmployeeRequest>
      <id>1</id>
      <firstName>Ada</firstName>
      <lastName>Lovelace</lastName>
      <email>ada.soap.updated@example.com</email>
      <status>REGISTERED</status>
    </emp:updateEmployeeRequest>
  </soapenv:Body>
  </soapenv:Envelope>
```

DeleteEmployee
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:emp="http://example.com/employee/soap">
  <soapenv:Header/>
  <soapenv:Body>
    <emp:deleteEmployeeRequest>
      <id>1</id>
    </emp:deleteEmployeeRequest>
  </soapenv:Body>
  </soapenv:Envelope>
```

Note: Orchestrator continues to use REST for Employee; SOAP is an additional interface for external/legacy systems.

### Web Portal (React)
Location: `web-portal/` (Employee Portal + Company Portal, no login).

Run:
```bash
cd web-portal
npm install
npm run dev
```
Open `http://localhost:5173`.

Configure backend URLs (optional via env):
- `VITE_ORCH_URL` (default `http://localhost:8080`)
- `VITE_EMP_URL` (default `http://localhost:8081`)
- `VITE_DOC_URL` (default `http://localhost:8082`)
- `VITE_TRN_URL` (default `http://localhost:8083`)
- `VITE_NOTI_URL` (default `http://localhost:8084`)

Employee Portal actions:
- Start onboarding (creates employee, sends notification)
- Upload & auto-verify document
- Assign and Complete training
- View notifications

Company Portal actions:
- View employee details, documents, training, and notifications by employee ID
### Notes
- Notifications are mocked: messages are logged and persisted.
- For demo simplicity, all services share one schema. In production, prefer per-service schemas and API gateways.

### Troubleshooting
- Use unique emails when starting onboarding to avoid duplicate email errors.
- If React calls fail, ensure services are running and CORS allows `http://localhost:5173`.
- Ensure Java runtime matches the compiled version (prefer Java 21+).



