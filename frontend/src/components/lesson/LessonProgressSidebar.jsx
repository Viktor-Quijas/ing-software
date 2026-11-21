export function LessonProgressSidebar({
  moduleTitle = 'Título de la lección',
  completedCount = 3,
  totalCount = 12,
  sectionName = '01 · Las bases del lenguaje',
  steps = [],
  feedbackText = 'Aquí va estar todo el feedback porque no hay tiempo para desarrollar ventanas modales.',
}) {
  const percentage = totalCount > 0 ? Math.round((completedCount / totalCount) * 100) : 0;

  return (
    <aside className="w-full xl:w-80 shrink-0 space-y-5">
      {/* Tarjeta de Progreso */}
      <div className="bg-white rounded-2xl border border-purple-100 p-5 shadow-xs">
        <p className="text-[11px] font-medium text-purple-500 mb-1">Progreso</p>
        <h2 className="text-lg font-bold text-gray-900">{moduleTitle}</h2>

        <div className="mt-3">
          <div className="flex items-center justify-between text-xs mb-1.5">
            <span className="text-gray-500">
              {completedCount} de {totalCount} completadas
            </span>
            <span className="font-semibold text-[#7C3AED]">{percentage}%</span>
          </div>
          <div className="w-full h-2 bg-purple-100 rounded-full overflow-hidden">
            <div
              className="h-full bg-[#7C3AED] rounded-full transition-all duration-300"
              style={{ width: `${percentage}%` }}
            />
          </div>
        </div>

        <p className="text-[11px] font-medium text-gray-400 mt-5 mb-3">{sectionName}</p>

        <ul className="space-y-3">
          {steps.map((step) => {
            const isCurrent = step.status === 'current';
            const isDone = step.status === 'completed';

            return (
              <li
                key={step.id}
                className={`flex items-center gap-3 p-2.5 rounded-xl transition ${
                  isCurrent ? 'bg-[#F3EFFE] border border-purple-200/70' : ''
                }`}
              >
                <div
                  className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-semibold shrink-0 ${
                    isDone
                      ? 'bg-emerald-50 text-emerald-600 border border-emerald-200'
                      : isCurrent
                      ? 'bg-[#7C3AED] text-white'
                      : 'bg-gray-100 text-gray-400'
                  }`}
                >
                  {isDone ? '✓' : step.code || '•'}
                </div>

                <div className="min-w-0 flex-1">
                  <p
                    className={`text-xs font-semibold truncate ${
                      isCurrent ? 'text-[#6D28D9]' : 'text-gray-800'
                    }`}
                  >
                    {step.title}
                  </p>
                  <p className="text-[11px] text-gray-400">
                    {step.duration} {isCurrent ? '· En curso' : ''}
                  </p>
                </div>
              </li>
            );
          })}
        </ul>

        <button
          type="button"
          className="mt-4 text-xs font-semibold text-[#6D28D9] hover:underline cursor-pointer"
        >
          Ver temario completo ↓
        </button>
      </div>

      {/* Tarjeta de Feedback */}
      <div className="bg-white rounded-2xl border border-purple-100 p-5 shadow-xs">
        <div className="w-7 h-7 rounded-lg bg-purple-50 text-[#7C3AED] flex items-center justify-center text-sm mb-2">
          ☕
        </div>
        <h3 className="text-sm font-bold text-gray-900">Feedback</h3>
        <p className="text-xs text-gray-500 mt-1.5 leading-relaxed">{feedbackText}</p>
      </div>
    </aside>
  );
}