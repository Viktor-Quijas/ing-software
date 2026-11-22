import { Outlet, Link, useLocation, useParams } from 'react-router-dom';
import logoCodea from '../assets/logotipo-codea.svg';

const LESSONS_TITLES = {
  '1': 'Lección 1 · JavaScript',
  '2': 'Lección 2 · HTML y CSS',
  '3': 'Lección 3 · Python',
  '4': 'Lección 4 · React',
  '5': 'Lección 5 · Git y GitHub',
  '6': 'Examen Diagnóstico · SQL',
};

export function LessonLayout({ lessonTitle = '[Nombre de lección actual]' }) {
  const location = useLocation();
  const { lessonId } = useParams(); // Lee el :lessonId de la URL (/lecciones/:lessonId)
  const currentLessonTitle = LESSONS_TITLES[lessonId] || `Lección ${lessonId}`;
  const isActive = (path) => location.pathname.startsWith(path);

  return (
    <div className="flex h-screen bg-[#F4F0FB] overflow-hidden font-sans">
      {/* Sidebar izquierdo */}
      <aside className="w-64 bg-[#F4F0FB] flex flex-col justify-between p-5 border-r border-purple-100/60 shrink-0">
        <div>
          <Link to="/avances" className="flex items-center gap-2 mb-7 px-1">
            <div className="bg-[#7C3AED] text-white p-2 rounded-xl flex items-center justify-center font-bold text-sm">
              <img src={logoCodea} alt="Logo Codea" className="w-5 h-5" />
            </div>
            <span className="text-xl font-bold text-gray-800 tracking-tight">
              Code<span className="text-gray-700">&lt;/a&gt;</span>
            </span>
          </Link>

          <div className="bg-white/70 p-3 rounded-2xl border border-white shadow-sm space-y-1">
            <p className="text-[10px] font-bold text-purple-400 uppercase tracking-wider px-3 py-1">
              TU ESPACIO
            </p>

            <nav className="space-y-1">
              <Link
                to="/avances"
                className={`flex items-center gap-3 px-3 py-2 rounded-xl text-sm transition ${
                  isActive('/avances')
                    ? 'bg-[#E9D8FD] text-[#7E22CE] font-semibold'
                    : 'text-gray-600 font-medium hover:bg-purple-50 hover:text-purple-700'
                }`}
              >
                <span>📈</span> Mis avances
              </Link>

              <Link
                to="/lecciones"
                className={`flex items-center gap-3 px-3 py-2 rounded-xl text-sm transition ${
                  isActive('/lecciones')
                    ? 'bg-[#E9D8FD] text-[#7E22CE] font-semibold'
                    : 'text-gray-600 font-medium hover:bg-purple-50 hover:text-purple-700'
                }`}
              >
                <span>📖</span> Explorar lecciones
              </Link>

              <Link
                to="/perfil"
                className={`flex items-center gap-3 px-3 py-2 rounded-xl text-sm transition ${
                  isActive('/perfil')
                    ? 'bg-[#E9D8FD] text-[#7E22CE] font-semibold'
                    : 'text-gray-600 font-medium hover:bg-purple-50 hover:text-purple-700'
                }`}
              >
                <span>👤</span> Mi perfil
              </Link>
            </nav>
          </div>
        </div>

        <div className="space-y-3 px-2">
          <button
            type="button"
            className="flex items-center gap-2 text-xs text-gray-500 hover:text-red-500 font-medium transition cursor-pointer"
          >
            <span className="border border-gray-400 rounded-full w-4 h-4 flex items-center justify-center text-[10px]">
              ✕
            </span>
            Cerrar Sesión
          </button>
          <p className="text-[10px] text-gray-400 uppercase tracking-widest font-semibold">
            VERSIÓN 1.0
          </p>
        </div>
      </aside>

      {/* Contenedor principal con TopBar y Outlet */}
      <div className="flex-1 flex flex-col overflow-hidden bg-[#F8F6FC]">
        <header className="h-14 px-8 bg-[#EFE7FD] flex items-center justify-between border-b border-purple-100 shrink-0">
          <nav className="text-xs text-gray-500 font-medium flex items-center gap-2 truncate">
            <Link to="/avances" className="hover:text-purple-700">
              Mi espacio
            </Link>
            <span>&gt;</span>
            <Link to="/lecciones" className="hover:text-purple-700">
              Explorar lecciones
            </Link>
            <span>&gt;</span>
            <span className="text-[#6D28D9] font-semibold truncate">
              {currentLessonTitle}
            </span>
          </nav>

          <div className="flex items-center gap-2.5 pl-4 border-l border-purple-200/70">
            <div className="w-7 h-7 rounded-full bg-[#7C3AED] text-white flex items-center justify-center text-xs font-bold">
              Us
            </div>
            <button
              type="button"
              className="flex items-center gap-1 text-xs font-medium text-gray-700 hover:text-purple-700 cursor-pointer"
            >
              Usuario
              <span className="text-[10px] text-gray-400">∨</span>
            </button>
          </div>
        </header>

        <main className="flex-1 overflow-y-auto px-6 lg:px-10 py-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}