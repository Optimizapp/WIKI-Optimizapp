package co.javeriana.dw.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class WikiController {

@GetMapping("/")
public String home(Model model) {
    model.addAttribute("title", "Inicio - Wiki Javeriana");
    model.addAttribute("welcomeMessage", "Bienvenido a la Wiki de la Universidad Javeriana");
    model.addAttribute("description",
            "Wiki académica server-side construida con Spring Boot y Thymeleaf, enfocada en MVC, fragmentos reutilizables y renderizado dinámico.");

    // Participantes
    List<String> features = Arrays.asList(
            "Maria Camila Beltrán — GitHub: Mcbeltranc",
            "Andrés Felipe Pinzón Márquez — GitHub: amarquez10",
            "Mauricio Raba — GitHub: Andresm-Rabab",
            "Nicolás Mateo Morales Sánchez — GitHub: Nicolasmateo2",
            "Diego Fernando Zabala — GitHub: DiegoFernzab"
    );
    model.addAttribute("features", features);

    // Detalles técnicos
    model.addAttribute("projectGoal",
            "Organizar y navegar información técnica tipo documentación, demostrando MVC, Thymeleaf (th:text, th:each, th:if), fragmentos/layouts y un formulario con validaciones en JavaScript.");

    List<String> architecture = Arrays.asList(
            "MVC: Controladores atienden rutas y preparan el Model.",
            "Vistas Thymeleaf: render dinámico sin lógica de negocio compleja.",
            "Modelo: estructuras simples para representar temas y páginas.",
            "Fragmentos reutilizables: header, footer y menú con th:replace."
    );
    model.addAttribute("architecture", architecture);

    List<TechItem> techStack = Arrays.asList(
            new TechItem("Spring Boot", "Controladores y rutas HTTP."),
            new TechItem("Thymeleaf", "Plantillas server-side dinámicas."),
            new TechItem("HTML/CSS", "Estructura y estilos consistentes."),
            new TechItem("JavaScript", "Validaciones del formulario según el enunciado."),
            new TechItem("Docker", "Ejecución en contenedor para despliegue."),
            new TechItem("Git/GitHub", "Ramas por funcionalidad e historial de commits.")
    );
    model.addAttribute("techStack", techStack);

    List<String> deploymentNotes = Arrays.asList(
            "La aplicación debe compilar y ejecutar sin errores.",
            "Despliegue en contenedor Docker (idealmente con docker compose).",
            "README con pasos claros de ejecución.",
            "No subir target/ ni artefactos compilados al repo."
    );
    model.addAttribute("deploymentNotes", deploymentNotes);

    return "home";
}

public static class TechItem {
    private final String name;
    private final String description;

    public TechItem(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String getName() { return name; }
    public String getDescription() { return description; }
}

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Acerca de - Wiki Javeriana");
        model.addAttribute("pageTitle", "Acerca de la Wiki Javeriana");
        return "about";
    }

    @GetMapping("/topics")
    public String topics(Model model) {
        model.addAttribute("title", "Temas - Wiki Javeriana");
        model.addAttribute("pageTitle", "Temas de la Wiki");
        
        List<Topic> topics = Arrays.asList(
            new Topic("Programación", "introduccion-programacion", "Fundamentos de programación y algoritmos"),
            new Topic("Base de Datos", "bases-datos", "Modelado y gestión de bases de datos"),
            new Topic("Estructuras de Datos", "estructuras-datos", "Estructuras de datos y algoritmos avanzados"),
            new Topic("Ingeniería de Software", "ingenieria-software", "Principios y prácticas de desarrollo de software"),
            new Topic("Redes de Computadores", "redes", "Fundamentos de redes y comunicaciones")
        );
        model.addAttribute("topics", topics);
        
        return "topics";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contacto - Wiki Javeriana");
        model.addAttribute("pageTitle", "Contacto");
        model.addAttribute("formSubmitted", false);
        return "contact";
    }

    @PostMapping("/contact")
    public String contactSubmit(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phone,
                                @RequestParam("subject") String subject,
                                @RequestParam("message") String message,
                                Model model) {
        model.addAttribute("title", "Contacto - Wiki Javeriana");
        model.addAttribute("pageTitle", "Contacto");
        model.addAttribute("formSubmitted", true);
        return "contact";
    }

    @GetMapping("/topic/{slug}")
    public String topicDetail(Model model) {
        model.addAttribute("title", "Programación - Wiki Javeriana");
        model.addAttribute("pageTitle", "Introducción a la Programación");
        return "topic-detail";
    }

    public static class Topic {
        private String name;
        private String slug;
        private String description;

        public Topic(String name, String slug, String description) {
            this.name = name;
            this.slug = slug;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getSlug() {
            return slug;
        }

        public String getDescription() {
            return description;
        }
    }
}
