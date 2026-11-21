import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logoCodea from '../assets/logotipo-codea.svg';
import { saveDiagnosticResult, skipDiagnostic } from '../utils/diagnostic';

/* ------------------------------------------------------------------ */
/* Configuración y datos de ejemplo (luego vendrán de tu API)          */
/* ------------------------------------------------------------------ */
// Si es false, el usuario NO puede omitir el examen.
const ALLOW_SKIP = false;

// Opción que se agrega a todas las preguntas para no obligar a adivinar
const IDK = { id: 'idk', text: 'No lo sé todavía' };

const questions = [
  {
    id: 'q1',
    topic: 'Variables',
    text: '¿Qué imprime este código?',
    code: ['let edad = 20;', 'edad = edad + 1;', 'console.log(edad);'].join('\n'),
    options: [
      { id: 'a', text: '20' },
      { id: 'b', text: '21' },
      { id: 'c', text: 'edad' },
      { id: 'd', text: 'Marca un error' },
    ],
    correct: 'b',
  },
  {
    id: 'q2',
    topic: 'Tipos de datos',
    text: '¿Qué tipo de dato es "42" (con comillas)?',
    options: [
      { id: 'a', text: 'Número' },
      { id: 'b', text: 'Texto (string)' },
      { id: 'c', text: 'Booleano' },
      { id: 'd', text: 'Lista (array)' },
    ],
    correct: 'b',
  },
  {
    id: 'q3',
    topic: 'Condicionales',
    text: '¿Qué imprime este código?',
    code: [
      'const x = 12;',
      '',
      'if (x > 5 && x < 10) {',
      '  console.log("A");',
      '} else {',
      '  console.log("B");',
      '}',
    ].join('\n'),
    options: [
      { id: 'a', text: 'A' },
      { id: 'b', text: 'B' },
      { id: 'c', text: 'A y luego B' },
      { id: 'd', text: 'No imprime nada' },
    ],
    correct: 'b',
  },
  {
    id: 'q4',
    topic: 'Ciclos',
    text: '¿Qué valores se imprimen?',
    code: ['for (let i = 0; i < 3; i++) {', '  console.log(i);', '}'].join('\n'),
    options: [
      { id: 'a', text: '1, 2, 3' },
      { id: 'b', text: '0, 1, 2' },
      { id: 'c', text: '0, 1, 2, 3' },
      { id: 'd', text: '3, 2, 1' },
    ],
    correct: 'b',
  },
  {
    id: 'q5',
    topic: 'Arreglos',
    text: '¿Qué imprime este código?',
    code: [
      'const frutas = ["manzana", "pera", "uva"];',
      'console.log(frutas[1]);',
    ].join('\n'),
    options: [
      { id: 'a', text: 'manzana' },
      { id: 'b', text: 'pera' },
      { id: 'c', text: 'uva' },
      { id: 'd', text: 'undefined' },
    ],
    correct: 'b',
  },
  {
    id: 'q6',
    topic: 'Funciones',
    text: '¿Cuál es el resultado de llamar a la función?',
    code: [
      'function calcular(a, b) {',
      '  return a * b + 1;',
      '}',
      '',
      'console.log(calcular(2, 3));',
    ].join('\n'),
    options: [
      { id: 'a', text: '6' },
      { id: 'b', text: '7' },
      { id: 'c', text: '8' },
      { id: 'd', text: '23' },
    ],
    correct: 'b',
  },
  {
    id: 'q7',
    topic: 'Métodos de arreglos',
    text: '¿Cuál es el valor de resultado?',
    code: [
      'const nums = [1, 2, 3, 4];',
      'const resultado = nums',
      '  .map((n) => n * 2)',
      '  .filter((n) => n > 4);',
    ].join('\n'),
    options: [
      { id: 'a', text: '[2, 4, 6, 8]' },
      { id: 'b', text: '[6, 8]' },
      { id: 'c', text: '[3, 4]' },
      { id: 'd', text: '[]' },
    ],
    correct: 'b',
  },
  {
    id: 'q8',
    topic: 'Fundamentos de JavaScript',
    text: '¿Qué imprime esta línea?',
    code: 'console.log(0.1 + 0.2 === 0.3);',
    options: [
      { id: 'a', text: 'true' },
      { id: 'b', text: 'false' },
      { id: 'c', text: 'undefined' },
      { id: 'd', text: 'Marca un error' },
    ],
    correct: 'b',
  },
];

function getLevel(score) {
  if (score >= 7) {
    return {
      name: 'Avanzado',
      message:
        '¡Tienes una base muy sólida! Puedes saltarte lo básico y avanzar directo a retos más grandes.',
      start: 'Te recomendamos empezar por las lecciones de nivel Intermedio y retos.',
    };
  }
  if (score >= 4) {
    return {
      name: 'Intermedio',
      message:
        'Ya manejas lo esencial. Con un poco de práctica vas a dominar los temas que siguen.',
      start: 'Te recomendamos empezar por funciones, arreglos y sus métodos.',
    };
  }
  return {
    name: 'Principiante',
    message:
      'Vas a empezar desde cero, y está perfecto: la ruta está pensada para llevarte paso a paso.',
    start: 'Te recomendamos empezar por las lecciones de variables y tipos de datos.',
  };
}

/* ------------------------------------------------------------------ */
/* Subcomponentes                                                      */
/* ------------------------------------------------------------------ */
function PrimaryButton({ children, ...props }) {
  return (
    <button
      type="button"
      {...props}
      className="inline-flex items-center justify-center gap-2 rounded-xl bg-purple-700 px-5 py-3 text-sm font-semibold text-white shadow-sm transition-colors hover:bg-purple-800 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-900 focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:bg-purple-300"
    >
      {children}
    </button>
  );
}

function SecondaryButton({ children, ...props }) {
  return (
    <button
      type="button"
      {...props}
      className="inline-flex items-center justify-center gap-2 rounded-xl border border-gray-200 bg-white px-5 py-3 text-sm font-semibold text-gray-900 transition-colors hover:bg-gray-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-purple-600 disabled:cursor-not-allowed disabled:opacity-50"
    >
      {children}
    </button>
  );
}

function Intro({ onStart, onSkip }) {
  return (
    <section className="rounded-3xl border border-gray-200 bg-white p-8 md:p-10">
      <p className="font-mono text-[11px] text-purple-700">EXAMEN DIAGNÓSTICO</p>
      <h1 className="mt-3 text-3xl font-bold text-gray-900">
        Te damos la bienvenida a Code&lt;/a&gt;
      </h1>
      <p className="mt-4 text-sm leading-relaxed text-gray-600">
        Antes de empezar, haz este mini examen para conocer tu punto de partida y
        recomendarte las lecciones ideales para ti. No tiene calificación y no
        afecta tu avance: si no sabes una respuesta, elige “No lo sé todavía”.
      </p>

      <ul className="mt-6 flex flex-wrap gap-3 text-xs">
        {[`${questions.length} preguntas`, '≈ 5 minutos', 'Sin calificación'].map(
          (chip) => (
            <li
              key={chip}
              className="rounded-lg bg-purple-50 px-3 py-2 font-medium text-purple-800"
            >
              {chip}
            </li>
          )
        )}
      </ul>

      <div className="mt-8 flex flex-wrap items-center gap-4">
        <PrimaryButton onClick={onStart}>Empezar examen →</PrimaryButton>
        {ALLOW_SKIP && (
          <button
            type="button"
            onClick={onSkip}
            className="text-sm text-gray-500 hover:text-purple-700 hover:underline"
          >
            Omitir por ahora
          </button>
        )}
      </div>
    </section>
  );
}

function Question({ question, index, total, answer, onAnswer, onPrev, onNext }) {
  const options = [...question.options, IDK];
  const pct = Math.round(((index + 1) / total) * 100);
  const isLast = index === total - 1;

  return (
    <section className="rounded-3xl border border-gray-200 bg-white p-8 md:p-10">
      <div className="flex items-center justify-between text-xs text-gray-500">
        <span className="font-mono text-purple-700">
          Pregunta {index + 1} de {total}
        </span>
        <span>{question.topic}</span>
      </div>
      <div
        className="mt-3 h-1.5 overflow-hidden rounded-full bg-purple-100"
        role="progressbar"
        aria-valuenow={pct}
        aria-valuemin={0}
        aria-valuemax={100}
        aria-label="Avance del examen"
      >
        <div
          className="h-full rounded-full bg-purple-700 transition-all"
          style={{ width: `${pct}%` }}
        />
      </div>

      <fieldset className="mt-8">
        <legend className="text-xl text-gray-900">{question.text}</legend>

        {question.code && (
          <pre className="mt-5 overflow-x-auto rounded-xl bg-[#2A2745] p-5 font-mono text-[13px] leading-6 text-gray-200">
            <code>{question.code}</code>
          </pre>
        )}

        <div className="mt-5 space-y-2">
          {options.map((o) => {
            const selected = answer === o.id;
            return (
              <label
                key={o.id}
                className={`relative flex cursor-pointer items-center gap-4 rounded-xl border px-4 py-3 text-sm transition-colors has-[:focus-visible]:ring-2 has-[:focus-visible]:ring-purple-300 ${
                  selected
                    ? 'border-purple-700 bg-purple-50 text-purple-900'
                    : 'border-gray-200 bg-white text-gray-700 hover:border-purple-300'
                }`}
              >
                <input
                  type="radio"
                  name={question.id}
                  value={o.id}
                  checked={selected}
                  onChange={() => onAnswer(o.id)}
                  className="sr-only"
                />
                <span
                  aria-hidden="true"
                  className={`flex h-4 w-4 shrink-0 items-center justify-center rounded-full border ${
                    selected ? 'border-purple-700' : 'border-gray-300'
                  }`}
                >
                  {selected && <span className="h-2 w-2 rounded-full bg-purple-700" />}
                </span>
                {o.text}
              </label>
            );
          })}
        </div>
      </fieldset>

      <div className="mt-8 flex items-center justify-between">
        <SecondaryButton onClick={onPrev} disabled={index === 0}>
          ← Anterior
        </SecondaryButton>
        <PrimaryButton onClick={onNext} disabled={!answer}>
          {isLast ? 'Ver resultado' : 'Siguiente →'}
        </PrimaryButton>
      </div>
    </section>
  );
}

function Result({ answers, onFinish, onRetry }) {
  const results = questions.map((q) => ({
    ...q,
    isCorrect: answers[q.id] === q.correct,
  }));
  const score = results.filter((r) => r.isCorrect).length;
  const level = getLevel(score);
  const toReview = results.filter((r) => !r.isCorrect);

  return (
    <section className="rounded-3xl border border-gray-200 bg-white p-8 md:p-10">
      <p className="font-mono text-[11px] text-purple-700">RESULTADO</p>
      <h1 className="mt-3 text-3xl font-bold text-gray-900">
        Tu nivel de partida: {level.name}
      </h1>
      <p className="mt-2 text-sm text-gray-600">{level.message}</p>

      <div className="mt-6 flex items-center gap-4 rounded-2xl bg-purple-50 p-5">
        <span className="text-4xl font-bold text-purple-700">
          {score}/{questions.length}
        </span>
        <span className="text-sm text-purple-900">respuestas correctas. {level.start}</span>
      </div>

      <h2 className="mt-8 text-lg text-gray-900">Tus respuestas</h2>
      <ul className="mt-3 divide-y divide-gray-100 rounded-xl border border-gray-200">
        {results.map((r, i) => (
          <li key={r.id} className="flex items-center justify-between px-4 py-3 text-sm">
            <span className="text-gray-700">
              {i + 1}. {r.topic}
            </span>
            <span
              className={`rounded-md px-2.5 py-1 text-xs font-medium ${
                r.isCorrect
                  ? 'bg-emerald-50 text-emerald-700'
                  : 'bg-amber-50 text-amber-700'
              }`}
            >
              {r.isCorrect ? '✓ Correcta' : 'Por reforzar'}
            </span>
          </li>
        ))}
      </ul>

      {toReview.length > 0 && (
        <p className="mt-4 text-xs text-gray-500">
          Temas a reforzar: {toReview.map((r) => r.topic).join(', ')}.
        </p>
      )}

      <div className="mt-8 flex flex-wrap items-center gap-4">
        <PrimaryButton onClick={onFinish}>Ir a mis avances →</PrimaryButton>
        <SecondaryButton onClick={onRetry}>Repetir examen</SecondaryButton>
      </div>
    </section>
  );
}

/* ------------------------------------------------------------------ */
/* Vista principal                                                     */
/* ------------------------------------------------------------------ */
export function ExamenDiagnostico() {
  const navigate = useNavigate();
  // -1 = introducción · 0..n-1 = preguntas · n = resultado
  const [step, setStep] = useState(-1);
  const [answers, setAnswers] = useState({});

  const total = questions.length;

  const score = () =>
    questions.filter((q) => answers[q.id] === q.correct).length;

  const handleSkip = () => {
    skipDiagnostic();
    navigate('/avances', { replace: true });
  };

  // Guarda el resultado en cuanto el usuario ve su calificación
  const handleShowResult = () => {
    const finalScore = score();
    saveDiagnosticResult({
      score: finalScore,
      total,
      level: getLevel(finalScore).name,
    });
    setStep(total);
  };

  const handleRetry = () => {
    setAnswers({});
    setStep(0);
  };

  const handleFinish = () => navigate('/avances', { replace: true });

  return (
    // fixed + inset-0 para ocupar toda la ventana sin crear scroll extra
    <div className="fixed inset-0 overflow-y-auto bg-codea-gray-100 font-sans">
      <header className="flex h-16 items-center bg-codea-primary-100 px-8">
        <div className="flex items-center gap-2">
          <div className="flex items-center justify-center rounded-xl bg-codea-primary-500 p-2 text-white">
            <img src={logoCodea} alt="Logo Codea" className="h-6 w-6" />
          </div>
          <span className="text-2xl font-bold tracking-tight text-gray-800">
            Code&lt;/a&gt;
          </span>
        </div>
      </header>

      <main className="mx-auto max-w-2xl px-6 py-10">
        {step === -1 && <Intro onStart={() => setStep(0)} onSkip={handleSkip} />}

        {step >= 0 && step < total && (
          <Question
            question={questions[step]}
            index={step}
            total={total}
            answer={answers[questions[step].id]}
            onAnswer={(optionId) =>
              setAnswers((prev) => ({ ...prev, [questions[step].id]: optionId }))
            }
            onPrev={() => setStep((s) => s - 1)}
            onNext={() => (step === total - 1 ? handleShowResult() : setStep((s) => s + 1))}
          />
        )}

        {step === total && (
          <Result answers={answers} onFinish={handleFinish} onRetry={handleRetry} />
        )}
      </main>
    </div>
  );
}

export default ExamenDiagnostico;