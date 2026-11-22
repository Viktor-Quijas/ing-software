import { useEffect, useState } from 'react';
import { Link, useNavigate, useOutletContext, useParams } from 'react-router-dom';
import { TopicView } from '../components/lesson/TopicView';
import { apiRequest } from '../utils/api';
import { startLesson } from '../utils/progress';

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
function LessonHeader({ lesson, title, icon }) {
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
          <Icon name="check" size={12} /> Completa la actividad para guardar tu progreso
        </span>
      </div>

      <div className="flex items-start justify-between gap-4">
        <div className="space-y-2">
          <p className="font-mono text-[11px] text-[#4C1FD0]">
            LECCIÓN {lesson.idLesson} · {lesson.titleLesson}
          </p>
          <h1 className="flex items-center gap-3 text-3xl font-bold text-gray-900">
            <Icon name={icon} size={26} /> {title}
          </h1>
          <div className="flex flex-wrap items-center gap-4 pt-1 text-xs text-gray-500">
            {lesson.difficultyLesson && (
              <span className="font-semibold text-gray-600">
                {lesson.difficultyLesson}
              </span>
            )}
            {lesson.lengthLesson != null && (
              <span className="inline-flex items-center gap-1">
                <Icon name="clock" size={14} /> {lesson.lengthLesson} min
              </span>
            )}
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

function useLesson(id) {
  const [lesson, setLesson] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [reload, setReload] = useState(0);

  useEffect(() => {
    let cancelled = false;

    async function loadLesson() {
      setIsLoading(true);
      setError('');

      try {
        const data = await apiRequest(`/api/lessons/${id}`);
        if (!data?.idLesson) {
          throw new Error('El API devolvió una lección inválida.');
        }
        if (!cancelled) {
          setLesson(data);
          setIsLoading(false);
        }
      } catch (err) {
        if (!cancelled) {
          setError(err instanceof Error ? err.message : 'No se pudo cargar la lección.');
          setIsLoading(false);
        }
      }
    }

    loadLesson();
    return () => {
      cancelled = true;
    };
  }, [id, reload]);

  return {
    lesson,
    isLoading,
    error,
    retry: () => setReload((value) => value + 1),
  };
}

function usePublishLessonTitle(id, lesson) {
  const { setLessonTitle } = useOutletContext();

  useEffect(() => {
    if (lesson && String(lesson.idLesson) === id) {
      setLessonTitle(lesson.titleLesson);
    }
    return () => setLessonTitle('');
  }, [id, lesson, setLessonTitle]);
}

function LessonState({ isLoading, error, onRetry }) {
  if (isLoading) {
    return <p role="status">Cargando lección...</p>;
  }
  if (error) {
    return (
      <section className="space-y-3">
        <p role="alert" className="text-sm text-red-600">{error}</p>
        <button
          type="button"
          onClick={onRetry}
          className="mr-4 text-sm font-semibold text-purple-700 hover:underline"
        >
          Reintentar
        </button>
        <Link to="/lecciones" className="text-sm font-semibold text-purple-700 hover:underline">
          Volver a las lecciones
        </Link>
      </section>
    );
  }
  return null;
}

function splitContent(content) {
  return (content || '')
    .split(/\r?\n\s*\r?\n/)
    .map((paragraph) => paragraph.trim())
    .filter(Boolean);
}

/* ------------------------------------------------------------------ */
/* Lección · Tema                                                      */
/* ------------------------------------------------------------------ */

export function LeccionTema() {
  const { id = '1' } = useParams();
  const { lesson, isLoading, error, retry } = useLesson(id);
  usePublishLessonTitle(id, lesson);

  useEffect(() => {
    if (!isLoading && !error && String(lesson?.idLesson) === id) {
      startLesson(id);
    }
  }, [id, isLoading, error, lesson]);

  if (isLoading || error) {
    return <LessonState isLoading={isLoading} error={error} onRetry={retry} />;
  }

  const paragraphs = splitContent(lesson.contentLesson);

  return (
    <div className="space-y-6">
      <LessonHeader lesson={lesson} title="Contenido" icon="bookOpen" />

      {paragraphs.length > 0 ? (
        <TopicView paragraphs={paragraphs} />
      ) : (
        <p className="rounded-2xl border border-gray-200 bg-white p-6 text-sm text-gray-600">
          Esta lección aún no tiene contenido.
        </p>
      )}

      <LessonFooter next={`/lecciones/${id}/ejercicio`} />
    </div>
  );
}

/* ------------------------------------------------------------------ */
/* Lección · Ejercicio                                                 */
/* ------------------------------------------------------------------ */
export function LeccionEjercicio() {
  const { id = '' } = useParams();
  const { lesson, isLoading: isLessonLoading, error: lessonError, retry } = useLesson(id);
  usePublishLessonTitle(id, lesson);
  const [activity, setActivity] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [reload, setReload] = useState(0);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState('');

  useEffect(() => {
    let cancelled = false;

    async function loadActivity() {
      setIsLoading(true);
      setError('');
      setAnswers({});
      setResult(null);

      try {
        const summary = await apiRequest(`/api/activities/by-lesson/${id}`);
        if (!summary?.idActivity) {
          throw new Error('Esta lección todavía no tiene una actividad configurada.');
        }

        const detail = await apiRequest(`/api/activities/${summary.idActivity}/detail`);
        if (!Array.isArray(detail?.questions) || detail.questions.length === 0) {
          throw new Error('La actividad todavía no tiene preguntas configuradas.');
        }

        const questions = detail.questions.map((question) => ({
          id: question.idActivityQuestion,
          text: question.textQuestion,
          topic: question.topicQuestion,
          options: Array.isArray(question.options)
            ? question.options.map((option) => ({
                id: option.idActivityOption,
                text: option.textOption,
              }))
            : [],
        }));
        if (questions.some((question) => !question.id || question.options.length === 0)) {
          throw new Error('La actividad contiene preguntas sin opciones válidas.');
        }

        if (!cancelled) {
          setActivity({ ...detail, questions });
          setIsLoading(false);
        }
      } catch (err) {
        if (!cancelled) {
          setError(err instanceof Error ? err.message : 'No se pudo cargar la actividad.');
          setIsLoading(false);
        }
      }
    }

    loadActivity();
    return () => {
      cancelled = true;
    };
  }, [id, reload]);

  const handleSubmit = async () => {
    if (!activity || isSubmitting) return;
    setSubmitError('');

    const userId = Number(sessionStorage.getItem('userId'));
    if (!Number.isSafeInteger(userId) || userId <= 0) {
      setSubmitError('No se pudo identificar tu sesión. Inicia sesión nuevamente.');
      return;
    }
    if (activity.questions.some((question) => answers[question.id] == null)) {
      setSubmitError('Responde todas las preguntas antes de enviar la actividad.');
      return;
    }

    setIsSubmitting(true);
    try {
      const submittedResult = await apiRequest('/api/activity-attempts/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          idUser: userId,
          idActivity: activity.idActivity,
          answers: activity.questions.map((question) => ({
            idQuestion: question.id,
            idOption: answers[question.id],
          })),
        }),
      });

      if (!submittedResult || submittedResult.idActivityAttempt == null) {
        throw new Error('El API no confirmó el registro del intento.');
      }
      setResult(submittedResult);
    } catch (err) {
      setSubmitError(
        err instanceof Error ? err.message : 'No se pudo guardar el intento.'
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  if (isLessonLoading || isLoading) {
    return <p role="status">Cargando lección y actividad...</p>;
  }
  if (lessonError || error) {
    return (
      <section className="space-y-3">
        <p role="alert" className="text-sm text-red-600">{lessonError || error}</p>
        <button
          type="button"
          onClick={() => {
            retry();
            setReload((value) => value + 1);
          }}
          className="mr-4 text-sm font-semibold text-purple-700 hover:underline"
        >
          Reintentar
        </button>
        <Link to="/lecciones" className="text-sm font-semibold text-purple-700 hover:underline">
          Volver a las lecciones
        </Link>
      </section>
    );
  }

  return (
    <div className="space-y-6">
      <LessonHeader lesson={lesson} title={activity.titleActivity} icon="question" />

      <article className="space-y-6 rounded-2xl border border-gray-200 bg-white p-7">
        <header>
          <h2 className="text-2xl text-gray-900">{activity.descriptionActivity || activity.titleActivity}</h2>
          {activity.instructionActivity && (
            <p className="mt-2 text-sm text-gray-500">{activity.instructionActivity}</p>
          )}
        </header>

        {activity.questions.map((question, index) => (
          <fieldset key={question.id} className="border-t border-gray-100 pt-5">
            <legend className="text-lg text-gray-900">
              {index + 1}. {question.text}
            </legend>
            {question.topic && (
              <p className="mt-1 text-xs text-gray-500">{question.topic}</p>
            )}
            <div className="mt-4 space-y-2">
              {question.options.map((option) => {
                const selected = answers[question.id] === option.id;
                return (
                  <label
                    key={option.id}
                    className={`relative flex cursor-pointer items-center gap-4 rounded-lg px-3 py-3 text-sm transition-colors ${
                      selected
                        ? 'bg-purple-50 text-purple-800'
                        : 'text-gray-600 hover:bg-gray-50'
                    }`}
                  >
                    <input
                      type="radio"
                      name={`activity-question-${question.id}`}
                      value={option.id}
                      checked={selected}
                      onChange={() =>
                        setAnswers((previous) => ({
                          ...previous,
                          [question.id]: option.id,
                        }))
                      }
                      disabled={Boolean(result) || isSubmitting}
                    />
                    {option.text}
                  </label>
                );
              })}
            </div>
          </fieldset>
        ))}

        {submitError && (
          <p role="alert" className="text-sm text-red-600">{submitError}</p>
        )}

        {result ? (
          <div
            role="status"
            className={`rounded-xl p-4 ${
              result.passed
                ? 'bg-emerald-50 text-emerald-800'
                : 'bg-amber-50 text-amber-800'
            }`}
          >
            <p className="font-semibold">
              {result.passed ? '¡Actividad aprobada!' : 'Puedes volver a intentarlo.'}
            </p>
            <p className="mt-1 text-sm">
              Resultado: {result.scoreAttempt}/{result.totalQuestions}
              {result.passingScore != null && ` · Puntaje requerido: ${result.passingScore}`}
            </p>
          </div>
        ) : (
          <button
            type="button"
            onClick={handleSubmit}
            disabled={
              isSubmitting ||
              activity.questions.some((question) => answers[question.id] == null)
            }
            className="rounded-xl bg-purple-700 px-5 py-3 text-sm font-semibold text-white hover:bg-purple-800 disabled:cursor-not-allowed disabled:opacity-50"
          >
            {isSubmitting ? 'Enviando...' : 'Enviar actividad'}
          </button>
        )}
      </article>

      <div className="flex items-center justify-between">
        <Link
          to={`/lecciones/${id}/tema`}
          className="inline-flex items-center gap-2 text-xs text-gray-500 hover:text-purple-700"
        >
          <Icon name="arrowLeft" size={14} /> Volver al contenido
        </Link>
        {result?.passed ? (
          <Link
            to="/lecciones"
            className="inline-flex items-center gap-2 rounded-xl bg-purple-700 px-5 py-3 text-sm font-semibold text-white hover:bg-purple-800"
          >
            Volver a las lecciones <Icon name="arrowRight" />
          </Link>
        ) : result ? (
          <button
            type="button"
            onClick={() => {
              setAnswers({});
              setResult(null);
              setSubmitError('');
            }}
            className="rounded-xl bg-purple-700 px-5 py-3 text-sm font-semibold text-white hover:bg-purple-800"
          >
            Intentar otra vez
          </button>
        ) : null}
      </div>
    </div>
  );
}