// Guarda en el navegador si el usuario ya hizo (u omitió) el examen diagnóstico.
// OJO: esto es un MOCK. Cuando exista el backend, este dato debe venir del
// usuario (por ejemplo, `user.diagnosticCompleted`) y no de localStorage,
// porque localStorage es por navegador, no por cuenta.

const STORAGE_KEY = 'codea_diagnostic';

export function getDiagnosticStatus() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

// true si ya hizo el examen o decidió omitirlo
export function hasHandledDiagnostic() {
  return getDiagnosticStatus() !== null;
}

function saveStatus(status) {
  try {
    localStorage.setItem(
      STORAGE_KEY,
      JSON.stringify({
        ...status,
        completedAt: new Date().toISOString(),
      })
    );
  } catch {
    // Si el navegador bloquea el almacenamiento, seguimos sin guardar.
  }
}

export function saveDiagnosticResult({ score, total, level }) {
  saveStatus({ skipped: false, score, total, level });
}

export function markDiagnosticCompleted() {
  saveStatus({ skipped: false });
}

export function skipDiagnostic() {
  try {
    localStorage.setItem(
      STORAGE_KEY,
      JSON.stringify({ skipped: true, completedAt: new Date().toISOString() })
    );
  } catch {
    // Igual que arriba.
  }
}

// Útil para probar: vuelve a mostrar el examen como si fueras un usuario nuevo.
export function resetDiagnostic() {
  try {
    localStorage.removeItem(STORAGE_KEY);
  } catch {
    // Nada que hacer.
  }
}