# Web Portal

Single-page React app with two sections: Employee Portal and Company Portal.

## Prerequisites
- Node 18+

## Setup
```bash
cd web-portal
npm install
# optional: copy env
# cp .env.example .env
```

## Run
```bash
npm run dev
```
Open http://localhost:5173

## Configure backend URLs
Set environment variables (Vite):
- VITE_ORCH_URL (default http://localhost:8080)
- VITE_EMP_URL (default http://localhost:8081)
- VITE_DOC_URL (default http://localhost:8082)
- VITE_TRN_URL (default http://localhost:8083)
- VITE_NOTI_URL (default http://localhost:8084)

## Features
- Employee: start onboarding, upload/verify doc, assign/complete training, view notifications
- Company: view employee details, documents, training, notifications by employee ID

