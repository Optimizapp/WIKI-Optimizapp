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
        model.addAttribute("description", "Recurso educativo colaborativo para estudiantes, profesores y la comunidad universitaria.");
        
        List<String> features = Arrays.asList(
            "📚 Contenido académico organizado",
            "🔍 Búsqueda avanzada",
            "👥 Colaboración en tiempo real",
            "📱 Diseño responsive",
            "🌐 Acceso desde cualquier dispositivo"
        );
        model.addAttribute("features", features);
        
        return "home";
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
