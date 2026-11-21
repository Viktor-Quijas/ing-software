import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from './layouts/MainLayout';
import { AuthLayout } from './layouts/AuthLayout';
import { Login } from './pages/Login';
import { Register } from './pages/Register';
import { MisAvances } from './pages/MisAvances';
import { ExplorarLecciones } from './pages/ExplorarLecciones';
import { LeccionTema, LeccionEjercicio } from './pages/Leccion';
import { MiPerfil } from './pages/MiPerfil';
import { ExamenDiagnostico } from './pages/ExamenDiagnostico';
import { hasHandledDiagnostic } from './utils/diagnostic';

function ProtectedRoute({ isAuthenticated, children }) {
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

// Si el usuario aún no hizo (ni omitió) el diagnóstico, lo mandamos primero ahí
function RequireDiagnostic({ children }) {
  if (!hasHandledDiagnostic()) {
    return <Navigate to="/diagnostico" replace />;
  }
  return children;
}

export default function App() {
  // Variable traída desde el backend? no se.
  const isAuthenticated = true; // ← true para probar

  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas públicas: login y registro */}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Route>

        {/* Examen diagnóstico: pantalla completa, sin sidebar */}
        <Route
          path="/diagnostico"
          element={
            <ProtectedRoute isAuthenticated={isAuthenticated}>
              <ExamenDiagnostico />
            </ProtectedRoute>
          }
        />

        {/* Rutas privadas: primero pasan por el diagnóstico */}
        <Route
          path="/"
          element={
            <ProtectedRoute isAuthenticated={isAuthenticated}>
              <RequireDiagnostic>
                <MainLayout />
              </RequireDiagnostic>
            </ProtectedRoute>
          }
        >
          <Route index element={<MisAvances />} />
          <Route path="avances" element={<MisAvances />} />
          <Route path="lecciones" element={<ExplorarLecciones />} />
          <Route path="lecciones/:id/tema" element={<LeccionTema />} />
          <Route path="lecciones/:id/ejercicio" element={<LeccionEjercicio />} />
          <Route path="perfil" element={<MiPerfil />} />
        </Route>

        <Route
          path="*"
          element={<Navigate to={isAuthenticated ? '/' : '/login'} replace />}
        />
      </Routes>
    </BrowserRouter>
  );
}