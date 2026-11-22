import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { fetchProgressSummary } from '../utils/progress';


const iconProps = {
  width: 20,
  height: 20,
  viewBox: '0 0 24 24',
  fill: 'none',
  stroke: 'currentColor',
  strokeWidth: 2,
  strokeLinecap: 'round',
  strokeLinejoin: 'round',
  'aria-hidden': true,
};

function Icon({ name }) {
  switch (name) {
    case 'book':
      return (
        <svg {...iconProps}>
          <path d="M2 4h6a4 4 0 0 1 4 4v12a3 3 0 0 0-3-3H2z" />
          <path d="M22 4h-6a4 4 0 0 0-4 4v12a3 3 0 0 1 3-3h7z" />
        </svg>
      );
    case 'clock':
      return (
        <svg {...iconProps}>
          <circle cx="12" cy="12" r="9" />
          <path d="M12 7v5l3 2" />
        </svg>
      );
    case 'trend':
      return (
        <svg {...iconProps}>
          <path d="M3 17l6-6 4 4 8-8" />
          <path d="M15 7h6v6" />
        </svg>
      );
    case 'arrow':
      return (
        <svg {...iconProps} width={16} height={16}>
          <path d="M5 12h14" />
          <path d="M13 6l6 6-6 6" />
        </svg>
      );
    default:
      return null;
  }
}


function formatDuration(totalMinutes) {
  const h = Math.floor(totalMinutes / 60);
  const m = totalMinutes % 60;
  if (h === 0) return `${m} min`;
  if (m === 0) return `${h} h`;
  return `${h} h ${m} min`;
}

const pad = (n) => String(n).padStart(2, '0');

function buildStats(s) {
  return [
    {
      label: 'Lecciones completadas',
      value: String(s.completedLessons),
      delta: `de ${s.totalLessons}`,
      icon: 'book',
    },
    {
      label: 'Tiempo de aprendizaje',
      value: formatDuration(s.completedMinutes),
      delta: 'en lecciones completadas',
      icon: 'clock',
    },
    {
      label: 'Promedio de aciertos',
      value: s.averageScorePercent == null ? '—' : `${s.averageScorePercent}%`,
      delta: 'en tus cuestionarios',
      icon: 'trend',
    },
  ];
}

function buildMessage(s) {
  if (s.totalLessons === 0) return 'Aún no hay lecciones disponibles.';
  if (s.completedLessons === s.totalLessons) return '¡Completaste todas las lecciones!';
  if (s.completedLessons === 0 && s.inProgressLessons === 0) {
    return 'Empieza tu primera lección cuando quieras.';
  }
  return `Llevas ${s.completedLessons} de ${s.totalLessons} lecciones completadas. ¡Sigue así!`;
}

/* ------------------------------------------------------------------ */
/* Subcomponentes                                                      */
/* ------------------------------------------------------------------ */
function StatCard({ label, value, delta, icon }) {
  return (
    <article className="rounded-2xl border border-gray-200 bg-white p-5">
      <div className="flex items-start justify-between">
        <p className="text-sm text-gray-500">{label}</p>
        <span className="text-purple-700">
          <Icon name={icon} />
        </span>
      </div>
      <p className="mt-4 flex items-baseline gap-2">
        <span className="text-3xl font-bold text-gray-900">{value}</span>
        <span className="text-xs font-medium text-emerald-600">{delta}</span>
      </p>
    </article>
  );
}

function CodePreview() {
  return (
    <div
      aria-hidden="true"
      className="pointer-events-none absolute right-6 top-1/2 hidden -translate-y-1/2 lg:block"
    >
      <div className="w-56 rotate-[4deg] rounded-xl bg-white p-4 font-mono text-[11px] leading-relaxed text-purple-800 shadow-lg">
        <div className="mb-2 flex gap-1">
          <span className="h-1.5 w-1.5 rounded-full bg-purple-700" />
          <span className="h-1.5 w-1.5 rounded-full bg-purple-300" />
          <span className="h-1.5 w-1.5 rounded-full bg-purple-100" />
        </div>
        <p>function main() {'{'}</p>
        <p className="pl-3">console.log(“Hello World”);</p>
        <p className="pl-3 pt-2">return 0;</p>
        <p>{'}'}</p>
      </div>
      <span className="absolute -bottom-5 left-0 -rotate-3 rounded-md bg-emerald-100 px-2 py-1 font-mono text-[10px] text-emerald-700">
        ✓ Una lección más
      </span>
    </div>
  );
}

// Última lección con la que trabajó el usuario (viene del backend)
function ContinueCard({ lesson, totalLessons }) {
  const completed = lesson.statusProgress === 'COMPLETADO';

  let detail;
  if (completed) {
    detail = 'Lección completada';
  } else if (lesson.triesProgress === 0) {
    detail = 'Cuestionario pendiente';
  } else {
    const best = Math.round(lesson.gradeProgress ?? 0);
    detail = `Mejor nota: ${best}/${lesson.totalQuestions}`;
  }

  return (
    <section
      aria-labelledby="retoma-titulo"
      className="relative overflow-hidden rounded-3xl bg-[#DDBFFB] px-6 py-6"
    >
      <div className="relative z-10 max-w-xl">
        <div className="flex items-center gap-2 text-xs">
          <span className="rounded-md bg-white px-2 py-1 font-medium text-purple-700">
            {completed ? 'Completada' : 'En curso'}
          </span>
          <span className="font-mono text-purple-900">
            {(lesson.difficultyLesson ?? '').toUpperCase()} · {pad(lesson.position)} / {pad(totalLessons)}
          </span>
        </div>

        <h3 id="retoma-titulo" className="mt-4 text-2xl font-bold text-gray-900">
          {lesson.titleLesson}
        </h3>
        <p className="mt-2 text-sm text-gray-600">
          {lesson.lengthLesson ? `${lesson.lengthLesson} min` : 'Duración no definida'}
        </p>

        <div className="mt-4 flex items-center gap-4">
          <Link
            to={`/lecciones/${lesson.idLesson}/tema`}
            className="inline-flex items-center gap-2 rounded-lg bg-purple-700 px-5 py-3 text-sm font-semibold text-white transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2"
          >
            {completed ? 'Repasar lección' : 'Continuar lección'} <Icon name="arrow" />
          </Link>
          <span className="text-xs text-purple-900">{detail}</span>
        </div>
      </div>

      <CodePreview />
    </section>
  );
}

// Cuando el usuario todavía no ha abierto ninguna lección
function EmptyContinueCard() {
  return (
    <section className="rounded-3xl bg-[#DDBFFB] px-6 py-8">
      <h3 className="text-2xl font-bold text-gray-900">Aún no empiezas ninguna lección</h3>
      <p className="mt-2 text-sm text-gray-600">
        Cuando abras una lección, aparecerá aquí para que la retomes en un clic.
      </p>
      <Link
        to="/lecciones"
        className="mt-4 inline-flex items-center gap-2 rounded-lg bg-purple-700 px-5 py-3 text-sm font-semibold text-white transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2"
      >
        Explorar lecciones <Icon name="arrow" />
      </Link>
    </section>
  );
}

function WeeklyChart({ data, activeIndex }) {
  const total = data.reduce((sum, d) => sum + d.minutes, 0);
  const peak = Math.max(0, ...data.map((d) => d.minutes));
  const max = Math.max(30, Math.ceil(peak / 10) * 10);
  const ticks = [max, max / 2, 0];

  return (
    <section
      aria-labelledby="ritmo-titulo"
      className="rounded-2xl border border-gray-200 bg-white p-6"
    >
      <div className="flex items-start justify-between">
        <h3 id="ritmo-titulo" className="text-lg font-medium text-gray-900">
          Tu ritmo de aprendizaje
        </h3>
        <span className="text-xs text-gray-500">Esta semana</span>
      </div>

      <p className="mt-4 flex items-baseline gap-3">
        <span className="text-2xl font-bold text-gray-900">{formatDuration(total)}</span>
        <span className="text-xs text-gray-500">
          estimado según la duración de las lecciones que trabajaste
        </span>
      </p>

      <div className="mt-6 flex gap-3">
        {/* Eje Y */}
        <div className="flex h-44 flex-col justify-between font-mono text-[10px] text-purple-700">
          {ticks.map((t) => (
            <span key={t}>{t} m</span>
          ))}
        </div>

        {/* Barras */}
        <div className="grid flex-1 grid-cols-7 gap-4">
          {data.map((d, i) => {
            const isActive = i === activeIndex;
            const height = Math.max((d.minutes / max) * 100, 4);
            return (
              <div key={d.day} className="flex flex-col items-center gap-2">
                <div className="flex h-44 w-full items-end">
                  <div
                    role="img"
                    aria-label={`${d.day}: ${d.minutes} minutos`}
                    style={{ height: `${height}%` }}
                    className={`w-full rounded-sm transition-colors ${
                      isActive ? 'bg-[#3B0764]' : 'bg-purple-100 hover:bg-purple-200'
                    }`}
                  />
                </div>
                <span className="text-xs text-gray-600">{d.day}</span>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}

/* ------------------------------------------------------------------ */
/* Vista principal                                                     */
/* ------------------------------------------------------------------ */
export function MisAvances() {
  const [summary, setSummary] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;
    fetchProgressSummary()
      .then((data) => {
        if (active) setSummary(data);
      })
      .catch((err) => {
        if (active) setError(err.message);
      });
    return () => {
      active = false;
    };
  }, []);

  if (error) {
    return (
      <p role="alert" className="mx-auto max-w-6xl text-sm text-red-600">
        {error}
      </p>
    );
  }

  if (!summary) {
    return <p className="mx-auto max-w-6xl text-sm text-gray-500">Cargando tu progreso…</p>;
  }

  return (
    <div className="mx-auto max-w-6xl space-y-10">
      {/* Encabezado */}
      <header className="flex items-start justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Hola, {summary.userName}</h1>
          <p className="mt-2 text-sm text-gray-700">{buildMessage(summary)}</p>
        </div>
        <Link
          to="/lecciones"
          className="inline-flex shrink-0 items-center gap-2 rounded-lg border border-gray-200 bg-white px-4 py-3 text-sm font-semibold text-gray-900 transition-colors hover:border-purple-300 hover:text-purple-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-700"
        >
          Explorar lecciones <Icon name="arrow" />
        </Link>
      </header>

      {/* Métricas */}
      <section aria-label="Resumen" className="grid gap-4 md:grid-cols-3">
        {buildStats(summary).map((s) => (
          <StatCard key={s.label} {...s} />
        ))}
      </section>

      {/* Retoma donde lo dejaste */}
      <section className="space-y-4">
        <h2 className="text-lg text-gray-800">Retoma donde lo dejaste</h2>
        {summary.lastLesson ? (
          <ContinueCard lesson={summary.lastLesson} totalLessons={summary.totalLessons} />
        ) : (
          <EmptyContinueCard />
        )}
      </section>

      {/* Ritmo semanal */}
      <WeeklyChart data={summary.weekly} activeIndex={summary.todayIndex} />
    </div>
  );
}

export default MisAvances;