package co.javeriana.dw.thymeleaf.repository;

import co.javeriana.dw.thymeleaf.model.ContactMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<ContactMessage, Long> {
    // Spring Boot implementará automáticamente los métodos save, findById, etc.
}