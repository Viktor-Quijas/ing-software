import { authFetch } from './auth';

// Resumen de avances del usuario logueado 
export async function fetchProgressSummary() {
const response = await authFetch('/api/me/progress/summary');
if (!response.ok) {
    throw new Error('No se pudo cargar tu progreso. Inténtalo de nuevo.');
}
return response.json();
}

let lastStartedLessonId = null;

export async function startLesson(idLesson) {
if (lastStartedLessonId === idLesson) return;
lastStartedLessonId = idLesson;
try {
    await authFetch(`/api/me/progress/lessons/${idLesson}/start`, { method: 'POST' });
} catch {
    // Si falla, la lección se puede estudiar igual; solo no quedará como "última".
}
}