// src/components/lesson/TopicView.jsx
export function TopicView({ paragraphs = [], imageSrc, imageAlt = 'Diagrama del tema', highlights = [] }) {
  return (
    <div className="bg-white rounded-2xl border border-purple-100 p-6 lg:p-8 shadow-xs space-y-6">
      <div className="space-y-4 text-sm text-gray-700 leading-relaxed">
        {paragraphs.map((text, index) => (
          <p key={index}>{text}</p>
        ))}
      </div>

      {imageSrc && (
        <div className="flex justify-center py-2">
          <img
            src={imageSrc}
            alt={imageAlt}
            className="max-w-md w-full h-auto rounded-xl object-contain border border-gray-100"
          />
        </div>
      )}

      {highlights.length > 0 && (
        <div className="pt-2 space-y-2.5">
          <h3 className="text-sm font-bold text-gray-900">Puntos a resaltar</h3>
          <ul className="space-y-2">
            {highlights.map((item, idx) => (
              <li key={idx} className="flex items-center gap-3 text-xs text-gray-600">
                <span className="px-2 py-0.5 rounded-md bg-purple-100 text-[#6D28D9] font-semibold">
                  {item.label}
                </span>
                <span>{item.description}</span>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}