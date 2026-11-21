import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from './layouts/MainLayout';
import { AuthLayout } from './layouts/AuthLayout';
import { Login } from './pages/Login';
import { Register } from './pages/Register';

function ProtectedRoute({ isAuthenticated, children }) {
  if (!isAuthenticated){
    return <Navigate to="/login" replace />;
  }
  return children;
}

function MisAvances() {
  return (
    <div className="space-y-6">
      <div className="flex justify-between items-start">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Feedback Title</h1>
          <p className="text-sm text-gray-500"> Feedback body. (un msj cortito)</p>
        </div>
        <button className="bg-white border border-gray-200 px-4 py-2 rounded-xl text-sm font-semibold text-gray-700 hover:bg-gray-50">
          Explorar lecciones →
        </button>
      </div>
    </div>
  );
}
import { MisAvances } from './pages/MisAvances';
import { ExplorarLecciones } from './pages/ExplorarLecciones';

export default function App() {

  //Variable traída desde el backend? no se.
const isAuthenticated = false;

  return (
    <BrowserRouter>
      <Routes>
        {/* Ruta pública, login*/}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Route>

        {/*Ruta privada, pagina principal*/}
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
        </Route>

        <Route path="*" element={<Navigate to={isAuthenticated ? '/' : '/login'} replace/>} />
      </Routes>
    </BrowserRouter>
  );
}