package com.codea.backend.utils;

import java.text.Normalizer;
import java.util.Locale;

public final class TextUtils {

    private TextUtils() {
    }

    /** Minúsculas y sin espacios: "  Fracciones  Básicas" == "fraccionesbásicas". */
    public static String normalize(String text) {
        return text == null ? "" : text.toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
    }

    /** Igual que {@link #normalize(String)} pero además sin acentos ("Sí" == "si"). */
    public static String normalizeNoAccents(String text) {
        return Normalizer.normalize(normalize(text), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }
}
