import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { apiRequest } from '../utils/api';

// Colores de la franja superior de cada tarjeta
const tones = {
  violet: { bg: 'bg-[#EEEAFB]', text: 'text-[#5B45D6]', bar: 'bg-[#D9D2F6]' },
  green: { bg: 'bg-[#E6F4EC]', text: 'text-[#1F7A4D]', bar: 'bg-[#C8E6D5]' },
  amber: { bg: 'bg-[#FDF1DC]', text: 'text-[#B7791F]', bar: 'bg-[#F6DFB5]' },
  blue: { bg: 'bg-[#E8EEFB]', text: 'text-[#4C5FD5]', bar: 'bg-[#CFD9F5]' },
  peach: { bg: 'bg-[#FCEDE6]', text: 'text-[#C0623F]', bar: 'bg-[#F6D5C7]' },
  slate: { bg: 'bg-[#E8EAF6]', text: 'text-[#5B5F9E]', bar: 'bg-[#D0D3EC]' },
};

function mapLesson(lesson, index) {
  const content = lesson.contentLesson?.trim() || 'Abre la lección para consultar su contenido.';
  const description =
    content.length > 150 ? `${content.slice(0, 150).trimEnd()}…` : content;
  const toneNames = Object.keys(tones);

  return {
    id: lesson.idLesson,
    tag: `LECCIÓN ${index + 1}`,
    level: lesson.difficultyLesson || 'Sin nivel',
    title: lesson.titleLesson,
    description,
    duration: lesson.lengthLesson,
    badge: `#${index + 1}`,
    preview: lesson.statusLesson || 'Disponible',
    tone: toneNames[index % toneNames.length],
  };
}

function isDiagnosticLesson(lesson) {
  const normalize = (value = '') =>
    value.normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim().toLowerCase();
  return (
    normalize(lesson.titleLesson) === 'examen diagnostico' ||
    normalize(lesson.difficultyLesson) === 'diagnostico'
  );
}

/* ------------------------------------------------------------------ */
/* Iconos (SVG inline, sin dependencias)                               */
/* ------------------------------------------------------------------ */
function Icon({ name, size = 14 }) {
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
    case 'arrow':
      return (
        <svg {...props}>
          <path d="M5 12h14" />
          <path d="M13 6l6 6-6 6" />
        </svg>
      );
    case 'book':
      return (
        <svg {...props}>
          <path d="M2 4h6a4 4 0 0 1 4 4v12a3 3 0 0 0-3-3H2z" />
          <path d="M22 4h-6a4 4 0 0 0-4 4v12a3 3 0 0 1 3-3h7z" />
        </svg>
      );
    case 'clock':
      return (
        <svg {...props}>
          <circle cx="12" cy="12" r="9" />
          <path d="M12 7v5l3 2" />
        </svg>
      );
    case 'check':
      return (
        <svg {...props}>
          <path d="M5 12l5 5 9-10" />
        </svg>
      );
    case 'sparkle':
      return (
        <svg {...props}>
          <path d="M12 3l1.8 5.2L19 10l-5.2 1.8L12 17l-1.8-5.2L5 10l5.2-1.8z" />
          <path d="M19 16l.7 2.3L22 19l-2.3.7L19 22l-.7-2.3L16 19l2.3-.7z" />
        </svg>
      );
    default:
      return null;
  }
}

/* ------------------------------------------------------------------ */
/* Subcomponentes                                                      */
/* ------------------------------------------------------------------ */
function CardFooter({ lessonId, tone }) {
  const t = tones[tone];
  const linkClass = `inline-flex items-center gap-1 text-sm font-semibold ${t.text} hover:underline`;

  return (
    <Link to={`/lecciones/${lessonId}/tema`} className={linkClass}>
      Abrir lección <Icon name="arrow" />
    </Link>
  );
}

function LessonCard({ lesson }) {
  const t = tones[lesson.tone];

  return (
    <article className="overflow-hidden rounded-2xl border border-gray-200 bg-white">
      {/* Franja superior con "vista previa" */}
      <div className={`relative px-5 py-4 ${t.bg}`}>
        <div className="flex gap-1" aria-hidden="true">
          <span className={`h-1 w-1 rounded-full ${t.bar}`} />
          <span className={`h-1 w-1 rounded-full ${t.bar}`} />
          <span className={`h-1 w-1 rounded-full ${t.bar}`} />
        </div>
        <p className={`mt-3 font-mono text-[11px] ${t.text}`}>
          {lesson.preview}
        </p>
        <div className="mt-2 flex gap-2" aria-hidden="true">
          <span className={`h-1 w-10 rounded-full ${t.bar}`} />
          <span className={`h-1 w-14 rounded-full ${t.bar}`} />
        </div>
        <span
          className={`absolute right-5 top-1/2 -translate-y-1/2 text-4xl font-bold ${t.text}`}
        >
          {lesson.badge}
        </span>
      </div>

      {/* Contenido */}
      <div className="px-5 pb-4 pt-5">
        <div className="flex items-center justify-between">
          <span className="font-mono text-[10px] text-gray-500">
            {lesson.tag}
          </span>
          <span className="rounded-md bg-gray-100 px-2.5 py-1 text-xs text-gray-600">
            {lesson.level}
          </span>
        </div>

        <h3 className="mt-3 text-lg text-gray-900">{lesson.title}</h3>
        <p className="mt-1 text-sm text-gray-500">{lesson.description}</p>

        <div className="mt-3 flex items-center gap-4 text-xs text-gray-500">
          <span className="inline-flex items-center gap-1">
            <Icon name="book" /> Contenido
          </span>
          {lesson.duration != null && (
            <span className="inline-flex items-center gap-1">
              <Icon name="clock" /> {lesson.duration} min
            </span>
          )}
        </div>

        <div className="mt-4 flex items-center justify-between border-t border-gray-200 pt-3">
          <CardFooter lessonId={lesson.id} tone={lesson.tone} />
        </div>
      </div>
    </article>
  );
}

// Cuántas lecciones se muestran en "Recomendados"
const RECOMMENDED_COUNT = 3;

function LessonSection({ title, items }) {
  if (items.length === 0) return null;

  return (
    <section aria-label={title} className="space-y-4">
      <h2 className="text-lg text-gray-800">{title}</h2>
      <div className="grid gap-5 md:grid-cols-2 xl:grid-cols-3">
        {items.map((lesson) => (
          <LessonCard key={lesson.id} lesson={lesson} />
        ))}
      </div>
    </section>
  );
}

/* ------------------------------------------------------------------ */
/* Vista principal                                                     */
/* ------------------------------------------------------------------ */
export function ExplorarLecciones() {
  const [lessons, setLessons] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [reload, setReload] = useState(0);

  useEffect(() => {
    let cancelled = false;

    async function loadLessons() {
      setIsLoading(true);
      setError('');

      try {
        const data = await apiRequest('/api/lessons');
        if (!Array.isArray(data)) {
          throw new Error('El API devolvió una lista de lecciones inválida.');
        }

        if (!cancelled) {
          setLessons(data.filter((lesson) => !isDiagnosticLesson(lesson)).map(mapLesson));
          setIsLoading(false);
        }
      } catch (err) {
        if (!cancelled) {
          setError(err instanceof Error ? err.message : 'No se pudieron cargar las lecciones.');
          setIsLoading(false);
        }
      }
    }

    loadLessons();
    return () => {
      cancelled = true;
    };
  }, [reload]);

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-semibold text-gray-900">
        ¿Qué quieres aprender hoy?
      </h1>

      {isLoading && (
        <p role="status" className="rounded-2xl bg-white p-6 text-sm text-gray-600">
          Cargando lecciones...
        </p>
      )}

      {!isLoading && error && (
        <section className="rounded-2xl border border-red-100 bg-white p-6">
          <p role="alert" className="text-sm text-red-600">{error}</p>
          <button
            type="button"
            onClick={() => setReload((value) => value + 1)}
            className="mt-4 rounded-lg bg-purple-700 px-4 py-2 text-sm font-semibold text-white hover:bg-purple-800"
          >
            Reintentar
          </button>
        </section>
      )}

      {!isLoading && !error && lessons.length === 0 && (
        <p className="rounded-2xl bg-white p-6 text-sm text-gray-600">
          Aún no hay lecciones disponibles.
        </p>
      )}

      {!isLoading && !error && lessons.length > 0 && (
        <>
          <LessonSection
            title="Recomendados"
            items={lessons.slice(0, RECOMMENDED_COUNT)}
          />
          <LessonSection
            title="Todas las lecciones"
            items={lessons.slice(RECOMMENDED_COUNT)}
          />
        </>
      )}
    </div>
  );
}

export default ExplorarLecciones;