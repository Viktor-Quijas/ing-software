import { useEffect, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { apiRequest } from '../utils/api';
import { fetchProgressSummary } from '../utils/progress';

const emptyProfile = {
  firstName: '',
  lastName: '',
  email: '',
  styleName: '',
};

function mapUserToProfile(user) {
  return {
    firstName: user.nameUser || '',
    lastName: user.lastnameUser || '',
    email: user.emailUser || '',
    styleName: user.styleName || '',
  };
}

function formatDuration(totalMinutes = 0) {
  const hours = Math.floor(totalMinutes / 60);
  const minutes = totalMinutes % 60;
  if (hours === 0) return `${minutes} min`;
  if (minutes === 0) return `${hours} h`;
  return `${hours} h ${minutes} min`;
}

/* ------------------------------------------------------------------ */
/* Utilidades e iconos                                                 */
/* ------------------------------------------------------------------ */
function getInitials(firstName, lastName) {
  const a = (firstName || '').trim().charAt(0);
  const b = (lastName || '').trim().charAt(0);
  const initials = (a + b).toUpperCase();
  return initials || 'Us';
}

function Icon({ name, size = 16 }) {
  const props = {
    width: size,
    height: size,
    viewBox: '0 0 24 24',
    fill: 'none',
    stroke: 'currentColor',
    strokeWidth: 2,
    strokeLinecap: 'round',
    strokeLinejoin: 'round',
    'aria-hidden': true,
  };

  switch (name) {
    case 'lock':
      return (
        <svg {...props}>
          <rect x="5" y="11" width="14" height="9" rx="2" />
          <path d="M8 11V8a4 4 0 0 1 8 0v3" />
        </svg>
      );
    case 'shield':
      return (
        <svg {...props}>
          <path d="M12 3l7 3v5c0 4.5-3 8-7 10-4-2-7-5.5-7-10V6z" />
          <path d="M9 12l2 2 4-4" />
        </svg>
      );
    case 'check':
      return (
        <svg {...props}>
          <path d="M5 12l5 5 9-10" />
        </svg>
      );
    default:
      return null;
  }
}

/* ------------------------------------------------------------------ */
/* Subcomponentes de formulario                                        */
/* ------------------------------------------------------------------ */
const inputClass =
  'w-full rounded-lg border border-gray-200 bg-white px-3.5 py-3 text-sm text-gray-900 placeholder:text-gray-400 focus:border-purple-600 focus:outline-none focus:ring-2 focus:ring-purple-200';

function Field({ id, label, hint, children, extra }) {
  return (
    <div>
      <div className="mb-2 flex items-center gap-2">
        <label htmlFor={id} className="text-sm text-gray-900">
          {label}
        </label>
        {extra}
      </div>
      {children}
      {hint && <p className="mt-2 text-[11px] text-gray-500">{hint}</p>}
    </div>
  );
}

function Avatar({ initials, size = 'md' }) {
  const sizes = {
    md: 'h-[72px] w-[72px] text-2xl',
    lg: 'h-20 w-20 text-3xl',
  };

  return (
    <div
      className={`${sizes[size]} flex items-center justify-center rounded-full bg-purple-100 font-bold text-purple-500`}
    >
      {initials}
    </div>
  );
}

/* ------------------------------------------------------------------ */
/* Tarjeta lateral con resumen                                         */
/* ------------------------------------------------------------------ */
function ProfileSummary({ profile, progress, progressError }) {
  const fullName = `${profile.firstName} ${profile.lastName}`.trim() || 'Usuario';
  const stats = progress
    ? [
        { value: `${progress.completedLessons}/${progress.totalLessons}`, label: 'lecciones' },
        { value: formatDuration(progress.completedMinutes), label: 'aprendizaje' },
        {
          value: progress.averageScorePercent == null ? '—' : `${progress.averageScorePercent}%`,
          label: 'aciertos',
        },
      ]
    : [];

  return (
    <aside className="space-y-5">
      <div className="rounded-2xl bg-purple-50 px-6 py-7 text-center">
        <div className="mx-auto w-fit rounded-full border-4 border-white p-0.5">
          <Avatar
            size="lg"
            initials={getInitials(profile.firstName, profile.lastName)}
          />
        </div>
        <p className="mt-4 text-xl text-gray-900">{fullName}</p>
        <p className="mt-1 break-all font-mono text-xs text-gray-500">
          {profile.email || 'Correo no disponible'}
        </p>
        <p className="mt-3 text-xs text-gray-600">
          Estilo de aprendizaje: {profile.styleName || 'Sin definir'}
        </p>

        <dl className="mt-6 grid grid-cols-3 gap-2 border-t border-purple-100 pt-5">
          {stats.length > 0 ? (
            stats.map((s) => (
              <div key={s.label}>
                <dd className="text-2xl font-bold text-gray-900">{s.value}</dd>
                <dt className="mt-1 text-[10px] text-gray-500">{s.label}</dt>
              </div>
            ))
          ) : (
            <div className="col-span-3">
              <dd className={`text-sm ${progressError ? 'text-red-600' : 'text-gray-500'}`}>
                {progressError || 'Cargando progreso...'}
              </dd>
              <dt className="mt-1 text-[10px] text-gray-500">progreso</dt>
            </div>
          )}
        </dl>
      </div>

      <p className="flex gap-3 px-2 text-xs text-gray-500">
        <span className="mt-0.5 text-emerald-600">
          <Icon name="shield" />
        </span>
        Tus datos están protegidos y no se comparten con terceros... aún
      </p>
    </aside>
  );
}

/* ------------------------------------------------------------------ */
/* Pestaña "General"                                                   */
/* ------------------------------------------------------------------ */
function GeneralTab({
  profile,
  setField,
  progress,
  progressError,
  onCancel,
  onSave,
  saved,
  isSaving,
  saveError,
}) {
  return (
    <form onSubmit={onSave} className="space-y-6">
      <div className="grid items-start gap-6 lg:grid-cols-[1fr_340px]">
        <div className="space-y-6">
          {/* Información personal */}
          <section className="rounded-2xl border border-gray-200 bg-white p-7">
            <h2 className="text-xl text-gray-900">Información personal</h2>
            <p className="mt-1 text-xs text-gray-500">
              Actualiza los datos asociados a tu cuenta.
            </p>

            <div className="mt-6 space-y-5">
              <div className="grid gap-5 md:grid-cols-2">
                <Field id="firstName" label="Nombre">
                  <input
                    id="firstName"
                    type="text"
                    autoComplete="given-name"
                    required
                    value={profile.firstName}
                    onChange={(e) => setField('firstName', e.target.value)}
                    className={inputClass}
                  />
                </Field>
                <Field id="lastName" label="Apellidos">
                  <input
                    id="lastName"
                    type="text"
                    autoComplete="family-name"
                    value={profile.lastName}
                    onChange={(e) => setField('lastName', e.target.value)}
                    className={inputClass}
                  />
                </Field>
              </div>

              <Field id="email" label="Correo electrónico">
                <input
                  id="email"
                  type="email"
                  autoComplete="email"
                  required
                  value={profile.email}
                  onChange={(e) => setField('email', e.target.value)}
                  className={inputClass}
                />
              </Field>
            </div>
          </section>
        </div>

        <ProfileSummary
          profile={profile}
          progress={progress}
          progressError={progressError}
        />
      </div>

      {/* Acciones */}
      <div className="flex items-center gap-4 border-t border-gray-200 pt-6">
        <button
          type="button"
          onClick={onCancel}
          disabled={isSaving}
          className="rounded-lg border border-gray-200 bg-white px-5 py-3 text-sm font-semibold text-gray-900 hover:bg-gray-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-600"
        >
          Cancelar
        </button>
        <button
          type="submit"
          disabled={isSaving}
          className="inline-flex items-center gap-2 rounded-lg bg-purple-700 px-5 py-3 text-sm font-semibold text-white shadow-sm transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2"
        >
          {isSaving ? 'Guardando...' : 'Guardar cambios'} <Icon name="check" />
        </button>
        {saved && (
          <span role="status" className="text-sm text-emerald-700">
            ¡Cambios guardados!
          </span>
        )}
        {saveError && (
          <p role="alert" className="text-sm text-red-600">
            {saveError}
          </p>
        )}
      </div>
    </form>
  );
}

/* ------------------------------------------------------------------ */
/* Pestaña "Cuenta y seguridad"                                        */
/* ------------------------------------------------------------------ */
function SecurityTab() {
  return (
    <section className="rounded-2xl border border-gray-200 bg-white p-7">
      <h2 className="flex items-center gap-3 text-xl text-gray-900">
        <span className="text-purple-700">
          <Icon name="lock" size={20} />
        </span>
        Cuenta y seguridad
      </h2>
      <p className="mt-3 text-sm text-gray-600">
        La actualización de contraseña todavía no está disponible desde esta pantalla.
      </p>
    </section>
  );
}

/* ------------------------------------------------------------------ */
/* Vista principal                                                     */
/* ------------------------------------------------------------------ */
const tabs = [
  { id: 'general', label: 'General' },
  { id: 'seguridad', label: 'Cuenta y seguridad' },
];

export function MiPerfil() {
  const { setUserName } = useOutletContext();
  const [activeTab, setActiveTab] = useState('general');
  const [profile, setProfile] = useState(emptyProfile);
  const [account, setAccount] = useState(null);
  const [progress, setProgress] = useState(null);
  const [progressError, setProgressError] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState('');
  const [isSaving, setIsSaving] = useState(false);
  const [saveError, setSaveError] = useState('');
  const [saved, setSaved] = useState(false);
  const [loadAttempt, setLoadAttempt] = useState(0);
  const userId = Number(sessionStorage.getItem('userId'));

  useEffect(() => {
    let active = true;

    async function loadProfile() {
      setIsLoading(true);
      setLoadError('');

      if (!Number.isSafeInteger(userId) || userId <= 0) {
        setLoadError('No se pudo identificar la sesión del usuario.');
        setIsLoading(false);
        return;
      }

      try {
        const user = await apiRequest(`/api/users/${userId}`);
        if (!user || typeof user !== 'object' || user.idUser !== userId) {
          throw new Error('El servidor no devolvió los datos del usuario.');
        }

        if (active) {
          setAccount(user);
          setProfile(mapUserToProfile(user));
          const fullName = [user.nameUser, user.lastnameUser]
            .filter((part) => typeof part === 'string' && part.trim())
            .join(' ')
            .trim();
          if (fullName) setUserName(fullName);
        }
      } catch (err) {
        if (active) {
          setLoadError(
            err instanceof Error ? err.message : 'No se pudo cargar el perfil.'
          );
        }
      } finally {
        if (active) setIsLoading(false);
      }
    }

    loadProfile();
    return () => {
      active = false;
    };
  }, [userId, loadAttempt, setUserName]);

  useEffect(() => {
    let active = true;

    async function loadProgress() {
      setProgress(null);
      setProgressError('');
      if (!Number.isSafeInteger(userId) || userId <= 0) return;

      try {
        const summary = await fetchProgressSummary();
        if (active) setProgress(summary);
      } catch (err) {
        if (active) {
          setProgressError(
            err instanceof Error ? err.message : 'No se pudo cargar tu progreso.'
          );
        }
      }
    }

    loadProgress();
    return () => {
      active = false;
    };
  }, [userId, loadAttempt]);

  const setField = (field, value) => {
    setSaved(false);
    setSaveError('');
    setProfile((prev) => ({ ...prev, [field]: value }));
  };

  const handleCancel = () => {
    if (account) setProfile(mapUserToProfile(account));
    setSaveError('');
    setSaved(false);
  };

  const handleSave = async (e) => {
    e.preventDefault();
    setSaved(false);
    setSaveError('');

    const nameUser = profile.firstName.trim();
    const emailUser = profile.email.trim();
    if (!nameUser || !emailUser) {
      setSaveError('El nombre y el correo electrónico son obligatorios.');
      return;
    }
    if (!account) {
      setSaveError('No se han cargado los datos de la cuenta.');
      return;
    }

    setIsSaving(true);
    try {
      const updatedUser = await apiRequest(`/api/users/${userId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          nameUser,
          lastnameUser: profile.lastName.trim(),
          emailUser,
          rolUser: account.rolUser,
          statusUser: account.statusUser,
          idAlgorithm: account.idAlgorithm,
          idStyle: account.idStyle,
        }),
      });
      if (!updatedUser || updatedUser.idUser !== userId) {
        throw new Error('El servidor no confirmó los cambios del perfil.');
      }

      setAccount(updatedUser);
      setProfile(mapUserToProfile(updatedUser));
      const fullName = [updatedUser.nameUser, updatedUser.lastnameUser]
        .filter((part) => typeof part === 'string' && part.trim())
        .join(' ')
        .trim();
      if (fullName) setUserName(fullName);
      setSaved(true);
    } catch (err) {
      setSaveError(
        err instanceof Error ? err.message : 'No se pudieron guardar los cambios.'
      );
    } finally {
      setIsSaving(false);
    }
  };

  if (isLoading) {
    return <p role="status" className="text-sm text-gray-600">Cargando perfil...</p>;
  }

  if (loadError) {
    return (
      <div className="space-y-4">
        <p role="alert" className="text-sm text-red-600">{loadError}</p>
        <button
          type="button"
          onClick={() => setLoadAttempt((attempt) => attempt + 1)}
          className="rounded-lg bg-purple-700 px-5 py-3 text-sm font-semibold text-white hover:bg-purple-800"
        >
          Reintentar
        </button>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <header>
        <h1 className="text-3xl font-bold text-gray-900">Mi perfil</h1>
        <p className="mt-2 text-sm text-gray-500">
          Consulta y actualiza los datos asociados a tu cuenta.
        </p>
      </header>

      {/* Pestañas */}
      <div role="tablist" className="flex gap-8 border-b border-gray-200">
        {tabs.map((t) => {
          const isActive = activeTab === t.id;
          return (
            <button
              key={t.id}
              type="button"
              role="tab"
              aria-selected={isActive}
              onClick={() => setActiveTab(t.id)}
              className={`-mb-px border-b-2 pb-4 text-sm transition-colors ${
                isActive
                  ? 'border-purple-700 font-medium text-purple-700'
                  : 'border-transparent text-gray-500 hover:text-gray-800'
              }`}
            >
              {t.label}
            </button>
          );
        })}
      </div>

      {activeTab === 'general' ? (
        <GeneralTab
          profile={profile}
          setField={setField}
          progress={progress}
          progressError={progressError}
          onCancel={handleCancel}
          onSave={handleSave}
          saved={saved}
          isSaving={isSaving}
          saveError={saveError}
        />
      ) : (
        <SecurityTab />
      )}
    </div>
  );
}

export default MiPerfil;