import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { MainLayout } from './layouts/MainLayout';

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

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<MisAvances />} />
          <Route path="avances" element={<MisAvances />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}