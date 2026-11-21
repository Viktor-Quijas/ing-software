package com.codea.backend.config;

import com.codea.backend.entitys.StyleLearning;
import com.codea.backend.repositorys.StyleLearningRepository;
import com.codea.backend.utils.StyleCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Crea al arrancar las 6 combinaciones de estilo de aprendizaje si aún no existen. */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StyleLearningRepository styleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        for (String[] style : StyleCatalog.DEFAULT_STYLES) {
            if (styleRepository.findByNameStyleIgnoreCase(style[0]).isEmpty()) {
                styleRepository.save(StyleLearning.builder()
                        .nameStyle(style[0])
                        .descriptionStyle(style[1])
                        .build());
            }
        }
    }
}
