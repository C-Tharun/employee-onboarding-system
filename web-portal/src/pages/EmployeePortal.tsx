import { useState } from 'react'
import { assignTraining, completeTraining, startOnboarding, uploadAndVerifyDocument, getNotifications } from '../lib/api'

export default function EmployeePortal() {
  const [empId, setEmpId] = useState<number | null>(null)
  const [email, setEmail] = useState('')
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [docType, setDocType] = useState('ID')
  const [docLoc, setDocLoc] = useState('')
  const [course, setCourse] = useState('Security 101')
  const [output, setOutput] = useState<any>(null)

  async function doStart() {
    const res = await startOnboarding({ firstName, lastName, email })
    setOutput(res)
    const id = (res?.data?.employee?.id as number) || null
    setEmpId(id)
  }

  async function doDoc() {
    if (!empId) return
    const res = await uploadAndVerifyDocument(empId, { type: docType, location: docLoc })
    setOutput(res)
  }

  async function doAssign() {
    if (!empId) return
    const res = await assignTraining(empId, { courseName: course })
    setOutput(res)
  }

  async function doComplete() {
    if (!empId) return
    const res = await completeTraining(empId)
    setOutput(res)
  }

  async function viewNotifications() {
    if (!empId) return
    const res = await getNotifications(empId)
    setOutput(res)
  }

  return (
    <div>
      <h2>Employee Portal</h2>
      <div style={{ display: 'grid', gap: 8, maxWidth: 600 }}>
        <div style={{ padding: 12, border: '1px solid #ddd' }}>
          <h3>Start Onboarding</h3>
          <input placeholder="First name" value={firstName} onChange={e => setFirstName(e.target.value)} />
          <input placeholder="Last name" value={lastName} onChange={e => setLastName(e.target.value)} style={{ marginLeft: 8 }} />
          <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} style={{ marginLeft: 8, width: 280 }} />
          <button onClick={doStart} style={{ marginLeft: 8 }}>Start</button>
          {empId && <span style={{ marginLeft: 12 }}>Employee ID: {empId}</span>}
        </div>

        <div style={{ padding: 12, border: '1px solid #ddd' }}>
          <h3>Upload & Verify Document</h3>
          <input placeholder="Type" value={docType} onChange={e => setDocType(e.target.value)} />
          <input placeholder="Location" value={docLoc} onChange={e => setDocLoc(e.target.value)} style={{ marginLeft: 8, width: 380 }} />
          <button onClick={doDoc} style={{ marginLeft: 8 }}>Submit</button>
        </div>

        <div style={{ padding: 12, border: '1px solid #ddd' }}>
          <h3>Training</h3>
          <input placeholder="Course name" value={course} onChange={e => setCourse(e.target.value)} />
          <button onClick={doAssign} style={{ marginLeft: 8 }}>Assign</button>
          <button onClick={doComplete} style={{ marginLeft: 8 }}>Complete</button>
        </div>

        <div style={{ padding: 12, border: '1px solid #ddd' }}>
          <h3>Notifications</h3>
          <button onClick={viewNotifications}>View My Notifications</button>
        </div>

        <pre style={{ background: '#f7f7f7', padding: 12, overflow: 'auto' }}>{output ? JSON.stringify(output, null, 2) : 'Output will appear here.'}</pre>
      </div>
    </div>
  )
}


