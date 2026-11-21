import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

/* ------------------------------------------------------------------ */
/* Datos de ejemplo (luego los reemplazas con tu API / estado global)  */
/* ------------------------------------------------------------------ */
const lesson = {
  number: 'n',
  title: 'Titulo',
  difficulty: 'Nivel de dificultad',
  duration: 'Duración',
  type: 'Tipo. ejem “Lectura + práctica”',
};

// --- Lección · Tema ---
const theme = {
  title: 'Tema',
  paragraphs: [
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.',
  ],
  highlights: [
    { label: 'Punto 1', text: 'Texto del primer punto a resaltar.' },
    { label: 'Punto 2', text: 'Texto del segundo punto a resaltar.' },
    { label: 'Punto 3', text: 'Texto del tercer punto a resaltar.' },
  ],
};

// --- Lección · Ejercicio ---
const exercise = {
  title: 'Pregunta',
  problemTitle: 'Planteamiento del problema',
  problemDescription: 'Descripción de lo que se va a realizar a continuación.',
  expected: 'Lo que se espera al terminar la lección (que sea generado con IA)',
  exerciseTitle: 'Titulo del ejercicio',
  instructions: 'Instrucciones',
  codeLabel: 'Elemento gráfico de ayuda',
  // Cada línea es una lista de [texto, tipo]
  code: [
    [['function', 'kw'], [' ', ''], ['saludar', 'fn'], ['(nombre) {', '']],
    [['  ', ''], ['return', 'kw'], [' ', ''], ['`Hola, ${nombre}!`', 'str'], [';', '']],
    [['}', '']],
    [],
    [['saludar', 'fn'], ['(', ''], ['"Andrea"', 'str'], [');', '']],
  ],
  question: '¿Cuál es la salida de la función anterior?',
  options: [
    { id: 'a', text: '“Hola, Andrea!”', correct: true },
    { id: 'b', text: '“Hola, {Andrea}!”', correct: false },
    { id: 'c', text: 'Ninguna de las anteriores.', correct: false },
  ],
  feedbackDefault:
    'Aquí va a estar todo el feedback porque no hay tiempo para desarrollar ventanas modales.',
  feedbackCorrect: '¡Correcto! La función devuelve el saludo con el nombre ya sustituido.',
  feedbackWrong: 'Casi. Revisa cómo funcionan las plantillas de texto con `${...}` e inténtalo de nuevo.',
};

const progress = {
  title: 'Título de la lección',
  completed: 4,
  total: 12,
  section: '01 · Las bases del lenguaje',
  topics: [
    { title: 'Hola, JavaScript', min: 8, state: 'done' },
    { title: 'Variables y tipos de datos', min: 12, state: 'done' },
    { title: 'Operadores y condiciones', min: 15, state: 'done' },
    { title: 'Arrays y sus métodos', min: 18, state: 'done' },
    { title: 'Funciones', min: 15, state: 'current' },
    { title: 'Objetos en JavaScript', min: 16, state: 'pending' },
  ],
  moreTopics: [
    { title: 'Manejo de errores', min: 14, state: 'pending' },
    { title: 'Módulos', min: 12, state: 'pending' },
  ],
};

/* ------------------------------------------------------------------ */
/* Iconos (SVG inline, sin dependencias)                               */
/* ------------------------------------------------------------------ */
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
    case 'arrowLeft':
      return (
        <svg {...props}>
          <path d="M19 12H5" />
          <path d="M11 6l-6 6 6 6" />
        </svg>
      );
    case 'arrowRight':
      return (
        <svg {...props}>
          <path d="M5 12h14" />
          <path d="M13 6l6 6-6 6" />
        </svg>
      );
    case 'arrowDown':
      return (
        <svg {...props}>
          <path d="M12 5v14" />
          <path d="M6 13l6 6 6-6" />
        </svg>
      );
    case 'check':
      return (
        <svg {...props}>
          <path d="M5 12l5 5 9-10" />
        </svg>
      );
    case 'clock':
      return (
        <svg {...props}>
          <circle cx="12" cy="12" r="9" />
          <path d="M12 7v5l3 2" />
        </svg>
      );
    case 'bookmark':
      return (
        <svg {...props}>
          <path d="M6 3h12v18l-6-4-6 4z" />
        </svg>
      );
    case 'bookOpen':
      return (
        <svg {...props}>
          <path d="M5 3h12a2 2 0 0 1 2 2v16H7a2 2 0 0 1-2-2z" />
          <path d="M5 17h14" />
          <path d="M10 3v6l2-1.5L14 9V3" />
        </svg>
      );
    case 'question':
      return (
        <svg {...props}>
          <rect x="4" y="3" width="16" height="18" rx="2" />
          <path d="M9.5 9.5a2.5 2.5 0 1 1 3.5 2.3c-.6.3-1 .8-1 1.5" />
          <path d="M12 16.5h.01" />
        </svg>
      );
    case 'bulb':
      return (
        <svg {...props}>
          <path d="M9 18h6" />
          <path d="M10 21h4" />
          <path d="M12 3a6 6 0 0 0-3.5 10.9c.6.5 1 1.2 1 2.1h5c0-.9.4-1.6 1-2.1A6 6 0 0 0 12 3z" />
        </svg>
      );
    case 'copy':
      return (
        <svg {...props}>
          <rect x="9" y="9" width="11" height="11" rx="2" />
          <path d="M5 15V6a2 2 0 0 1 2-2h9" />
        </svg>
      );
    case 'coffee':
      return (
        <svg {...props}>
          <path d="M5 9h12v5a5 5 0 0 1-5 5h-2a5 5 0 0 1-5-5z" />
          <path d="M17 10h1.5a2.5 2.5 0 0 1 0 5H17" />
          <path d="M9 3v2" />
          <path d="M13 3v2" />
        </svg>
      );
    default:
      return null;
  }
}

/* ------------------------------------------------------------------ */
/* Piezas compartidas por las dos vistas                               */
/* ------------------------------------------------------------------ */
function LessonHeader({ title, icon }) {
  const [saved, setSaved] = useState(false);

  return (
    <header className="space-y-3">
      <div className="flex items-center justify-between text-xs">
        <Link
          to="/lecciones"
          className="inline-flex items-center gap-2 text-sm font-semibold text-[#4C1FD0] hover:underline"
        >
          <Icon name="arrowLeft" /> Volver a la ruta
        </Link>
        <span className="inline-flex items-center gap-1 text-gray-500">
          <Icon name="check" size={12} /> Tu progreso se guarda automáticamente
        </span>
      </div>

      <div className="flex items-start justify-between gap-4">
        <div className="space-y-2">
          <p className="font-mono text-[11px] text-[#4C1FD0]">
            LECCIÓN {lesson.number} · {lesson.title}
          </p>
          <h1 className="flex items-center gap-3 text-3xl font-bold text-gray-900">
            <Icon name={icon} size={26} /> {title}
          </h1>
          <div className="flex flex-wrap items-center gap-4 pt-1 text-xs text-gray-500">
            <span className="font-semibold text-gray-600">{lesson.difficulty}</span>
            <span className="inline-flex items-center gap-1">
              <Icon name="clock" size={14} /> {lesson.duration}
            </span>
            <span>{lesson.type}</span>
          </div>
        </div>

        <button
          type="button"
          onClick={() => setSaved((s) => !s)}
          aria-pressed={saved}
          aria-label={saved ? 'Quitar de guardados' : 'Guardar lección'}
          className={`flex h-11 w-11 shrink-0 items-center justify-center rounded-xl border bg-white transition-colors focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-600 ${
            saved
              ? 'border-purple-300 text-purple-700'
              : 'border-gray-200 text-gray-700 hover:border-purple-300'
          }`}
        >
          <Icon name="bookmark" />
        </button>
      </div>
    </header>
  );
}

function LessonFooter({ previous, next }) {
  const navigate = useNavigate();

  return (
    <div className="flex items-center justify-between pt-2">
      <button
        type="button"
        onClick={() => (previous ? navigate(previous) : navigate(-1))}
        className="inline-flex items-center gap-2 text-xs text-gray-500 hover:text-purple-700"
      >
        <Icon name="arrowLeft" size={14} /> Lección anterior
      </button>

      <button
        type="button"
        onClick={() => next && navigate(next)}
        className="inline-flex items-center gap-2 rounded-xl bg-purple-700 px-5 py-3 text-sm font-semibold text-white shadow-sm transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2"
      >
        Completar y continuar <Icon name="arrowRight" />
      </button>
    </div>
  );
}

/* ------------------------------------------------------------------ */
/* Lección · Tema                                                      */
/* ------------------------------------------------------------------ */
function ArrayDiagram() {
  const values = [2, 4, 8, 12, 16];
  const startX = 150;
  const boxW = 48;

  return (
    <svg
      viewBox="0 0 440 230"
      role="img"
      aria-label="Diagrama de un arreglo de JavaScript con los valores 2, 4, 8, 12 y 16"
      className="h-auto w-full max-w-md rounded-sm bg-gray-100"
    >
      <defs>
        <marker
          id="arrowhead"
          markerWidth="8"
          markerHeight="8"
          refX="6"
          refY="4"
          orient="auto"
        >
          <path d="M0 0L8 4L0 8z" fill="#222" />
        </marker>
      </defs>

      <rect x="150" y="12" width="150" height="30" fill="#fff" />
      <text x="225" y="33" textAnchor="middle" fontSize="15" fontWeight="700" fill="#111">
        JavaScript Array
      </text>

      <text x="22" y="76" fontSize="11" fontWeight="700" fill="#111">
        Array Index
      </text>
      <line x1="92" y1="73" x2="136" y2="73" stroke="#222" markerEnd="url(#arrowhead)" />

      {values.map((v, i) => {
        const x = startX + i * boxW;
        const cx = x + boxW / 2;
        return (
          <g key={v}>
            <text x={cx} y="68" textAnchor="middle" fontSize="9" fill="#111">
              {i}
            </text>
            <rect x={x} y="80" width={boxW} height="48" fill="#fff" stroke="#333" />
            <line
              x1={cx}
              y1="128"
              x2={cx}
              y2="166"
              stroke="#222"
              markerEnd="url(#arrowhead)"
            />
            <rect x={cx - 17} y="170" width="34" height="28" fill="#fff" stroke="#333" />
            <text
              x={cx}
              y="190"
              textAnchor="middle"
              fontSize="13"
              fontWeight="700"
              fill="#111"
            >
              {v}
            </text>
          </g>
        );
      })}

      <text x="22" y="188" fontSize="11" fontWeight="700" fill="#111">
        Array Elements
      </text>
      <line x1="108" y1="185" x2="130" y2="185" stroke="#222" markerEnd="url(#arrowhead)" />
    </svg>
  );
}

export function LeccionTema() {
  const { id = '1' } = useParams();

  return (
    <div className="space-y-6">
      <LessonHeader title={theme.title} icon="bookOpen" />

      <article className="rounded-2xl border border-gray-200 bg-white">
        <div className="mx-auto max-w-4xl space-y-6 px-8 pt-14 text-sm leading-relaxed text-gray-900">
          {theme.paragraphs.map((p) => (
            <p key={p.slice(0, 24)}>{p}</p>
          ))}

          <figure className="flex justify-center py-8">
            <ArrayDiagram />
          </figure>
        </div>

        <section aria-labelledby="puntos-titulo" className="px-7 pb-8 pt-4">
          <h2 id="puntos-titulo" className="text-lg text-gray-800">
            Puntos a resaltar
          </h2>
          <ul className="mt-4 space-y-3">
            {theme.highlights.map((h) => (
              <li key={h.label} className="flex items-center gap-4 text-sm">
                <span className="w-20 rounded-md bg-purple-50 px-2.5 py-1.5 font-mono text-[11px] text-[#9B1C8F]">
                  {h.label}
                </span>
                <span className="text-gray-600">{h.text}</span>
              </li>
            ))}
          </ul>
        </section>
      </article>

      <LessonFooter next={`/lecciones/${id}/ejercicio`} />
    </div>
  );
}

/* ------------------------------------------------------------------ */
/* Lección · Ejercicio                                                 */
/* ------------------------------------------------------------------ */
const tokenColors = {
  kw: 'text-[#B9A8FF]',
  fn: 'text-[#9FE0B6]',
  str: 'text-[#F2D98B]',
  '': 'text-gray-200',
};

function CodeBlock({ label, lines }) {
  const [copied, setCopied] = useState(false);

  useEffect(() => {
    if (!copied) return undefined;
    const timer = setTimeout(() => setCopied(false), 1500);
    return () => clearTimeout(timer);
  }, [copied]);

  const plainText = lines
    .map((line) => line.map(([text]) => text).join(''))
    .join('\n');

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(plainText);
      setCopied(true);
    } catch {
      // Si el navegador bloquea el portapapeles, no pasa nada.
    }
  };

  return (
    <div className="overflow-hidden rounded-2xl bg-[#2A2745]">
      <div className="flex items-center justify-between border-b border-white/5 bg-white/5 px-5 py-3 font-mono text-[11px] text-gray-300">
        <span>{label}</span>
        <button
          type="button"
          onClick={handleCopy}
          className="inline-flex items-center gap-2 text-gray-300 hover:text-white focus:outline-none focus-visible:ring-2 focus-visible:ring-white"
        >
          <Icon name={copied ? 'check' : 'copy'} size={14} />
          {copied ? 'Copiado' : 'Copiar'}
        </button>
      </div>

      <pre className="overflow-x-auto px-5 py-5 font-mono text-[13px] leading-6">
        {lines.map((line, i) => (
          // eslint-disable-next-line react/no-array-index-key
          <div key={i} className="flex gap-5">
            <span className="w-4 select-none text-right text-gray-500">{i + 1}</span>
            <code className="whitespace-pre">
              {line.length === 0
                ? ' '
                : line.map(([text, type], j) => (
                    // eslint-disable-next-line react/no-array-index-key
                    <span key={j} className={tokenColors[type]}>
                      {text}
                    </span>
                  ))}
            </code>
          </div>
        ))}
      </pre>
    </div>
  );
}

function ProgressCard() {
  const [showAll, setShowAll] = useState(false);
  const pct = Math.round((progress.completed / progress.total) * 100);
  const topics = showAll
    ? [...progress.topics, ...progress.moreTopics]
    : progress.topics;

  return (
    <section
      aria-label="Progreso de la lección"
      className="rounded-2xl border border-gray-200 bg-white p-5"
    >
      <p className="font-mono text-[10px] text-purple-700">Progreso</p>
      <h2 className="mt-3 text-lg text-gray-900">{progress.title}</h2>

      <div className="mt-4 flex items-center justify-between text-[11px]">
        <span className="text-gray-500">
          {progress.completed} de {progress.total} completadas
        </span>
        <span className="font-mono text-purple-700">{pct}%</span>
      </div>
      <div className="mt-2 h-1.5 overflow-hidden rounded-full bg-purple-100">
        <div
          className="h-full rounded-full bg-purple-700"
          style={{ width: `${pct}%` }}
        />
      </div>

      <p className="mt-5 border-t border-gray-200 pt-5 text-xs text-gray-600">
        {progress.section}
      </p>

      <ol className="mt-4 space-y-1">
        {topics.map((t, i) => {
          const number = String(i + 1).padStart(2, '0');
          const isCurrent = t.state === 'current';
          return (
            <li
              key={t.title}
              aria-current={isCurrent ? 'step' : undefined}
              className={`flex items-center gap-3 rounded-lg px-3 py-3 ${
                isCurrent ? 'bg-purple-50' : ''
              }`}
            >
              <span
                className={`flex h-7 w-7 shrink-0 items-center justify-center rounded-full font-mono text-[9px] ${
                  t.state === 'done'
                    ? 'bg-emerald-50 text-emerald-600'
                    : isCurrent
                      ? 'bg-purple-700 text-white'
                      : 'bg-gray-100 text-gray-400'
                }`}
              >
                {t.state === 'done' ? <Icon name="check" size={12} /> : number}
              </span>
              <span>
                <span
                  className={`block text-xs ${
                    isCurrent ? 'text-purple-700' : 'text-gray-900'
                  }`}
                >
                  {t.title}
                </span>
                <span className="block text-[10px] text-gray-500">
                  {t.min} min{isCurrent ? ' · En curso' : ''}
                </span>
              </span>
            </li>
          );
        })}
      </ol>

      <button
        type="button"
        onClick={() => setShowAll((s) => !s)}
        className="mt-4 inline-flex items-center gap-1 text-xs font-medium text-purple-700 hover:underline"
      >
        {showAll ? 'Ver menos' : 'Ver el temario completo'}
        <span className={`inline-block ${showAll ? 'rotate-180' : ''}`}>
          <Icon name="arrowDown" size={12} />
        </span>
      </button>
    </section>
  );
}

function FeedbackCard({ message }) {
  return (
    <aside
      aria-live="polite"
      className="rounded-2xl bg-purple-50 p-5 text-gray-500"
    >
      <span className="text-purple-700">
        <Icon name="coffee" size={22} />
      </span>
      <h2 className="mt-3 text-base text-gray-900">Feedback</h2>
      <p className="mt-3 text-sm leading-relaxed">{message}</p>
    </aside>
  );
}

export function LeccionEjercicio() {
  const { id = '1' } = useParams();
  const [selected, setSelected] = useState(null);

  const chosen = exercise.options.find((o) => o.id === selected);
  let feedback = exercise.feedbackDefault;
  if (chosen) {
    feedback = chosen.correct ? exercise.feedbackCorrect : exercise.feedbackWrong;
  }

  return (
    <div className="space-y-6">
      <LessonHeader title={exercise.title} icon="question" />

      <div className="grid items-start gap-6 xl:grid-cols-[1fr_320px]">
        {/* Columna principal */}
        <div className="space-y-6">
          <article className="space-y-6 rounded-2xl border border-gray-200 bg-white p-7">
            <header>
              <h2 className="text-2xl text-gray-900">{exercise.problemTitle}</h2>
              <p className="mt-2 text-sm text-gray-500">
                {exercise.problemDescription}
              </p>
            </header>

            <p className="flex items-center gap-3 rounded-lg bg-purple-50 px-4 py-4 text-sm text-purple-800">
              <Icon name="bulb" />
              {exercise.expected}
            </p>

            <div>
              <h3 className="text-lg text-gray-900">{exercise.exerciseTitle}</h3>
              <p className="mt-2 text-xs text-gray-500">{exercise.instructions}</p>
            </div>

            <CodeBlock label={exercise.codeLabel} lines={exercise.code} />

            <fieldset>
              <legend className="text-lg text-gray-900">{exercise.question}</legend>
              <div className="mt-4 space-y-2">
                {exercise.options.map((o) => {
                  const isSelected = selected === o.id;
                  return (
                    <label
                      key={o.id}
                      className={`relative flex cursor-pointer items-center gap-4 rounded-lg px-3 py-3 text-sm transition-colors has-[:focus-visible]:ring-2 has-[:focus-visible]:ring-purple-300 ${
                        isSelected
                          ? 'bg-purple-50 text-purple-800'
                          : 'text-gray-600 hover:bg-gray-50'
                      }`}
                    >
                      <input
                        type="radio"
                        name="respuesta"
                        value={o.id}
                        checked={isSelected}
                        onChange={() => setSelected(o.id)}
                        className="sr-only"
                      />
                      <span
                        aria-hidden="true"
                        className={`flex h-4 w-4 shrink-0 items-center justify-center rounded-full border ${
                          isSelected ? 'border-purple-700' : 'border-gray-300'
                        }`}
                      >
                        {isSelected && (
                          <span className="h-2 w-2 rounded-full bg-purple-700" />
                        )}
                      </span>
                      {o.text}
                    </label>
                  );
                })}
              </div>
            </fieldset>
          </article>

          <LessonFooter
            previous={`/lecciones/${id}/tema`}
            next="/lecciones" // TODO: ir a la siguiente lección cuando exista
          />
        </div>

        {/* Columna lateral */}
        <div className="space-y-5">
          <ProgressCard />
          <FeedbackCard message={feedback} />
        </div>
      </div>
    </div>
  );
}