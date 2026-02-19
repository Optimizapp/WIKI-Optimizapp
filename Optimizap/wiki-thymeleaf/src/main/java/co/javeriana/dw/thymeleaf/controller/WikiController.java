package co.javeriana.dw.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class WikiController {

    // Clase para representar a un integrante del equipo
    public static class Member {
        private final String name;
        private final String github;
        private final String image;
        private final String description;

        public Member(String name, String github, String image, String description) {
            this.name = name;
            this.github = github;
            this.image = image;
            this.description = description;
        }

        public String getName() { return name; }
        public String getGithub() { return github; }
        public String getImage() { return image; }
        public String getDescription() { return description; }
    }

    // Datos compartidos (para reutilizar en varias páginas)
    private static final List<Member> MEMBERS = Arrays.asList(
            new Member("Maria Camila Beltrán", "Mcbeltranc", "camila.jpg", "Le gusta viajar, manejar bases de datos y dirigir proyectos."),
            new Member("Andrés Felipe Pinzón Márquez", "amarquez10", "andres.jpg", "Le gusta jugar videojuegos, programar páginas web y desarrollar aplicaciones móviles."),
            new Member("Mauricio Raba", "Andresm-Rabab", "mauricio.jpg", "Le gusta jugar videojuegos, programar páginas web y desarrollar aplicaciones móviles."),
            new Member("Nicolás Mateo Morales Sánchez", "Nicolasmateo2", "nicolas.jpg", "Le gusta jugar videojuegos, programar páginas web y desarrollar aplicaciones móviles."),
            new Member("Diego Fernando Zabala", "DiegoFernzab", "diego.jpg", "Le gusta jugar videojuegos, programar páginas web y desarrollar aplicaciones móviles."),
            new Member("Daniel Galvis", "daga", "daga.jpg", "Le gusta jugar videojuegos, programar páginas web y desarrollar aplicaciones móviles.")
    );

    private static final List<String> ARCHITECTURE = Arrays.asList(
            "Controladores: atienden solicitudes HTTP y preparan el Model para las vistas.",
            "Vistas (Thymeleaf): renderizan contenido dinámico sin lógica de negocio compleja.",
            "Modelo: estructuras simples que representan temas y páginas de la wiki.",
            "Fragmentos: header, footer y navegación reutilizables con fragmentos de Thymeleaf."
    );

    private static final List<TechItem> TECH_STACK = Arrays.asList(
            new TechItem("Spring Boot", "Estructura del proyecto y controladores MVC."),
            new TechItem("Thymeleaf", "Motor de plantillas y fragmentos reutilizables."),
            new TechItem("HTML/CSS", "Estructura y estilos de las vistas."),
            new TechItem("JavaScript", "Validaciones del formulario de contacto."),
            new TechItem("Docker", "Ejecución en contenedor para despliegue."),
            new TechItem("Git/GitHub", "Control de versiones y trabajo por ramas.")
    );

    private static final List<Topic> TOPICS = Arrays.asList(
            new Topic("Programación", "introduccion-programacion", "Fundamentos de programación y algoritmos"),
            new Topic("Base de Datos", "bases-datos", "Modelado y gestión de bases de datos"),
            new Topic("Estructuras de Datos", "estructuras-datos", "Estructuras de datos y algoritmos avanzados"),
            new Topic("Ingeniería de Software", "ingenieria-software", "Principios y prácticas de desarrollo de software"),
            new Topic("Redes de Computadores", "redes", "Fundamentos de redes y comunicaciones")
    );

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "home");

        model.addAttribute("title", "Inicio - Wiki Javeriana");
        model.addAttribute("welcomeMessage", "Bienvenido a la Wiki de la Universidad Javeriana");
        model.addAttribute("description",
                "Wiki académica server-side construida con Spring Boot y Thymeleaf, enfocada en arquitectura MVC, fragmentos reutilizables y renderizado dinámico.");

        // Integrantes en Home
        model.addAttribute("features", MEMBERS);

        // Detalles técnicos (Home)
        model.addAttribute("projectGoal",
                "Organizar y navegar información técnica tipo documentación, demostrando MVC, Thymeleaf (th:text, th:each, th:if), fragmentos y un formulario con validaciones en JavaScript.");

        model.addAttribute("architecture", ARCHITECTURE);
        model.addAttribute("techStack", TECH_STACK);

        model.addAttribute("deploymentNotes", Arrays.asList(
                "La aplicación debe compilar y ejecutarse sin errores.",
                "Debe ejecutarse en un contenedor Docker.",
                "El repositorio debe incluir instrucciones claras de ejecución.",
                "No se deben versionar artefactos compilados como target/."
        ));

        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage", "about");

        model.addAttribute("title", "Acerca de - Wiki Javeriana");
        model.addAttribute("pageTitle", "Acerca de la Wiki Javeriana");

        model.addAttribute("purpose",
                "Aplicación web server-side tipo wiki para organizar, presentar y navegar contenido técnico, demostrando el uso de Thymeleaf y arquitectura MVC.");

        model.addAttribute("architecture", ARCHITECTURE);
        model.addAttribute("techStack", TECH_STACK);

        // Nota para evitar afirmar tecnologías que no estén implementadas
        model.addAttribute("techNote",
                "Las tecnologías listadas corresponden a lo implementado en el repositorio del taller.");

        model.addAttribute("members", MEMBERS);

        return "about";
    }

    @GetMapping("/topics")
    public String topics(Model model) {
        model.addAttribute("activePage", "topics");

        model.addAttribute("title", "Temas - Wiki Javeriana");
        model.addAttribute("pageTitle", "Temas de la Wiki");
        model.addAttribute("topics", TOPICS);

        return "topics";
    }

    @GetMapping("/member/{index}")
    public String memberDetail(@PathVariable int index, Model model) {
        if (index < 0 || index >= MEMBERS.size()) {
            return "redirect:/";
        }
        Member member = MEMBERS.get(index);
        model.addAttribute("title", "Acerca de " + member.getName());
        model.addAttribute("member", member);
        return "member-detail";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("activePage", "contact");

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
        model.addAttribute("activePage", "contact");

        model.addAttribute("title", "Contacto - Wiki Javeriana");
        model.addAttribute("pageTitle", "Contacto");
        model.addAttribute("formSubmitted", true);

        // No se requiere guardar ni enviar; solo confirmación
        return "contact";
    }

    @GetMapping("/topic/{slug}")
    public String topicDetail(@PathVariable String slug, Model model) {
        // Busca el tema por slug (si no existe, muestra un título genérico)
        Topic found = TOPICS.stream()
                .filter(t -> t.getSlug().equals(slug))
                .findFirst()
                .orElse(new Topic("Tema", slug, "Descripción no disponible."));

        model.addAttribute("activePage", "topics");

        model.addAttribute("title", found.getName() + " - Wiki Javeriana");
        model.addAttribute("pageTitle", found.getName());
        model.addAttribute("topic", found);

        return "topic-detail";
    }

    // Clases simples para render en vistas (Modelo)
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

    public static class Topic {
        private final String name;
        private final String slug;
        private final String description;

        public Topic(String name, String slug, String description) {
            this.name = name;
            this.slug = slug;
            this.description = description;
        }

        public String getName() { return name; }
        public String getSlug() { return slug; }
        public String getDescription() { return description; }
    }
}
