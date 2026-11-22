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
import { useState } from 'react';

function ProtectedRoute({ isAuthenticated, children }) {
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    () => Boolean(sessionStorage.getItem('token') && sessionStorage.getItem('userId'))
  );

  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas públicas: login y registro */}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login onLogin={() => setIsAuthenticated(true)} />} />
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
              <MainLayout />
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