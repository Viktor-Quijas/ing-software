export function CodeaLogo(){
    return (
        <div className="inline-flex items-center gap-2.5 bg-[#F3EFFE] px-5 py-2.5 rounded-2xl shadow-md shadow-purple-200/60 border border-white">
            <div className="bg-[#7C3AED] text-white w-8 h-8 rounded-lg flex items-center justify-center font-bold text-sm">
                &lt;&gt; {/*Se cambia por el svg del logo */}
            </div>
            <span className="text-2xl font-medium text-gray-800 tracking-tight">
                Code<span className="text-gray-700">&lt;/a&gt;</span>    
            </span>
        </div>

    )
}