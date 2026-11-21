import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { MainLayout } from './layouts/MainLayout';
import { MisAvances } from './pages/MisAvances';
import { ExplorarLecciones } from './pages/ExplorarLecciones';
import { MiPerfil } from './pages/MiPerfil';


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<MisAvances />} />
          <Route path="avances" element={<MisAvances />} />
          <Route path="lecciones" element={<ExplorarLecciones />} /> 
          <Route path="perfil" element={<MiPerfil />} /> 
        </Route>
      </Routes>
    </BrowserRouter>
  );
}