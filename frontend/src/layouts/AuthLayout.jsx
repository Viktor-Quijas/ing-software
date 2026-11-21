import { Outlet } from 'react-router-dom';

export function AuthLayout() {
    return (
        <div className="min-h-screen w-full bg-white flex items-center justify-center p-6 md:p-12 font-sans">
            <div className="w-full max-w-6xl">
                <Outlet />
            </div>
        </div>
    )
}