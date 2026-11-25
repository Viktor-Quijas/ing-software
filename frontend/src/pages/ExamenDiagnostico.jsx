import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logoCodea from '../assets/logotipo-codea.svg';
import { apiRequest } from '../utils/api';
import { markDiagnosticCompleted, saveDiagnosticResult } from '../utils/diagnostic';

function normalizeText(value = '') {
  return value
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .trim()
    .toLowerCase();
}

function getLevel(score, total) {
  const percentage = total > 0 ? score / total : 0;
  if (percentage >= 0.75) {
    return {
      name: 'Avanzado',
      message:
        '¡Tienes una base muy sólida! Puedes saltarte lo básico y avanzar directo a retos más grandes.',
      start: 'Te recomendamos empezar por las lecciones de nivel Intermedio y retos.',
    };
  }
  if (percentage >= 0.4) {
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

function Intro({ onStart, examName, questionCount }) {
  return (
    <section className="rounded-3xl border border-gray-200 bg-white p-8 md:p-10">
      <p className="font-mono text-[11px] text-purple-700">EXAMEN DIAGNÓSTICO</p>
      <h1 className="mt-3 text-3xl font-bold text-gray-900">
        {examName}
      </h1>
      <p className="mt-4 text-sm leading-relaxed text-gray-600">
        Antes de empezar, haz este mini examen para conocer tu punto de partida y
        recomendarte las lecciones ideales para ti. Tus respuestas se usarán para
        calcular el resultado y personalizar tu experiencia.
      </p>

      <ul className="mt-6 flex flex-wrap gap-3 text-xs">
        {[`${questionCount} preguntas`, '≈ 5 minutos', 'Examen personalizado'].map(
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
      </div>
    </section>
  );
}

function Question({
  question,
  index,
  total,
  answer,
  onAnswer,
  onPrev,
  onNext,
  isSubmitting,
  submitError,
}) {
  const options = question.options;
  const pct = Math.round(((index + 1) / total) * 100);
  const isLast = index === total - 1;

  return (
    <section className="rounded-3xl border border-gray-200 bg-white p-8 md:p-10">
      <div className="flex items-center justify-between text-xs text-gray-500">
        <span className="font-mono text-purple-700">
          Pregunta {index + 1} de {total}
        </span>
        <span>{question.topic || question.difficulty || 'Diagnóstico'}</span>
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
                  name={`question-${question.id}`}
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

      {submitError && (
        <p role="alert" className="mt-4 text-sm text-red-600">
          {submitError}
        </p>
      )}

      <div className="mt-8 flex items-center justify-between">
        <SecondaryButton onClick={onPrev} disabled={index === 0}>
          ← Anterior
        </SecondaryButton>
        <PrimaryButton onClick={onNext} disabled={!answer || isSubmitting}>
          {isSubmitting ? 'Enviando...' : isLast ? 'Ver resultado' : 'Siguiente →'}
        </PrimaryButton>
      </div>
    </section>
  );
}

function Result({ result, onFinish, onRetry }) {
  const score = Number(result.scoreAttempt || 0);
  const total = Number(result.totalQuestions || 0);
  const level = getLevel(score, total);

  return (
    <section className="rounded-3xl border border-gray-200 bg-white p-8 md:p-10">
      <p className="font-mono text-[11px] text-purple-700">RESULTADO</p>
      <h1 className="mt-3 text-3xl font-bold text-gray-900">
        Tu nivel de partida: {level.name}
      </h1>
      <p className="mt-2 text-sm text-gray-600">{level.message}</p>

      <div className="mt-6 flex items-center gap-4 rounded-2xl bg-purple-50 p-5">
        <span className="text-4xl font-bold text-purple-700">
          {score}/{total}
        </span>
        <span className="text-sm text-purple-900">
          respuestas correctas. {level.start}
        </span>
      </div>

      <p className={`mt-5 text-sm font-semibold ${result.passed ? 'text-emerald-700' : 'text-amber-700'}`}>
        {result.passed
          ? 'Tu resultado quedó guardado.'
          : 'Tu resultado quedó guardado; puedes continuar con tu ruta de aprendizaje.'}
      </p>

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
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState('');
  const [loadAttempt, setLoadAttempt] = useState(0);
  const [exam, setExam] = useState(null);
  const [step, setStep] = useState(-1);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState('');

  useEffect(() => {
    let cancelled = false;

    async function loadExam() {
      setIsLoading(true);
      setLoadError('');

      try {
        const token = sessionStorage.getItem('token');
        const userId = Number(sessionStorage.getItem('userId'));
        if (!token || !Number.isSafeInteger(userId) || userId <= 0) {
          throw new Error('No se pudo identificar tu sesión. Inicia sesión nuevamente.');
        }

        const lessons = await apiRequest('/api/lessons');
        if (!Array.isArray(lessons)) {
          throw new Error('El API devolvió una lista de lecciones inválida.');
        }

        const diagnosticLesson = lessons.find((item) =>
          normalizeText(item.titleLesson) === 'examen diagnostico' ||
          normalizeText(item.difficultyLesson) === 'diagnostico'
        );
        if (!diagnosticLesson) {
          throw new Error('No se encontró la lección del examen diagnóstico en el API.');
        }

        const activity = await apiRequest(
          `/api/activities/by-lesson/${diagnosticLesson.idLesson}`
        );
        if (!activity?.idActivity) {
          throw new Error('La lección del examen diagnóstico no tiene una actividad configurada.');
        }

        const activityAttempts = await apiRequest(`/api/activity-attempts/by-user/${userId}`);
        if (!Array.isArray(activityAttempts)) {
          throw new Error('El API devolvió una lista de intentos inválida.');
        }
        if (activityAttempts.some((attempt) => Number(attempt.idActivity) === Number(activity.idActivity))) {
          markDiagnosticCompleted();
          navigate('/avances', { replace: true });
          return;
        }

        const detail = await apiRequest(`/api/activities/${activity.idActivity}/detail`);
        if (!Array.isArray(detail?.questions) || detail.questions.length === 0) {
          throw new Error('El examen diagnóstico no tiene preguntas configuradas.');
        }

        const questions = detail.questions.map((question) => ({
          id: question.idActivityQuestion,
          text: question.textQuestion,
          topic: question.topicQuestion,
          difficulty: question.difficultyQuestion,
          options: Array.isArray(question.options)
            ? question.options.map((option) => ({
                id: option.idActivityOption,
                text: option.textOption,
              }))
            : [],
        }));
        if (questions.some((question) => !question.id || question.options.length === 0)) {
          throw new Error('El examen contiene preguntas sin opciones válidas.');
        }

        if (!cancelled) {
          setExam({ ...detail, titleActivity: activity.titleActivity, questions });
          setIsLoading(false);
        }
      } catch (err) {
        if (!cancelled) {
          setLoadError(
            err instanceof Error ? err.message : 'No se pudo cargar el examen diagnóstico.'
          );
          setIsLoading(false);
        }
      }
    }

    loadExam();
    return () => {
      cancelled = true;
    };
  }, [loadAttempt, navigate]);

  const total = exam?.questions.length || 0;

  const handleShowResult = async () => {
    if (!exam || isSubmitting) return;
    setSubmitError('');

    const userId = Number(sessionStorage.getItem('userId'));
    const token = sessionStorage.getItem('token');
    if (!token || !Number.isSafeInteger(userId) || userId <= 0) {
      setSubmitError('No se pudo identificar tu sesión. Inicia sesión nuevamente.');
      return;
    }

    if (exam.questions.some((question) => answers[question.id] == null)) {
      setSubmitError('Responde todas las preguntas antes de enviar el examen.');
      return;
    }

    setIsSubmitting(true);
    try {
      const submittedResult = await apiRequest('/api/activity-attempts/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          idUser: userId,
          idActivity: exam.idActivity,
          answers: exam.questions.map((question) => ({
            idQuestion: question.id,
            idOption: answers[question.id],
          })),
        }),
      });

      if (!submittedResult || submittedResult.idActivityAttempt == null) {
        throw new Error('El API no confirmó el registro del intento diagnóstico.');
      }
      const score = Number(submittedResult.scoreAttempt || 0);
      const totalQuestions = Number(submittedResult.totalQuestions || 0);
      saveDiagnosticResult({
        score,
        total: totalQuestions,
        level: getLevel(score, totalQuestions).name,
      });
      setResult(submittedResult);
      setStep(total);
    } catch (err) {
      setSubmitError(
        err instanceof Error ? err.message : 'No se pudo guardar el intento diagnóstico.'
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleRetry = () => {
    setAnswers({});
    setResult(null);
    setSubmitError('');
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
        {isLoading && (
          <p role="status" className="rounded-3xl bg-white p-8 text-gray-700">
            Verificando tus intentos y cargando el examen...
          </p>
        )}

        {!isLoading && loadError && (
          <section className="rounded-3xl border border-gray-200 bg-white p-8">
            <p role="alert" className="text-sm text-red-600">{loadError}</p>
            <PrimaryButton
              onClick={() => setLoadAttempt((attempt) => attempt + 1)}
              className="mt-5"
            >
              Reintentar
            </PrimaryButton>
          </section>
        )}

        {!isLoading && !loadError && exam && (
          <>
            {step === -1 && (
              <Intro
                onStart={() => setStep(0)}
                examName={exam.titleActivity || 'Examen diagnóstico'}
                questionCount={total}
              />
            )}

            {step >= 0 && step < total && (
              <Question
                question={exam.questions[step]}
                index={step}
                total={total}
                answer={answers[exam.questions[step].id]}
                onAnswer={(optionId) =>
                  setAnswers((previous) => ({
                    ...previous,
                    [exam.questions[step].id]: optionId,
                  }))
                }
                onPrev={() => setStep((current) => current - 1)}
                onNext={() =>
                  step === total - 1
                    ? handleShowResult()
                    : setStep((current) => current + 1)
                }
                isSubmitting={isSubmitting}
                submitError={submitError}
              />
            )}

            {step === total && result && (
              <Result result={result} onFinish={handleFinish} onRetry={handleRetry} />
            )}
          </>
        )}
      </main>
    </div>
  );
}

export default ExamenDiagnostico;