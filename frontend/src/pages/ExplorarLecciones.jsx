/* ------------------------------------------------------------------ */
/* Datos de ejemplo (luego los reemplazas con tu API / estado global)  */
/* ------------------------------------------------------------------ */
const lastLesson = {
  title: 'Última lección',
  description: 'Aquí se mostrará la información de la última lección.',
  progressNote: 'Aquí va a ir el porcentaje de avance del usuario',
  progress: 'n% progresado',
};

// status.type: 'progress' | 'completed' | 'free'
const lessons = [
  {
    id: 1,
    tag: 'JAVASCRIPT',
    level: 'Principiante',
    title: 'Lección 1',
    description: 'Dale vida a tus ideas con la lógica de la web.',
    lessons: 12,
    time: '3 h',
    badge: '#1',
    preview: 'ejemplo1',
    tone: 'violet',
    status: { type: 'progress', done: 3, total: 12 },
  },
  {
    id: 2,
    tag: 'HTML Y CSS',
    level: 'Principiante',
    title: 'Lección 2',
    description: 'De una hoja en blanco a tu primer sitio.',
    lessons: 10,
    time: '2 h 30 min',
    badge: '#2',
    preview: 'ejemplo2',
    tone: 'green',
    status: { type: 'completed' },
  },
  {
    id: 3,
    tag: 'PYTHON',
    level: 'Principiante',
    title: 'Lección 3',
    description: 'Aprende a resolver problemas paso a paso.',
    lessons: 16,
    time: '4 h',
    badge: '#3',
    preview: 'ejemplo3',
    tone: 'amber',
    status: { type: 'free' },
  },
  {
    id: 4,
    tag: 'REACT',
    level: 'Intermedio',
    title: 'Lección 4',
    description: 'Construye componentes que trabajan juntos.',
    lessons: 14,
    time: '4 h 20 min',
    badge: '#4',
    preview: 'ejemplo4',
    tone: 'blue',
    status: { type: 'free' },
  },
  {
    id: 5,
    tag: 'GIT Y GITHUB',
    level: 'Principiante',
    title: 'Lección 5',
    description: 'Guarda, colabora y nunca pierdas una idea.',
    lessons: 8,
    time: '1 h 45 min',
    badge: '#5',
    preview: 'ejemplo5',
    tone: 'peach',
    status: { type: 'free' },
  },
  {
    id: 6,
    tag: 'SQL',
    level: 'Intermedio',
    title: 'Examen Diagnóstico',
    description: 'Encuentra las respuestas en tus datos.',
    lessons: 11,
    time: '3 h 10 min',
    badge: '!!',
    preview: 'ejemplo6',
    tone: 'slate',
    status: { type: 'free' },
  },
];

// Colores de la franja superior de cada tarjeta
const tones = {
  violet: { bg: 'bg-[#EEEAFB]', text: 'text-[#5B45D6]', bar: 'bg-[#D9D2F6]' },
  green: { bg: 'bg-[#E6F4EC]', text: 'text-[#1F7A4D]', bar: 'bg-[#C8E6D5]' },
  amber: { bg: 'bg-[#FDF1DC]', text: 'text-[#B7791F]', bar: 'bg-[#F6DFB5]' },
  blue: { bg: 'bg-[#E8EEFB]', text: 'text-[#4C5FD5]', bar: 'bg-[#CFD9F5]' },
  peach: { bg: 'bg-[#FCEDE6]', text: 'text-[#C0623F]', bar: 'bg-[#F6D5C7]' },
  slate: { bg: 'bg-[#E8EAF6]', text: 'text-[#5B5F9E]', bar: 'bg-[#D0D3EC]' },
};

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
function LastLessonBanner({ data }) {
  return (
    <section
      aria-label="Última lección"
      className="flex flex-wrap items-center justify-between gap-6 rounded-3xl bg-[#38004F] px-8 py-9 text-white"
    >
      <div>
        <p className="font-mono text-[11px] text-purple-200">
          Continúa donde lo dejaste
        </p>
        <h2 className="mt-2 text-3xl font-medium">{data.title}</h2>
        <p className="mt-3 text-sm text-purple-100">{data.description}</p>
      </div>

      <div className="flex items-center gap-8">
        <p className="max-w-[170px] text-center font-mono text-[11px] leading-snug text-purple-200">
          {data.progressNote}
        </p>
        <div className="flex flex-col items-center gap-3">
          <button
            type="button"
            className="inline-flex items-center gap-2 rounded-lg bg-[#9B10D8] px-6 py-3 text-sm font-semibold text-white transition-colors hover:bg-[#AA25E6] focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-offset-2 focus-visible:ring-offset-[#38004F]"
          >
            Continuar <Icon name="arrow" size={16} />
          </button>
          <span className="text-[11px] text-purple-200">{data.progress}</span>
        </div>
      </div>
    </section>
  );
}

function CardFooter({ status, tone }) {
  const t = tones[tone];

  if (status.type === 'progress') {
    const pct = Math.round((status.done / status.total) * 100);
    return (
      <>
        <div className="flex items-center gap-3">
          <div className="h-1.5 w-20 overflow-hidden rounded-full bg-gray-200">
            <div
              className="h-full rounded-full bg-[#5B45D6]"
              style={{ width: `${pct}%` }}
            />
          </div>
          <span className="text-xs text-gray-500">
            {status.done} de {status.total}
          </span>
        </div>
        <button
          type="button"
          className={`inline-flex items-center gap-1 text-sm font-semibold ${t.text} hover:underline`}
        >
          Continuar <Icon name="arrow" />
        </button>
      </>
    );
  }

  if (status.type === 'completed') {
    return (
      <>
        <span className="inline-flex items-center gap-1 rounded-md bg-emerald-50 px-2.5 py-1.5 text-xs font-medium text-emerald-700">
          <Icon name="check" size={12} /> Completado
        </span>
        <button
          type="button"
          className="inline-flex items-center gap-1 text-sm font-semibold text-[#4C5FD5] hover:underline"
        >
          Repasar <Icon name="arrow" />
        </button>
      </>
    );
  }

  return (
    <>
      <span className="text-xs text-emerald-700">Acceso gratuito</span>
      <button
        type="button"
        className="inline-flex items-center gap-1 text-sm font-semibold text-[#4C5FD5] hover:underline"
      >
        Empezar <Icon name="arrow" />
      </button>
    </>
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
            <Icon name="book" /> {lesson.lessons} lecciones
          </span>
          <span className="inline-flex items-center gap-1">
            <Icon name="clock" /> {lesson.time}
          </span>
        </div>

        <div className="mt-4 flex items-center justify-between border-t border-gray-200 pt-3">
          <CardFooter status={lesson.status} tone={lesson.tone} />
        </div>
      </div>
    </article>
  );
}

/* ------------------------------------------------------------------ */
/* Vista principal                                                     */
/* ------------------------------------------------------------------ */
export function ExplorarLecciones() {
  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-semibold text-gray-900">
        ¿Qué quieres aprender hoy?
      </h1>

      <LastLessonBanner data={lastLesson} />

      <section
        aria-label="Lecciones disponibles"
        className="grid gap-5 md:grid-cols-2 xl:grid-cols-3"
      >
        {lessons.map((lesson) => (
          <LessonCard key={lesson.id} lesson={lesson} />
        ))}
      </section>

      <p className="flex items-center justify-center gap-2 pt-2 text-xs text-gray-500">
        <span className="text-[#5B45D6]">
          <Icon name="sparkle" />
        </span>
        Vamos carajo si se puede
      </p>
    </div>
  );
}

export default ExplorarLecciones;