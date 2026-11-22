package com.codea.backend.utils;

import java.util.List;
import java.util.Set;

/**
 * Catálogo de estilos de aprendizaje.
 * Las 3 preguntas de estilo del examen tienen como tópico VISUAL, AUDITIVO o KINESTESICO y se
 * responden con "sí" / "no". De ahí salen las 6 combinaciones por defecto de STYLE_LEARNING
 * (el orden de la combinación no importa).
 */
public final class StyleCatalog {

    public static final String VISUAL = "VISUAL";
    public static final String AUDITIVO = "AUDITIVO";
    public static final String KINESTESICO = "KINESTESICO";

    /** {nombre, descripción} de los 6 estilos por defecto. */
    public static final List<String[]> DEFAULT_STYLES = List.of(
            new String[]{"Visual", "Aprende mejor con imágenes, diagramas y videos"},
            new String[]{"Auditivo", "Aprende mejor escuchando explicaciones y audios"},
            new String[]{"Kinestesico", "Aprende mejor practicando y haciendo"},
            new String[]{"Visual + Auditivo", "Combina imágenes y explicaciones habladas"},
            new String[]{"Visual + Kinestesico", "Combina imágenes con práctica"},
           new String[]{"Auditivo + Kinestesico", "Combina explicaciones habladas con práctica"},
            new String[]{"Visual + Auditivo + Kinestesico", "Combina imágenes, explicaciones habladas y práctica"}
    );

    private StyleCatalog() {
    }

    /** Devuelve VISUAL / AUDITIVO / KINESTESICO si el tópico es de una pregunta de estilo, o null si no. */
    public static String styleOfTopic(String topic) {
        return switch (TextUtils.normalizeNoAccents(topic)) {
            case "visual" -> VISUAL;
            case "auditivo" -> AUDITIVO;
            case "kinestesico" -> KINESTESICO;
            default -> null;
        };
    }

    /** La respuesta de una pregunta de estilo es "sí" (sin distinguir mayúsculas ni acentos). */
    public static boolean isYes(String optionText) {
        return "si".equals(TextUtils.normalizeNoAccents(optionText));
    }

    /**
     * Nombre del estilo para los estilos marcados con "sí".
     * Devuelve null si no se puede determinar (ninguno o los tres).
     */
    public static String combinationName(Set<String> styles) {
        if (styles.isEmpty() ) return null;
        StringBuilder name = new StringBuilder();
        if (styles.contains(VISUAL)) name.append("Visual");
        if (styles.contains(AUDITIVO)) name.append(name.length() > 0 ? " + " : "").append("Auditivo");
        if (styles.contains(KINESTESICO)) name.append(name.length() > 0 ? " + " : "").append("Kinestesico");
        
        return name.toString();
    }
}
