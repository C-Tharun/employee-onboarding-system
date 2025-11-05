import { useState } from 'react'
import { getEmployee, getDocs, getTraining, getNotifications } from '../lib/api'

export default function CompanyPortal() {
  const [empId, setEmpId] = useState<number | ''>('')
  const [output, setOutput] = useState<any>(null)

  const idNum = typeof empId === 'number' ? empId : Number(empId || 0)

  async function viewEmployee() { if (!idNum) return; setOutput(await getEmployee(idNum)) }
  async function viewDocs() { if (!idNum) return; setOutput(await getDocs(idNum)) }
  async function viewTraining() { if (!idNum) return; setOutput(await getTraining(idNum)) }
  async function viewNotifications() { if (!idNum) return; setOutput(await getNotifications(idNum)) }

  return (
    <div>
      <h2>Company Portal</h2>
      <div style={{ display: 'grid', gap: 8, maxWidth: 600 }}>
        <div style={{ padding: 12, border: '1px solid #ddd' }}>
          <h3>Lookup by Employee ID</h3>
          <input placeholder="Employee ID" value={empId} onChange={e => setEmpId(e.target.value === '' ? '' : Number(e.target.value))} />
          <button onClick={viewEmployee} style={{ marginLeft: 8 }}>Employee</button>
          <button onClick={viewDocs} style={{ marginLeft: 8 }}>Documents</button>
          <button onClick={viewTraining} style={{ marginLeft: 8 }}>Training</button>
          <button onClick={viewNotifications} style={{ marginLeft: 8 }}>Notifications</button>
        </div>

        <pre style={{ background: '#f7f7f7', padding: 12, overflow: 'auto' }}>{output ? JSON.stringify(output, null, 2) : 'Output will appear here.'}</pre>
      </div>
    </div>
  )
}


