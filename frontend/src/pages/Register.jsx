import { useState } from 'react';
import { Link , useNavigate } from 'react-router-dom';
import { CodeaLogo } from '../components/CodeaLogo';
// Ilustraciones svg
import registerLeftImg from '../assets/register-1.svg';
import registerRightImg from '../assets/register-2.svg';

export function Register() {
    const navigate = useNavigate();
    const [error, setError] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [formData, setFormData] = useState({
        nameUser: '',
        lastnameUser: '',
        email: '',
        password: '',
        confirmPassword: '',
        aceptaTerminos: false,
    });
    

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (formData.password !== formData.confirmPassword) {
            setError('Las contraseñas no coinciden.');
            return;
        }

        if (!formData.aceptaTerminos) {
            setError('Debes aceptar los términos y condiciones.');
            return;
        }

        setIsSubmitting(true);

        try {
            const response = await fetch('/api/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                nameUser: formData.nameUser,
                lastnameUser: formData.lastnameUser,
                emailUser: formData.email,
                passwordUser: formData.password,
            }),
            });

            const responseText = await response.text();
            let data = null;

            if (responseText) {
                try {
                    data = JSON.parse(responseText);
                } catch {
                    throw new Error(`El servidor devolvió una respuesta no válida (${response.status}).`);
                }
            }

            if (!response.ok) {
                throw new Error(data?.message || `Error del servidor (${response.status}).`);
            }

            navigate('/login');
        } catch (err) {
            setError(err instanceof Error ? err.message : 'No se pudo completar el registro.');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 items-center">
            {/*Columna Izquierda, imagen*/}
            <div className="hidden lg:flex justify-center items-center">
                <img 
                    src={registerLeftImg}
                    alt="Ilustración registro izquierda"
                    className="w-full max-w-xs h-auto object-contain"
                />
            </div>

            {/*Columna del centro, formulario*/}
            <div className="flex flex-col items-center w-full max-w-sm mx-auto">
                <CodeaLogo />

                <p className="text-xs font-medium text-gray-700 mt-4 mb-5">
                    Ingresa tu información personal
                </p>

                <form onSubmit={handleSubmit} className="w-full space-y-3.5">
                    <input
                        type="text"
                        name="nameUser"
                        placeholder="Nombre"
                        required
                        value={formData.nameUser}
                        onChange={handleChange}
                        className="w-full px-4 py-2 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED]"
                    />
                    <input
                        type="text"
                        name="lastnameUser"
                        placeholder="Apellido"
                        required
                        value={formData.lastnameUser}
                        onChange={handleChange}
                        className="w-full px-4 py-2 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED]"
                    />
                    <input
                        type="email"
                        name="email"
                        placeholder="Correo electrónico"
                        required
                        value={formData.email}
                        onChange={handleChange}
                        className="w-full px-4 py-2 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED]"
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Contraseña"
                        required
                        value={formData.password}
                        onChange={handleChange}
                        className="w-full px-4 py-2 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED]"
                    />
                    <input
                        type="password"
                        name="confirmPassword"
                        placeholder="Confirma tu Constraseña"
                        required
                        value={formData.confirmPassword}
                        onChange={handleChange}
                        className="w-full px-4 py-2 bg-[#F4F0FB] border border-[#8B5CF6]/50 rounded-lg text-sm text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[#7C3AED]"
                    />

                    {/*Checkbox terminos y condiciones*/}
                    <label className="flex items-center gap-2 pt-1 cursor-pointer select-none">
                        <input
                            type="checkbox"
                            name="aceptaTerminos"
                            checked={formData.aceptaTerminos}
                            onChange={handleChange}
                            className="w-3.5 h-3.5 rounded border-dashed border-gray-400 text-[#581C87] focus:ring-[#7C3AED]"
                        />
                        <span className="text-[11px] text-gray-600">
                            Aceptar los términos y condiciones
                        </span>
                    </label>

                    {error && <p role="alert" className="text-sm text-red-600">{error}</p>}

                    <button
                        type="submit"
                        className="w-full py-2.5 mt-2 bg-[#581C87] hover:bg-[#4C1D95] text-white font-semibold text-sm rounded-lg transition cursor-pointer"
                    >
                        Registrarse
                    </button>
                </form>

                <p className="text-xs text-gray-500 mt-4">
                    ¿Ya tienes cuenta?{' '}
                    <Link to="/login" className="text-[#7C3AED] font-semibold hover:underline">
                        Inicia sesión
                    </Link>
                </p>
            </div>

            {/* Columna Derecha: Ilustración Escudo/Espada */}
            <div className="hidden lg:flex justify-center items-center">
                <img
                src={registerRightImg}
                alt="Ilustración registro derecha"
                className="w-full max-w-xs h-auto object-contain"
                />
            </div>
        </div>
    )
}