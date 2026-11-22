const TOKEN_KEY = 'token';

export const getToken = () => sessionStorage.getItem(TOKEN_KEY);
export const setToken = (token) => sessionStorage.setItem(TOKEN_KEY, token);
export const clearSession = () => sessionStorage.removeItem(TOKEN_KEY);

export function isTokenValid(token = getToken()) {
    if (!token) return false;
        try {
    const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
    const payload = JSON.parse(atob(base64));
    return payload.exp * 1000 > Date.now();
}    catch {
    return false;
}
}


export async function logoutRequest() {
const token = getToken();
try {
    if (token) {
    await fetch('/api/auth/logout', {
        method: 'POST',
        headers: { Authorization: `Bearer ${token}` },
    });
    }
} catch {               

} finally {
    clearSession();
}    
}

export async function authFetch(url, options = {}) {
const response = await fetch(url, {
    ...options,
    headers: { ...options.headers, Authorization: `Bearer ${getToken()}` },
});
if (response.status === 401) {
    clearSession();
    window.location.replace('/login');
}
return response;
}