import { useEffect, useState } from 'react';
import { Outlet, Link, useLocation, useNavigate } from 'react-router-dom';
import logoCodea from '../assets/logotipo-codea.svg';
import { logoutRequest } from '../utils/auth';
import { apiRequest } from '../utils/api';

const ROUTE_LABELS = {
  '/': 'Mis avances',
  '/avances': 'Mis avances',
  '/lecciones': 'Explorar lecciones',
  '/perfil': 'Mi perfil',
}

export function MainLayout() {
  const location = useLocation();
  const navigate = useNavigate();
  const [lessonTitle, setLessonTitle] = useState('');
  const [userName, setUserName] = useState('');
  const [userError, setUserError] = useState('');

  useEffect(() => {
    let active = true;

    async function loadUserName() {
      const userId = Number(sessionStorage.getItem('userId'));
      if (!Number.isSafeInteger(userId) || userId <= 0) {
        setUserError('No se pudo identificar la sesión del usuario.');
        return;
      }

      try {
        const user = await apiRequest(`/api/users/${userId}`);
        const fullName = [user?.nameUser, user?.lastnameUser]
          .filter((part) => typeof part === 'string' && part.trim())
          .join(' ')
          .trim();

        if (!fullName) {
          throw new Error('El servidor no devolvió el nombre del usuario.');
        }
        if (active) setUserName(fullName);
      } catch (err) {
        if (active) {
          setUserError(
            err instanceof Error ? err.message : 'No se pudo cargar el nombre del usuario.'
          );
        }
      }
    }

    loadUserName();
    return () => {
      active = false;
    };
  }, []);

  // Función auxiliar para detectar el enlace activo
  const lessonRoute = location.pathname.match(/^\/lecciones\/([^/]+)\/(?:tema|ejercicio)$/);
  const currentSection = lessonRoute
    ? lessonTitle || `Lección ${lessonRoute[1]}`
    : ROUTE_LABELS[location.pathname] || 'Mis avances';
  const displayedUserName = userName || (userError ? 'No disponible' : 'Cargando...');
  const userInitials = userName
    ? userName.split(/\s+/).slice(0, 2).map((part) => part[0].toUpperCase()).join('')
    : 'U';

  const isActive = (path) => location.pathname === path;

  // Cerrar sesión: borra los datos guardados y redirige al login
  const handleLogout = async () => {
  await logoutRequest(); 
  navigate('/login', { replace: true });
};

  return (
    <div className= "flex fixed inset-0 flex bg-codea-gray-100 overflow-hidden font-sans-screen bg-codea-gray-100 overflow-hidden font-sans">
      {/* 1. Sidebar (Barra Lateral Izquierda) */}
      <aside className="w-64 bg-codea-primary-100 flex flex-col justify-between p-6 border-r border-purple-100/50">
        <div>
          {/* Logotipo */}
          <div className="flex items-center gap-2 mb-8">
            <div className="bg-codea-primary-500 text-white p-2 rounded-xl flex items-center justify-center font-bold text-lg">
              <img src={logoCodea} alt="Logo Codea" className="w-6 h-6" />
            </div>
            <span className="text-2xl font-bold text-gray-800 tracking-tight">
              Code<span className="text-gray-800">&lt;/a&gt;</span>
            </span>
          </div>

          {/* Menú Tu Espacio */}
          <div className="bg-white/60 backdrop-blur-sm p-3 rounded-2xl border border-white/80 shadow-sm space-y-1">
            <p className="text-[10px] font-bold text-purple-400 uppercase tracking-wider px-3 py-1">
              TU ESPACIO
            </p>
            
            <nav className="space-y-1">
              <Link
                to="/avances"
                className={`flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition-all ${
                  isActive('/avances') || location.pathname === '/'
                    ? 'bg-[#E9D8FD] text-[#7E22CE] font-semibold'
                    : 'text-gray-600 hover:bg-purple-50/50 hover:text-purple-700'
                }`}
              >
                <span>📈</span> Mis avances
              </Link>
              
              <Link
                to="/lecciones"
                className={`flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition-all ${
                  isActive('/lecciones')
                    ? 'bg-[#E9D8FD] text-[#7E22CE] font-semibold'
                    : 'text-gray-600 hover:bg-purple-50/50 hover:text-purple-700'
                }`}
              >
                <span>📖</span> Explorar lecciones
              </Link>
              
              <Link
                to="/perfil"
                className={`flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition-all ${
                  isActive('/perfil')
                    ? 'bg-[#E9D8FD] text-[#7E22CE] font-semibold'
                    : 'text-gray-600 hover:bg-purple-50/50 hover:text-purple-700'
                }`}
              >
                <span>👤</span> Mi perfil
              </Link>
            </nav>
          </div>
        </div>

        {/* Footer Sidebar */}
        <div className="space-y-4 px-2">
          <button
            onClick={handleLogout}
            className="flex items-center gap-2 text-sm text-gray-500 hover:text-red-500 font-medium transition-colors"
          >
            <span className="border border-gray-400 rounded-full w-4 h-4 flex items-center justify-center text-[10px]">✕</span>
            Cerrar Sesión
          </button>
          <p className="text-[11px] text-gray-400 uppercase tracking-widest font-semibold">
            VERSIÓN 1.0
          </p>
        </div>
      </aside>

      {/* Áreas Contenedoras Principales */}
      <div className="flex-1 flex flex-col overflow-hidden bg-white border border-gray-100">
        {/* 2. TopBar (Barra Superior) */}
        <header className="h-16 px-8 bg-codea-primary-100 flex items-center justify-between border-b border-gray-100">
          {/* Migas de pan */}
          <nav className="text-sm text-gray-400 font-medium flex items-center gap-2">
            <Link to="/avances" className="hover:text-purple-700 transition-colors">
              Mi espacio
            </Link>
            <span>&gt;</span>
            <span className="text-purple-700 font-bold">{currentSection}</span>
          </nav>

          {/* Menú Usuario */}
          <div className="border-l border-gray-200 pl-4">
            <Link
              to="/perfil"
              title={userError || `Ver perfil de ${displayedUserName}`}
              aria-label={`Abrir mi perfil${userError ? `: ${userError}` : ''}`}
              className="flex items-center gap-3 text-sm font-medium text-gray-700 hover:text-purple-700"
            >
              <span className="flex h-8 w-8 items-center justify-center rounded-full bg-[#A855F7] text-xs font-bold text-white">
                {userInitials}
              </span>
              <span>{displayedUserName}</span>
              <span className="text-xs text-gray-400" aria-hidden="true">∨</span>
            </Link>
          </div>
        </header>

        {/* 3. Outlet (Vista Dinámica) */}
        <main className="flex-1 overflow-y-auto p-8 bg-gray-50/30">
          <Outlet context={{ setLessonTitle, setUserName }} />
        </main>
      </div>
    </div>
  );
}