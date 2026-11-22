import { BrowserRouter, Routes, Route, Navigate, useLocation, useParams } from 'react-router-dom';
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
import { hasHandledDiagnostic } from './utils/diagnostic';
import { isTokenValid, clearSession } from './utils/auth';

function ProtectedRoute({ children }) {
  useLocation(); 
  if (!isTokenValid()) {
    clearSession();
    return <Navigate to="/login" replace />;
  }
  return children;
}

function RequireDiagnostic({ children }) {
  if (!hasHandledDiagnostic()) {
    return <Navigate to="/diagnostico" replace />;
  }
  return children;
}

function CatchAll () {
  return <Navigate to={isTokenValid() ? '/' : '/login'} replace />;
}

function LessonRedirect() {
  const { id } = useParams();
  return <Navigate to={`/lecciones/${id}/tema`} replace />;
}

export default function App() {
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
            <ProtectedRoute>
              <ExamenDiagnostico />
            </ProtectedRoute>
          }
        />

        {/* Rutas privadas: primero pasan por el diagnóstico */}
        <Route
          path="/"
          element={
            <ProtectedRoute>
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
          <Route path="lecciones/:id" element={<LessonRedirect />} />
          <Route path="lecciones/:id/ejercicio" element={<LeccionEjercicio />} />
          <Route path="perfil" element={<MiPerfil />} />
        </Route>

        <Route path="*" element={<CatchAll />} />
      </Routes>
    </BrowserRouter>
  );
}