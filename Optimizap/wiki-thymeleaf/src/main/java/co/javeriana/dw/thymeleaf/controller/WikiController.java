package co.javeriana.dw.thymeleaf.controller;

import co.javeriana.dw.thymeleaf.model.ContactMessage;
import co.javeriana.dw.thymeleaf.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class WikiController {

    // Inyectamos el servicio
    private final ContactService contactService;

    public WikiController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Inicio - Wiki Javeriana");
        model.addAttribute("welcomeMessage", "Bienvenido a la Wiki de la Universidad Javeriana");
        model.addAttribute("description", "Wiki académica server-side construida con Spring Boot y Thymeleaf.");

       
        List<TechItem> techStack = Arrays.asList(
                new TechItem("Spring Boot", "Controladores y rutas HTTP."),
                new TechItem("Thymeleaf", "Plantillas server-side dinámicas."),
                new TechItem("H2 Database", "Persistencia de datos en memoria."), // Agregado para el reporte
                new TechItem("JavaScript", "Validaciones del formulario.")
        );
        model.addAttribute("techStack", techStack);
        
        return "home";
    }

    // --- SECCIÓN DE CONTACTO ---

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contacto - Wiki Javeriana");
        model.addAttribute("pageTitle", "Contacto");
        
        // Enviamos un objeto vacío para que Thymeleaf lo vincule al formulario
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String contactSubmit(@Valid @ModelAttribute("contactMessage") ContactMessage contactMessage, 
                                BindingResult result, 
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        // 1. Verificación de errores del servidor (Validation API)
        if (result.hasErrors()) {
            model.addAttribute("title", "Contacto - Wiki Javeriana");
            model.addAttribute("pageTitle", "Contacto");
            return "contact"; // Si hay errores, vuelve al formulario mostrando los mensajes
        }

        // 2. Guardado en Base de Datos H2 a través del Servicio
        contactService.saveMessage(contactMessage);

        // 3. Feedback al usuario
        // Usamos FlashAttributes para que el mensaje sobreviva a una redirección (Post-Redirect-Get pattern)
        redirectAttributes.addFlashAttribute("successMessage", "¡Gracias! Tu mensaje ha sido guardado en la base de datos H2.");
        
        return "redirect:/contact"; 
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
            new Topic("Programación", "introduccion-programacion", "Fundamentos de programación"),
            new Topic("Base de Datos", "bases-datos", "Modelado y gestión")
        );
        model.addAttribute("topics", topics);
        return "topics";
    }

    @GetMapping("/topic/{slug}")
    public String topicDetail(Model model) {
        model.addAttribute("title", "Detalle - Wiki Javeriana");
        model.addAttribute("pageTitle", "Detalle del Tema");
        return "topic-detail";
    }

    public static class TechItem {
        private final String name;
        private final String description;
        public TechItem(String name, String description) { this.name = name; this.description = description; }
        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    public static class Topic {
        private String name; private String slug; private String description;
        public Topic(String name, String slug, String description) { this.name = name; this.slug = slug; this.description = description; }
        public String getName() { return name; }
        public String getSlug() { return slug; }
        public String getDescription() { return description; }
    }
}