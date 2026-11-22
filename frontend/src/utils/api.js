export async function apiRequest(path, options = {}) {
  const token = sessionStorage.getItem('token');
  const headers = { ...(options.headers || {}) };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(path, { ...options, headers });
  const body = await response.text();
  let data = null;

  if (body) {
    try {
      data = JSON.parse(body);
    } catch {
      throw new Error(`El servidor devolvió una respuesta no válida (${response.status}).`);
    }
  }

  if (!response.ok) {
    throw new Error(data?.message || `Error del servidor (${response.status}).`);
  }

  return data;
}
