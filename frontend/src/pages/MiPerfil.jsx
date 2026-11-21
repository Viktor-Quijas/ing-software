import { useEffect, useRef, useState } from 'react';

/* ------------------------------------------------------------------ */
/* Datos de ejemplo (luego los reemplazas con tu API / estado global)  */
/* ------------------------------------------------------------------ */
const initialProfile = {
  firstName: 'Usuario',
  lastName: '',
  username: '@usuario.code',
  email: 'usuario@correo.com',
  emailVerified: true,
  about: 'Información sobre el usuario vdd.',
  language: 'es',
  level: 'principiante',
  weeklyGoal: 5,
};

const stats = [
  { value: 24, label: 'lecciones' },
  { value: 7, label: 'días de racha' },
  { value: 42, label: 'Horas Aprendiendo' },
];

const joinedAt = 'xx de octubre del 2026';

const languages = [
  { value: 'es', label: 'Español' },
  { value: 'en', label: 'English' },
];

const levels = [
  { value: 'principiante', label: 'Principiante' },
  { value: 'intermedio', label: 'Intermedio' },
  { value: 'avanzado', label: 'Avanzado' },
];

const goals = [
  { value: 3, hint: 'Sin prisa' },
  { value: 5, hint: 'Paso a paso' },
  { value: 7, hint: 'A por todas' },
];

const MAX_PHOTO_BYTES = 2 * 1024 * 1024; // 2 MB

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
    case 'upload':
      return (
        <svg {...props}>
          <path d="M12 16V4" />
          <path d="M7 9l5-5 5 5" />
          <path d="M5 20h14" />
        </svg>
      );
    case 'lock':
      return (
        <svg {...props}>
          <rect x="5" y="11" width="14" height="9" rx="2" />
          <path d="M8 11V8a4 4 0 0 1 8 0v3" />
        </svg>
      );
    case 'chevron':
      return (
        <svg {...props}>
          <path d="M6 9l6 6 6-6" />
        </svg>
      );
    case 'sliders':
      return (
        <svg {...props}>
          <path d="M4 7h10" />
          <path d="M18 7h2" />
          <circle cx="16" cy="7" r="2" />
          <path d="M4 17h2" />
          <path d="M10 17h10" />
          <circle cx="8" cy="17" r="2" />
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

function SelectField({ id, label, value, onChange, options }) {
  return (
    <Field id={id} label={label}>
      <div className="relative">
        <select
          id={id}
          value={value}
          onChange={onChange}
          className={`${inputClass} appearance-none pr-10`}
        >
          {options.map((o) => (
            <option key={o.value} value={o.value}>
              {o.label}
            </option>
          ))}
        </select>
        <span className="pointer-events-none absolute right-3.5 top-1/2 -translate-y-1/2 text-gray-500">
          <Icon name="chevron" />
        </span>
      </div>
    </Field>
  );
}

function Avatar({ initials, photo, size = 'md' }) {
  const sizes = {
    md: 'h-[72px] w-[72px] text-2xl',
    lg: 'h-20 w-20 text-3xl',
  };

  if (photo) {
    return (
      <img
        src={photo}
        alt="Foto de perfil"
        className={`${sizes[size]} rounded-full object-cover`}
      />
    );
  }

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
function ProfileSummary({ profile, photo }) {
  const fullName = `${profile.firstName} ${profile.lastName}`.trim() || 'Usuario';

  return (
    <aside className="space-y-5">
      <div className="rounded-2xl bg-purple-50 px-6 py-7 text-center">
        <div className="mx-auto w-fit rounded-full border-4 border-white p-0.5">
          <Avatar
            size="lg"
            photo={photo}
            initials={getInitials(profile.firstName, profile.lastName)}
          />
        </div>
        <p className="mt-4 text-xl text-gray-900">{fullName}</p>
        <p className="mt-1 font-mono text-xs text-gray-500">{profile.username}</p>

        <dl className="mt-6 grid grid-cols-3 gap-2 border-t border-purple-100 pt-5">
          {stats.map((s) => (
            <div key={s.label}>
              <dd className="text-2xl font-bold text-gray-900">{s.value}</dd>
              <dt className="mt-1 text-[10px] text-gray-500">{s.label}</dt>
            </div>
          ))}
        </dl>

        <p className="mt-5 text-xs text-gray-600">Se unió el {joinedAt}</p>
      </div>

      <p className="flex gap-3 px-2 text-xs text-gray-500">
        <span className="mt-0.5 text-emerald-600">
          <Icon name="shield" />
        </span>
        Tu correo es privado. Tú decides qué compartes con la comunidad.
      </p>
    </aside>
  );
}

/* ------------------------------------------------------------------ */
/* Pestaña "General"                                                   */
/* ------------------------------------------------------------------ */
function GeneralTab({ profile, setField, photo, setPhoto, onCancel, onSave, saved }) {
  const fileRef = useRef(null);
  const [photoError, setPhotoError] = useState('');

  const handlePhoto = (e) => {
    const file = e.target.files?.[0];
    if (!file) return;

    if (!['image/jpeg', 'image/png'].includes(file.type)) {
      setPhotoError('Solo se permiten archivos JPG o PNG.');
      return;
    }
    if (file.size > MAX_PHOTO_BYTES) {
      setPhotoError('La imagen pesa más de 2 MB.');
      return;
    }

    setPhotoError('');
    setPhoto(URL.createObjectURL(file));
    e.target.value = '';
  };

  return (
    <form onSubmit={onSave} className="space-y-6">
      <div className="grid items-start gap-6 lg:grid-cols-[1fr_340px]">
        <div className="space-y-6">
          {/* Información personal */}
          <section className="rounded-2xl border border-gray-200 bg-white p-7">
            <h2 className="text-xl text-gray-900">Información personal</h2>
            <p className="mt-1 text-xs text-gray-500">
              Así te verá la comunidad de Code&lt;/a&gt;.
            </p>

            <div className="mt-6 flex items-center gap-5">
              <Avatar
                photo={photo}
                initials={getInitials(profile.firstName, profile.lastName)}
              />
              <div>
                <div className="flex items-center gap-4">
                  <button
                    type="button"
                    onClick={() => fileRef.current?.click()}
                    className="inline-flex items-center gap-2 rounded-lg border border-gray-200 bg-white px-4 py-2.5 text-sm font-semibold text-gray-900 hover:border-purple-300 hover:text-purple-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-600"
                  >
                    Cambiar foto <Icon name="upload" />
                  </button>
                  <button
                    type="button"
                    onClick={() => setPhoto(null)}
                    className="text-xs text-gray-500 hover:text-red-600"
                  >
                    Eliminar
                  </button>
                  <input
                    ref={fileRef}
                    type="file"
                    accept="image/png,image/jpeg"
                    onChange={handlePhoto}
                    className="hidden"
                  />
                </div>
                <p
                  className={`mt-2 text-[11px] ${
                    photoError ? 'text-red-600' : 'text-gray-400'
                  }`}
                >
                  {photoError || 'JPG o PNG. Máximo 2 MB.'}
                </p>
              </div>
            </div>

            <div className="mt-6 space-y-5">
              <div className="grid gap-5 md:grid-cols-2">
                <Field id="firstName" label="Nombre">
                  <input
                    id="firstName"
                    type="text"
                    value={profile.firstName}
                    onChange={(e) => setField('firstName', e.target.value)}
                    className={inputClass}
                  />
                </Field>
                <Field id="lastName" label="Apellidos">
                  <input
                    id="lastName"
                    type="text"
                    value={profile.lastName}
                    onChange={(e) => setField('lastName', e.target.value)}
                    className={inputClass}
                  />
                </Field>
              </div>

              <Field
                id="username"
                label="Nombre de usuario"
                hint="Tu identificador único dentro de la comunidad."
              >
                <input
                  id="username"
                  type="text"
                  value={profile.username}
                  onChange={(e) => setField('username', e.target.value)}
                  className={inputClass}
                />
              </Field>

              <Field
                id="email"
                label="Correo electrónico"
                hint="Puedes cambiar tu correo en Cuenta y seguridad."
                extra={
                  profile.emailVerified && (
                    <span className="rounded-md bg-emerald-50 px-2 py-1 text-xs font-medium text-emerald-700">
                      Verificado
                    </span>
                  )
                }
              >
                <div className="relative">
                  <input
                    id="email"
                    type="email"
                    value={profile.email}
                    readOnly
                    className={`${inputClass} cursor-not-allowed bg-gray-50 pr-10 text-gray-500`}
                  />
                  <span className="pointer-events-none absolute right-3.5 top-1/2 -translate-y-1/2 text-gray-400">
                    <Icon name="lock" />
                  </span>
                </div>
              </Field>

              <Field id="about" label="Sobre mí">
                <textarea
                  id="about"
                  rows={3}
                  value={profile.about}
                  onChange={(e) => setField('about', e.target.value)}
                  className={`${inputClass} resize-none`}
                />
              </Field>
            </div>
          </section>

          {/* Tu forma de aprender */}
          <section className="rounded-2xl border border-gray-200 bg-white p-7">
            <h2 className="flex items-center gap-3 text-xl text-gray-900">
              <span className="text-purple-600">
                <Icon name="sliders" size={18} />
              </span>
              Tu forma de aprender
            </h2>

            <div className="mt-5 grid gap-5 md:grid-cols-2">
              <SelectField
                id="language"
                label="Idioma de la plataforma"
                value={profile.language}
                onChange={(e) => setField('language', e.target.value)}
                options={languages}
              />
              <SelectField
                id="level"
                label="Nivel de experiencia"
                value={profile.level}
                onChange={(e) => setField('level', e.target.value)}
                options={levels}
              />
            </div>

            <fieldset className="mt-5">
              <legend className="mb-2 text-sm text-gray-900">
                Mi objetivo semanal
              </legend>
              <div className="grid gap-4 md:grid-cols-3">
                {goals.map((g) => {
                  const selected = profile.weeklyGoal === g.value;
                  return (
                    <label
                      key={g.value}
                      className={`relative flex cursor-pointer items-center gap-3 rounded-lg border px-4 py-3 transition-colors has-[:focus-visible]:ring-2 has-[:focus-visible]:ring-purple-300 ${
                        selected
                          ? 'border-purple-700 bg-purple-50'
                          : 'border-gray-200 bg-white hover:border-purple-300'
                      }`}
                    >
                      <input
                        type="radio"
                        name="weeklyGoal"
                        value={g.value}
                        checked={selected}
                        onChange={() => setField('weeklyGoal', g.value)}
                        className="sr-only"
                      />
                      <span
                        aria-hidden="true"
                        className={`flex h-4 w-4 shrink-0 items-center justify-center rounded-full border ${
                          selected ? 'border-purple-700' : 'border-gray-300'
                        }`}
                      >
                        {selected && (
                          <span className="h-2 w-2 rounded-full bg-purple-700" />
                        )}
                      </span>
                      <span>
                        <span className="block text-sm text-gray-900">
                          {g.value} lecciones
                        </span>
                        <span className="block text-[11px] text-gray-500">
                          {g.hint}
                        </span>
                      </span>
                    </label>
                  );
                })}
              </div>
            </fieldset>
          </section>
        </div>

        <ProfileSummary profile={profile} photo={photo} />
      </div>

      {/* Acciones */}
      <div className="flex items-center gap-4 border-t border-gray-200 pt-6">
        <button
          type="button"
          onClick={onCancel}
          className="rounded-lg border border-gray-200 bg-white px-5 py-3 text-sm font-semibold text-gray-900 hover:bg-gray-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-600"
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="inline-flex items-center gap-2 rounded-lg bg-purple-700 px-5 py-3 text-sm font-semibold text-white shadow-sm transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2"
        >
          Guardar cambios <Icon name="check" />
        </button>
        {saved && (
          <span role="status" className="text-sm text-emerald-700">
            ¡Cambios guardados!
          </span>
        )}
      </div>
    </form>
  );
}

/* ------------------------------------------------------------------ */
/* Pestaña "Cuenta y seguridad" (pendiente de diseño)                  */
/* ------------------------------------------------------------------ */
const emptyPasswords = { current: '', next: '', repeat: '' };
const MIN_PASSWORD_LENGTH = 8;

function PasswordField({ id, label, value, onChange, error, autoComplete }) {
  return (
    <Field id={id} label={label}>
      <input
        id={id}
        type="password"
        value={value}
        onChange={onChange}
        autoComplete={autoComplete}
        aria-invalid={Boolean(error)}
        className={`${inputClass} ${error ? 'border-red-400 focus:border-red-500 focus:ring-red-100' : ''}`}
      />
      {error && <p className="mt-2 text-[11px] text-red-600">{error}</p>}
    </Field>
  );
}

function SecurityTab() {
  const [values, setValues] = useState(emptyPasswords);
  const [errors, setErrors] = useState({});
  const [saved, setSaved] = useState(false);

  const setValue = (field) => (e) => {
    setSaved(false);
    setValues((prev) => ({ ...prev, [field]: e.target.value }));
  };

  const validate = () => {
    const next = {};
    if (!values.current) next.current = 'Escribe tu contraseña actual.';
    if (values.next.length < MIN_PASSWORD_LENGTH) {
      next.next = `La contraseña nueva debe tener al menos ${MIN_PASSWORD_LENGTH} caracteres.`;
    }
    if (values.repeat !== values.next) {
      next.repeat = 'Las contraseñas no coinciden.';
    }
    return next;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const found = validate();
    setErrors(found);
    if (Object.keys(found).length > 0) return;

    // TODO: enviar a tu backend (PUT /api/cuenta/password)
    // Nunca hagas console.log de contraseñas.
    setValues(emptyPasswords);
    setSaved(true);
  };

  const handleCancel = () => {
    setValues(emptyPasswords);
    setErrors({});
    setSaved(false);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6" noValidate>
      <section className="rounded-2xl border border-gray-200 bg-white p-7">
        <h2 className="flex items-center gap-3 text-xl text-gray-900">
          <span className="text-purple-700">
            <Icon name="lock" size={20} />
          </span>
          Cambio de contraseña
        </h2>

        <div className="mt-5 space-y-5">
          <PasswordField
            id="currentPassword"
            label="Contraseña actual"
            value={values.current}
            onChange={setValue('current')}
            error={errors.current}
            autoComplete="current-password"
          />
          <PasswordField
            id="newPassword"
            label="Contraseña nueva"
            value={values.next}
            onChange={setValue('next')}
            error={errors.next}
            autoComplete="new-password"
          />
          <PasswordField
            id="repeatPassword"
            label="Repetir contraseña nueva"
            value={values.repeat}
            onChange={setValue('repeat')}
            error={errors.repeat}
            autoComplete="new-password"
          />
        </div>
      </section>

      <div className="flex items-center gap-4 border-t border-gray-200 pt-6">
        <button
          type="button"
          onClick={handleCancel}
          className="rounded-lg border border-gray-200 bg-white px-5 py-3 text-sm font-semibold text-gray-900 hover:bg-gray-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-600"
        >
          Cancelar
        </button>
        <button
          type="submit"
          className="inline-flex items-center gap-2 rounded-lg bg-purple-700 px-5 py-3 text-sm font-semibold text-white shadow-sm transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2"
        >
          Guardar cambios <Icon name="check" />
        </button>
        {saved && (
          <span role="status" className="text-sm text-emerald-700">
            ¡Contraseña actualizada!
          </span>
        )}
      </div>
    </form>
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
  const [activeTab, setActiveTab] = useState('general');
  const [profile, setProfile] = useState(initialProfile);
  const [photo, setPhoto] = useState(null);
  const [saved, setSaved] = useState(false);

  // Libera la URL temporal de la foto cuando cambia o se desmonta
  useEffect(() => {
    return () => {
      if (photo) URL.revokeObjectURL(photo);
    };
  }, [photo]);

  const setField = (field, value) => {
    setSaved(false);
    setProfile((prev) => ({ ...prev, [field]: value }));
  };

  const handleCancel = () => {
    setProfile(initialProfile);
    setPhoto(null);
    setSaved(false);
  };

  const handleSave = (e) => {
    e.preventDefault();
    // TODO: enviar `profile` a tu backend (PUT /api/perfil)
    console.log('Guardar perfil:', profile);
    setSaved(true);
  };

  return (
    <div className="space-y-6">
      <header>
        <h1 className="text-3xl font-bold text-gray-900">Mi perfil</h1>
        <p className="mt-2 text-sm text-gray-500">
          Cuéntanos sobre ti y haz que aprender encaje en tu día a día.
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
          photo={photo}
          setPhoto={setPhoto}
          onCancel={handleCancel}
          onSave={handleSave}
          saved={saved}
        />
      ) : (
        <SecurityTab />
      )}
    </div>
  );
}

export default MiPerfil;