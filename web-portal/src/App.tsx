import { Link, Route, Routes, Navigate } from 'react-router-dom'
import EmployeePortal from './pages/EmployeePortal'
import CompanyPortal from './pages/CompanyPortal'

export default function App() {
  return (
    <div style={{ fontFamily: 'Inter, system-ui, Arial', padding: 16 }}>
      <h1>Employee Onboarding System</h1>
      <nav style={{ display: 'flex', gap: 12, marginBottom: 16 }}>
        <Link to="/employee">Employee Portal</Link>
        <Link to="/company">Company Portal</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Navigate to="/employee" replace />} />
        <Route path="/employee" element={<EmployeePortal />} />
        <Route path="/company" element={<CompanyPortal />} />
      </Routes>
    </div>
  )
}


