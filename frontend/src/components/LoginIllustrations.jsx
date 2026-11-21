import handMedalSvg from '../assets/login-1.svg';
import laptopsDeskSvg from '../assets/login-3.svg';
import roundTableSvg from '../assets/login-2.svg';

export function LoginIllustrations() {
  return (
    <div className="flex flex-col items-center justify-center w-full max-w-lg mx-auto select-none">
      {/* 1. Imagen Superior (Mano OK + Medalla) */}
      <div className="flex justify-center -mb-4">
        <img
          src={handMedalSvg}
          alt="Certificación y logros Codea"
          className="w-52 xl:w-60 h-auto object-contain"
        />
      </div>

      {/* 2. Fila Inferior con las dos ilustraciones de laptops */}
      <div className="grid grid-cols-2 gap-4 items-end w-full">
        {/* Abajo Izquierda */}
        <div className="flex justify-center">
          <img
            src={laptopsDeskSvg}
            alt="Espacio de estudio"
            className="w-full max-w-[230px] h-auto object-contain"
          />
        </div>

        {/* Abajo Derecha */}
        <div className="flex justify-center">
          <img
            src={roundTableSvg}
            alt="Práctica de código"
            className="w-full max-w-[230px] h-auto object-contain"
          />
        </div>
      </div>
    </div>
  );
}