const ORCH = import.meta.env.VITE_ORCH_URL || 'http://localhost:8080'
const EMP = import.meta.env.VITE_EMP_URL || 'http://localhost:8081'
const DOC = import.meta.env.VITE_DOC_URL || 'http://localhost:8082'
const TRN = import.meta.env.VITE_TRN_URL || 'http://localhost:8083'
const NOTI = import.meta.env.VITE_NOTI_URL || 'http://localhost:8084'

export async function startOnboarding(payload: { firstName: string; lastName: string; email: string }) {
  return fetch(`${ORCH}/onboarding/start`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) }).then(r => r.json())
}

export async function uploadAndVerifyDocument(empId: number, payload: { type: string; location: string }) {
  return fetch(`${ORCH}/onboarding/${empId}/document`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) }).then(r => r.json())
}

export async function assignTraining(empId: number, payload: { courseName: string }) {
  return fetch(`${ORCH}/onboarding/${empId}/training/assign`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) }).then(r => r.json())
}

export async function completeTraining(empId: number) {
  return fetch(`${ORCH}/onboarding/${empId}/training/complete`, { method: 'PUT' }).then(r => r.json())
}

export async function getEmployee(empId: number) { return fetch(`${EMP}/employees/${empId}`).then(r => r.json()) }
export async function getDocs(empId: number) { return fetch(`${DOC}/documents/employee/${empId}`).then(r => r.json()) }
export async function getTraining(empId: number) { return fetch(`${TRN}/training/${empId}`).then(r => r.json()) }
export async function getNotifications(empId: number) { return fetch(`${NOTI}/notifications/${empId}`).then(r => r.json()) }


