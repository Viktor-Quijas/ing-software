import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from './layouts/MainLayout';
import { AuthLayout } from './layouts/AuthLayout';
import { Login } from './pages/Login';
import { Register } from './pages/Register';
import { MisAvances } from './pages/MisAvances';
import { ExplorarLecciones } from './pages/ExplorarLecciones';
import { MiPerfil } from './pages/MiPerfil';
import { LessonLayout } from './layouts/LessonLayout';
import { LessonPage } from './pages/LessonPage';

function ProtectedRoute({ isAuthenticated, children }) {
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

export default function App() {
  // Variable traída desde el backend? no se.
  const isAuthenticated = true;

  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas públicas: login y registro */}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Route>

        {/* Rutas privadas: página principal */}
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
          <Route path="perfil" element={<MiPerfil />} />
        </Route>

        <Route
          path="lecciones/:lessonId"
          element={
            <ProtectedRoute isAuthenticated={isAuthenticated}>
                <LessonLayout />
            </ProtectedRoute>
          }
        >
          <Route index element={<LessonPage />} />
        </Route>

        <Route
          path="*"
          element={<Navigate to={isAuthenticated ? '/' : '/login'} replace />}
        />
      </Routes>
    </BrowserRouter>
  );
}