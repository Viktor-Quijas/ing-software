package com.codea.backend.config;

import com.codea.backend.entitys.Activity;
import com.codea.backend.entitys.ActivityOption;
import com.codea.backend.entitys.ActivityQuestion;
import com.codea.backend.entitys.Lesson;
import com.codea.backend.entitys.StyleLearning;
import com.codea.backend.repositorys.ActivityOptionRepository;
import com.codea.backend.repositorys.ActivityQuestionRepository;
import com.codea.backend.repositorys.ActivityRepository;
import com.codea.backend.repositorys.LessonRepository;
import com.codea.backend.repositorys.StyleLearningRepository;
import com.codea.backend.utils.StyleCatalog;
import com.codea.backend.utils.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Crea al arrancar:
 *  - las combinaciones de estilo de aprendizaje (si aún no existen);
 *  - 3 lecciones de POO y 3 de Estructuras de Datos, cada una con su actividad (cuestionario),
 *    sus preguntas y sus opciones (si la lección aún no existe, se compara por título).
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StyleLearningRepository styleRepository;
    private final LessonRepository lessonRepository;
    private final ActivityRepository activityRepository;
    private final ActivityQuestionRepository questionRepository;
    private final ActivityOptionRepository optionRepository;

    /** Pregunta de opción múltiple: texto, índice (desde 0) de la opción correcta y las opciones. */
    private record Q(String text, int correct, List<String> options) {
    }

    private static Q q(String text, int correct, String... options) {
        return new Q(text, correct, List.of(options));
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedStyles();
        seedLessons();
    }

    private void seedStyles() {
        for (String[] style : StyleCatalog.DEFAULT_STYLES) {
            if (styleRepository.findByNameStyleIgnoreCase(style[0]).isEmpty()) {
                styleRepository.save(StyleLearning.builder()
                        .nameStyle(style[0])
                        .descriptionStyle(style[1])
                        .build());
            }
        }
    }

    // ------------------------------------------------------------------
    // Lecciones
    // ------------------------------------------------------------------
    private void seedLessons() {
        Set<String> existing = lessonRepository.findAll().stream()
                .map(l -> TextUtils.normalize(l.getTitleLesson()))
                .collect(Collectors.toSet());

        // ===================== POO =====================
        createLesson(existing, "Clases y objetos", "Principiante", 15,
                """
                Una clase es una plantilla que describe qué datos (atributos) y qué comportamientos (métodos) tendrán los objetos que se creen a partir de ella. Un objeto es una instancia concreta de esa clase, con sus propios valores.

                Por ejemplo, la clase Perro puede definir los atributos nombre y edad y el método ladrar(). Cada objeto creado con new Perro(...) es un perro distinto, aunque todos comparten la misma estructura.

                Ejemplo:
                class Perro {
                    String nombre;
                    int edad;

                    Perro(String nombre, int edad) {
                        this.nombre = nombre;
                        this.edad = edad;
                    }

                    void ladrar() {
                        System.out.println(nombre + " dice: ¡Guau!");
                    }
                }

                Perro p = new Perro("Rex", 3);
                p.ladrar();

                Puntos clave:
                - Clase: plantilla. Objeto: instancia de esa plantilla.
                - Los atributos guardan el estado; los métodos definen el comportamiento.
                - El constructor inicializa el objeto cuando se crea con new.""",
                "Cuestionario: Clases y objetos",
                "Comprueba que distingues clase, objeto y constructor.",
                "Responde las 3 preguntas. Necesitas al menos 2 respuestas correctas para completar la lección.",
                List.of(
                        q("¿Qué es una clase en la programación orientada a objetos?", 0,
                                "Una plantilla que define los atributos y métodos de los objetos",
                                "Un objeto que ya fue creado en memoria",
                                "Una variable que solo guarda un número"),
                        q("Considera este código:\n\nPerro p = new Perro(\"Rex\", 3);\nPerro q = new Perro(\"Luna\", 5);\n\n¿Cuántos objetos se crean?", 1,
                                "Uno, porque los dos son de la clase Perro",
                                "Dos, uno por cada uso de new",
                                "Ninguno, solo se declara la clase"),
                        q("¿Para qué sirve el constructor de una clase?", 2,
                                "Para borrar el objeto de la memoria",
                                "Para imprimir los atributos en pantalla",
                                "Para inicializar el objeto cuando se crea con new")));

        createLesson(existing, "Encapsulamiento y abstracción", "Intermedio", 20,
                """
                El encapsulamiento consiste en ocultar el estado interno de un objeto: los atributos se declaran private y solo se accede a ellos mediante métodos públicos (getters, setters u otras operaciones) que pueden validar los datos.

                La abstracción consiste en mostrar únicamente lo esencial de un objeto y esconder los detalles de cómo está implementado. En Java se logra con clases abstractas e interfaces.

                Ejemplo:
                class Cuenta {
                    private double saldo;

                    public void depositar(double monto) {
                        if (monto > 0) {
                            saldo += monto;
                        }
                    }

                    public double getSaldo() {
                        return saldo;
                    }
                }

                Puntos clave:
                - private protege el estado interno del objeto.
                - Los métodos públicos controlan cómo se modifican los datos.
                - La abstracción expone qué hace un objeto, no cómo lo hace.""",
                "Cuestionario: Encapsulamiento y abstracción",
                "Verifica que entiendes cómo se protege el estado de un objeto.",
                "Responde las 3 preguntas. Necesitas al menos 2 respuestas correctas para completar la lección.",
                List.of(
                        q("¿Qué logra el encapsulamiento?", 1,
                                "Que todos los atributos sean accesibles desde cualquier clase",
                                "Proteger el estado interno del objeto y controlar cómo se modifica",
                                "Que una clase pueda heredar de varias clases a la vez"),
                        q("Dada la clase Cuenta con el atributo private double saldo, ¿qué pasa si desde otra clase se escribe c.saldo = 500; ?", 0,
                                "Error de compilación: saldo es privado y no se puede acceder desde fuera",
                                "Se asigna 500 al saldo sin ningún problema",
                                "Se llama automáticamente al método depositar(500)"),
                        q("¿Cuál frase describe mejor la abstracción?", 2,
                                "Copiar el código de una clase dentro de otra",
                                "Convertir un objeto en un arreglo de datos",
                                "Mostrar solo lo esencial de un objeto y ocultar los detalles de su implementación")));

        createLesson(existing, "Herencia y polimorfismo", "Avanzado", 25,
                """
                La herencia permite que una clase (subclase) reutilice los atributos y métodos de otra (superclase) usando la palabra extends. La subclase puede agregar comportamiento nuevo o sobrescribir el heredado con @Override.

                El polimorfismo significa que una misma llamada a un método puede comportarse de forma distinta según el tipo real del objeto. Una variable de tipo Animal puede apuntar a un Perro o a un Gato, y al llamar sonido() se ejecuta la versión del objeto real.

                Ejemplo:
                class Animal {
                    void sonido() { System.out.println("..."); }
                }

                class Perro extends Animal {
                    @Override
                    void sonido() { System.out.println("Guau"); }
                }

                class Gato extends Animal {
                    @Override
                    void sonido() { System.out.println("Miau"); }
                }

                Animal a = new Gato();
                a.sonido(); // imprime Miau

                Puntos clave:
                - extends crea la relación de herencia entre clases.
                - @Override reemplaza un método de la superclase en la subclase.
                - Con polimorfismo, el método ejecutado depende del objeto real, no del tipo de la variable.""",
                "Cuestionario: Herencia y polimorfismo",
                "Pon a prueba herencia, sobrescritura y polimorfismo.",
                "Responde las 3 preguntas. Necesitas al menos 2 respuestas correctas para completar la lección.",
                List.of(
                        q("Considera este código:\n\nclass Animal { void sonido() { System.out.println(\"...\"); } }\nclass Perro extends Animal { @Override void sonido() { System.out.println(\"Guau\"); } }\n\nAnimal a = new Perro();\na.sonido();\n\n¿Qué imprime?", 2,
                                "...",
                                "Error de compilación",
                                "Guau"),
                        q("¿Qué palabra clave se usa en Java para que una clase herede de otra?", 1,
                                "implements",
                                "extends",
                                "inherits"),
                        q("¿Qué es el polimorfismo?", 0,
                                "Que una misma llamada a un método se comporta distinto según el tipo real del objeto",
                                "Que una clase tenga muchos atributos privados",
                                "Que un objeto no pueda modificarse una vez creado")));

        // ===================== Estructuras de datos =====================
        createLesson(existing, "Arreglos y listas", "Principiante", 12,
                """
                Un arreglo guarda elementos del mismo tipo en posiciones contiguas de memoria. Se accede a cada elemento por su índice, que empieza en 0, y esa operación es muy rápida (tiempo constante). Su tamaño se define al crearlo y no cambia.

                Una lista dinámica, como ArrayList en Java, funciona de forma parecida pero crece automáticamente cuando agregas elementos. Insertar o eliminar en medio de la lista obliga a desplazar los elementos siguientes.

                Ejemplo:
                int[] numeros = {10, 20, 30, 40};
                System.out.println(numeros[0]); // 10
                System.out.println(numeros[3]); // 40

                ArrayList<String> nombres = new ArrayList<>();
                nombres.add("Ana");
                nombres.add("Luis");

                Puntos clave:
                - El primer elemento está en el índice 0.
                - Acceder por índice es una operación de tiempo constante.
                - El arreglo tiene tamaño fijo; la lista dinámica puede crecer.""",
                "Cuestionario: Arreglos y listas",
                "Revisa cómo se accede a los elementos y qué diferencia hay entre arreglo y lista.",
                "Responde las 3 preguntas. Necesitas al menos 2 respuestas correctas para completar la lección.",
                List.of(
                        q("Considera este código:\n\nint[] n = {10, 20, 30, 40};\nSystem.out.println(n[2]);\n\n¿Qué imprime?", 1,
                                "20",
                                "30",
                                "40"),
                        q("¿Qué característica tiene un arreglo de tamaño fijo?", 0,
                                "Su tamaño se define al crearlo y no cambia",
                                "Crece automáticamente al agregar elementos",
                                "Solo puede guardar texto"),
                        q("¿Qué ventaja tiene acceder a un elemento de un arreglo por su índice?", 2,
                                "Hay que recorrer todos los elementos anteriores para llegar a él",
                                "Solo funciona si el arreglo está ordenado",
                                "Es una operación rápida, de tiempo constante")));

        createLesson(existing, "Pilas y colas", "Intermedio", 18,
                """
                Una pila (stack) sigue el principio LIFO: el último elemento en entrar es el primero en salir. Sus operaciones principales son push (apilar), pop (desapilar) y peek (ver el elemento de arriba sin quitarlo).

                Una cola (queue) sigue el principio FIFO: el primer elemento en entrar es el primero en salir. Sus operaciones principales son enqueue (encolar) y dequeue (desencolar).

                Ejemplo:
                Deque<Integer> pila = new ArrayDeque<>();
                pila.push(1);
                pila.push(2);
                pila.push(3);
                pila.pop(); // devuelve 3

                Queue<String> cola = new LinkedList<>();
                cola.add("A");
                cola.add("B");
                cola.poll(); // devuelve A

                Puntos clave:
                - Pila: LIFO. Se usa en deshacer/rehacer y en la pila de llamadas.
                - Cola: FIFO. Se usa en filas de impresión y en turnos de atención.
                - Ambas permiten agregar y quitar elementos en un solo extremo de acceso.""",
                "Cuestionario: Pilas y colas",
                "Comprueba que distingues LIFO de FIFO y sabes cuándo usar cada una.",
                "Responde las 3 preguntas. Necesitas al menos 2 respuestas correctas para completar la lección.",
                List.of(
                        q("En una pila vacía se hacen push(1), push(2), push(3) y luego pop(). ¿Qué valor devuelve pop()?", 2,
                                "1",
                                "2",
                                "3"),
                        q("Una cola funciona con el principio FIFO. ¿Qué significa?", 0,
                                "El primer elemento en entrar es el primero en salir",
                                "El último elemento en entrar es el primero en salir",
                                "Los elementos salen siempre en orden alfabético"),
                        q("¿Qué estructura es la más adecuada para implementar la función deshacer (Ctrl+Z) de un editor?", 1,
                                "Una cola",
                                "Una pila",
                                "Un arreglo ordenado")));

        createLesson(existing, "Árboles binarios", "Avanzado", 25,
                """
                Un árbol es una estructura jerárquica formada por nodos. El nodo superior se llama raíz, los nodos sin hijos se llaman hojas y cada nodo de un árbol binario tiene como máximo dos hijos: izquierdo y derecho.

                En un árbol binario de búsqueda (ABB), los valores menores que el de un nodo van en su subárbol izquierdo y los mayores en el derecho. Si el árbol está balanceado, buscar un valor toma tiempo logarítmico. Los recorridos más comunes son inorden (izquierdo, nodo, derecho), preorden (nodo, izquierdo, derecho) y postorden (izquierdo, derecho, nodo).

                Ejemplo (se insertan 8, 3, 10, 1, 6):
                        8
                       / \\
                      3   10
                     / \\
                    1   6

                Recorrido inorden: 1, 3, 6, 8, 10

                Puntos clave:
                - Raíz: nodo superior. Hoja: nodo sin hijos.
                - En un ABB, menores a la izquierda y mayores a la derecha.
                - El recorrido inorden de un ABB devuelve los valores ordenados.""",
                "Cuestionario: Árboles binarios",
                "Valida que entiendes la estructura y los recorridos de un árbol binario de búsqueda.",
                "Responde las 3 preguntas. Necesitas al menos 2 respuestas correctas para completar la lección.",
                List.of(
                        q("En un árbol binario de búsqueda, ¿dónde se ubica un valor menor que el de un nodo?", 0,
                                "En su subárbol izquierdo",
                                "En su subárbol derecho",
                                "En la raíz del árbol"),
                        q("Se insertan en un ABB los valores 8, 3, 10, 1 y 6 (en ese orden). ¿Qué devuelve el recorrido inorden?", 1,
                                "8, 3, 1, 6, 10",
                                "1, 3, 6, 8, 10",
                                "1, 6, 3, 10, 8"),
                        q("¿Cómo se llama un nodo que no tiene hijos?", 2,
                                "Raíz",
                                "Padre",
                                "Hoja")));
    }

    /**
     * Crea la lección con su actividad, preguntas y opciones.
     * Si ya existe una lección con ese título, no hace nada (así no se duplica al reiniciar).
     */
    private void createLesson(Set<String> existingTitles, String title, String difficulty, int minutes,
                              String content, String activityTitle, String activityDescription,
                              String activityInstruction, List<Q> questions) {
        if (existingTitles.contains(TextUtils.normalize(title))) {
            return;
        }

        Lesson lesson = lessonRepository.save(Lesson.builder()
                .titleLesson(title)
                .authorLesson("Codea")
                .contentLesson(content)
                .lengthLesson(minutes)
                .difficultyLesson(difficulty)
                .statusLesson("ACTIVO")
                .build());

        Activity activity = activityRepository.save(Activity.builder()
                .titleActivity(activityTitle)
                .descriptionActivity(activityDescription)
                .typeActivity("Cuestionario")
                .instructionActivity(activityInstruction)
                .lengthActivity(questions.size() * 2)
                .passingScore(2.0)
                .lesson(lesson)
                .build());

        int orderQuestion = 1;
        for (Q q : questions) {
            ActivityQuestion question = questionRepository.save(ActivityQuestion.builder()
                    .textQuestion(q.text())
                    .topicQuestion(title)
                    .difficultyQuestion(difficulty)
                    .orderQuestion(orderQuestion++)
                    .typeQuestion("OPCION_MULTIPLE")
                    .activity(activity)
                    .build());

            List<ActivityOption> options = new ArrayList<>();
            for (int i = 0; i < q.options().size(); i++) {
                options.add(ActivityOption.builder()
                        .textOption(q.options().get(i))
                        .isCorrect(i == q.correct())
                        .orderOption(i + 1)
                        .activityQuestion(question)
                        .build());
            }
            optionRepository.saveAll(options);
        }
    }
}
