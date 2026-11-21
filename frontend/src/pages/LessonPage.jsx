import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { TopicView } from '../components/lesson/TopicView';
import { QuizView } from '../components/lesson/QuizView';
import { LessonProgressSidebar } from '../components/lesson/LessonProgressSidebar';

const MODULE_STEPS = [
  { id: 1, title: 'Hola, JavaScript', duration: '8 min', status: 'completed' },
  { id: 2, title: 'Variables y tipos de datos', duration: '12 min', status: 'completed' },
  { id: 3, title: 'Operadores y condiciones', duration: '15 min', status: 'completed' },
  { id: 4, title: 'Arrays y sus métodos', duration: '18 min', status: 'completed' },
  { id: 5, code: '06', title: 'Funciones', duration: '15 min', status: 'current' },
  { id: 6, title: 'Objetos en JavaScript', duration: '16 min', status: 'pending' },
];

const TOPIC_PARAGRAPHS = [
  'En esta lección revisamos los conceptos clave del tema antes de pasar al cuestionario.',
  'Lee la explicación con calma, observa cómo se estructuran los datos y relaciónala con los puntos destacados.',
];

const TOPIC_HIGHLIGHTS = [
  { label: 'Punto 1', description: 'Comprender la idea principal y su estructura.' },
  { label: 'Punto 2', description: 'Identificar cómo se relacionan los elementos.' },
  { label: 'Punto 3', description: 'Prepararte para responder el cuestionario práctico.' },
];

const QUIZ_OPTIONS = [
  '“Hola, Andrea!”',
  '“Hola, {Andrea}!”',
  'Ninguna de las anteriores.',
];

const QUIZ_CODE = `function saludar(nombre) {
  return \`Hola, \${nombre}!\`;
}

saludar("Andrea");`;

export function LessonPage() {
  const navigate = useNavigate();
  // 'topic' = presentación del tema | 'quiz' = cuestionario de la lección
  const [stage, setStage] = useState('topic');
  const [completedSteps, setCompletedSteps] = useState(3);
  const [selectedOption, setSelectedOption] = useState(null);
  const [bookmarked, setBookmarked] = useState(false);

  const totalSteps = 12;

  const handleCompleteAndContinue = () => {
    if (stage === 'topic') {
      setCompletedSteps((prev) => Math.min(prev + 1, totalSteps));
      setStage('quiz');
    } else {
      setCompletedSteps((prev) => Math.min(prev + 1, totalSteps));
      alert('¡Cuestionario completado! Avanzando a la siguiente etapa.');
    }
  };

  const handlePrevious = () => {
    if (stage === 'quiz') {
      setStage('topic');
    } else {
      navigate('/lecciones');
    }
  };

  return (
    <div className="max-w-7xl mx-auto">
      {/* Barra superior de acción dentro de la lección */}
      <div className="flex items-center justify-between text-xs mb-4">
        <Link
          to="/lecciones"
          className="font-semibold text-[#6D28D9] hover:underline flex items-center gap-1.5"
        >
          <span>←</span> Volver a la ruta
        </Link>
        <span className="text-gray-400">✓ Tu progreso se guarda automáticamente</span>
      </div>

      <div className="flex flex-col xl:flex-row gap-6 items-start">
        {/* Columna principal: Encabezado + Tema/Cuestionario + Footer */}
        <div className="flex-1 w-full min-w-0 space-y-5">
          {/* Encabezado de la lección */}
          <div className="flex items-start justify-between gap-4">
            <div>
              <p className="text-[11px] font-bold text-[#7C3AED] uppercase tracking-wider">
                LECCIÓN n · TÍTULO
              </p>
              <div className="flex items-center gap-2.5 mt-1">
                <span className="text-xl">{stage === 'topic' ? '📖' : '❓'}</span>
                <h1 className="text-2xl font-bold text-gray-900">
                  {stage === 'topic' ? 'Tema' : 'Pregunta'}
                </h1>
              </div>
              <div className="flex flex-wrap items-center gap-4 text-xs text-gray-500 mt-2">
                <span>Nivel de dificultad</span>
                <span>• Duración</span>
                <span>Tipo. ejem “Lectura + práctica”</span>
              </div>
            </div>

            <button
              type="button"
              onClick={() => setBookmarked((prev) => !prev)}
              aria-label="Guardar lección"
              className={`w-9 h-9 rounded-xl border flex items-center justify-center transition cursor-pointer ${
                bookmarked
                  ? 'bg-purple-100 border-purple-300 text-[#7C3AED]'
                  : 'bg-white border-gray-200 text-gray-500 hover:bg-gray-50'
              }`}
            >
              {bookmarked ? '🔖' : '📑'}
            </button>
          </div>

          {/* Contenido dinámico: primero Tema y luego Cuestionario */}
          {stage === 'topic' ? (
            <TopicView
              paragraphs={TOPIC_PARAGRAPHS}
              imageAlt="JavaScript Array"
              highlights={TOPIC_HIGHLIGHTS}
            />
          ) : (
            <QuizView
              problemStatement="Descripción de lo que se va a realizar a continuación."
              expectedOutcome="Lo que se espera al terminar la lección (que sea generado con IA)"
              exerciseTitle="Título del ejercicio"
              instructions="Instrucciones"
              codeSnippet={QUIZ_CODE}
              question="¿Cuál es la salida de la función anterior?"
              options={QUIZ_OPTIONS}
              selectedOption={selectedOption}
              onSelectOption={setSelectedOption}
            />
          )}

          {/* Navegación inferior */}
          <div className="flex items-center justify-between pt-2">
            <button
              type="button"
              onClick={handlePrevious}
              className="text-xs font-medium text-gray-500 hover:text-gray-800 flex items-center gap-1.5 cursor-pointer"
            >
              <span>←</span> Lección anterior
            </button>

            <button
              type="button"
              onClick={handleCompleteAndContinue}
              className="px-5 py-2.5 rounded-xl bg-[#7C3AED] hover:bg-[#6D28D9] text-white text-xs font-semibold flex items-center gap-2 transition cursor-pointer"
            >
              <span>{stage === 'topic' ? 'Ir al cuestionario' : 'Completar y continuar'}</span>
              <span>→</span>
            </button>
          </div>
        </div>

        {/* Barra lateral de progreso */}
        <LessonProgressSidebar
          completedCount={completedSteps}
          steps={MODULE_STEPS}
          totalCount={totalSteps}
        />
      </div>
    </div>
  );
}