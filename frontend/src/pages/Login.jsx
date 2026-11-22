import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { CodeaLogo } from '../components/CodeaLogo';
import { LoginIllustrations } from '../components/LoginIllustrations';
import { setToken } from '../utils/auth';

export function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setIsSubmitting(true);

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          emailUser: formData.email,
          passwordUser: formData.password,
        })
      });

      if (response.status === 401) {
        throw new Error('Correo o contraseña incorrectos.');
      }

      if(!response.ok){
        throw new Error('Ocurrió un error. Por favor, inténtalo de nuevo más tarde. (Server)');
      }

      const data = await response.json();
      if (!data.token || !data.user?.idUser) {
        throw new Error('La respuesta del servidor no contiene el token o el identificador del usuario.');
      }

      sessionStorage.setItem('userId', String(data.user.idUser));
      setToken(data.token);
      navigate('/diagnostico');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'No se puedo iniciar sesión.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
      {/* Columna Izquierda: Ilustraciones */}
      <div className="hidden lg:flex justify-center items-center">
        <LoginIllustrations />
      </div>

      {/* Columna Derecha: Formulario */}
      <div className="flex flex-col items-center w-full max-w-sm mx-auto">
        <CodeaLogo />

        <h1 className="text-base font-bold text-gray-900 mt-6 mb-6">
          Inicia Sesión o Regístrate
        </h1>

        <form onSubmit={handleSubmit} className="w-full space-y-4">
          <input
            type="email"
            placeholder="Correo electrónico"
            required
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            className="w-full px-4 py-2.5 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED] transition"
          />

          <input
            type="password"
            placeholder="Contraseña"
            required
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
            className="w-full px-4 py-2.5 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED] transition"
          />

          {error && <p role="alert" className="text-sm text-red-600">{error}</p>}

          <button
            type="submit"
            disabled={isSubmitting}
            className="w-full py-2.5 bg-[#DDD6FE] hover:bg-[#C4B5FD] text-gray-800 font-semibold text-sm rounded-lg transition cursor-pointer"
          >
            {isSubmitting ? 'Iniciando sesión...' : 'Iniciar Sesión'}
          </button>
        </form>

        {/* Divisor "o" */}
        <div className="flex items-center w-full my-6">
          <div className="flex-1 h-px bg-gray-300" />
          <span className="px-4 text-xs text-gray-400">o</span>
          <div className="flex-1 h-px bg-gray-300" />
        </div>

        {/* Botón hacia Registro */}
        <Link
          to="/register"
          className="w-full py-2.5 bg-[#581C87] hover:bg-[#4C1D95] text-white font-semibold text-sm rounded-lg text-center transition"
        >
          Registrarse
        </Link>
      </div>
    </div>
  );
}