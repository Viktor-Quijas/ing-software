export function QuizView({
  problemStatement,
  expectedOutcome,
  exerciseTitle,
  instructions,
  codeSnippet,
  question,
  options = [],
  selectedOption,
  onSelectOption,
}) {
  const handleCopy = () => {
    if (codeSnippet) {
      navigator.clipboard?.writeText(codeSnippet);
    }
  };

  return (
    <div className="bg-white rounded-2xl border border-purple-100 p-6 lg:p-8 shadow-xs space-y-6">
      <div>
        <h2 className="text-lg font-bold text-gray-900">Planteamiento del problema</h2>
        <p className="text-xs text-gray-500 mt-1">{problemStatement}</p>
      </div>

      {expectedOutcome && (
        <div className="bg-[#F5EFFF] border border-purple-200/70 rounded-xl px-4 py-3 flex items-center gap-2.5 text-xs font-medium text-[#6D28D9]">
          <span>💡</span>
          <span>{expectedOutcome}</span>
        </div>
      )}

      <div className="space-y-2">
        <h3 className="text-sm font-bold text-gray-900">{exerciseTitle}</h3>
        <p className="text-xs text-gray-500">{instructions}</p>

        {codeSnippet && (
          <div className="bg-[#1E1B4B] text-purple-100 rounded-xl overflow-hidden mt-3">
            <div className="flex items-center justify-between px-4 py-2 bg-[#27235C] text-[11px] text-purple-200">
              <span>Elemento gráfico de ayuda</span>
              <button
                type="button"
                onClick={handleCopy}
                className="hover:text-white transition cursor-pointer"
              >
                Copiar
              </button>
            </div>
            <pre className="p-4 text-xs overflow-x-auto leading-relaxed">
              <code>{codeSnippet}</code>
            </pre>
          </div>
        )}
      </div>

      <div className="space-y-3 pt-1">
        <h3 className="text-sm font-bold text-gray-900">{question}</h3>
        <div className="space-y-2">
          {options.map((opt, index) => {
            const active = selectedOption === index;
            return (
              <button
                key={index}
                type="button"
                onClick={() => onSelectOption(index)}
                className={`w-full text-left px-4 py-2.5 rounded-xl border text-xs transition cursor-pointer ${
                  active
                    ? 'bg-[#F3EFFE] border-[#7C3AED] text-[#5B21B6] font-semibold'
                    : 'bg-gray-50/60 border-gray-200 text-gray-700 hover:bg-purple-50/40'
                }`}
              >
                {opt}
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
}