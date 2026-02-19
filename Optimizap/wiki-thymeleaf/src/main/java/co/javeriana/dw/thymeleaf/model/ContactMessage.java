package co.javeriana.dw.thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contact_messages")
@Data // Esto quita los errores de getters/setters si instalaste la extensión de Lombok
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;    
    private String email;   
    private String phone;   
    private String subject; 

    @Column(length = 400)
    private String message; 
}