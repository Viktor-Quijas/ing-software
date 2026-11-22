package com.codea.backend.utils;

import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.Lesson;

public final class DiagnosticUtils {

    private DiagnosticUtils() {
    }

    public static boolean isDiagnosticActivity(Activity activity) {
        if (activity == null) return false;
        if ("diagnostico".equals(TextUtils.normalizeNoAccents(activity.getTypeActivity()))) {
            return true;
        }

        Lesson lesson = activity.getLesson();
        return lesson != null
                && ("examendiagnostico".equals(TextUtils.normalizeNoAccents(lesson.getTitleLesson()))
                || "diagnostico".equals(TextUtils.normalizeNoAccents(lesson.getDifficultyLesson())));
    }
}
