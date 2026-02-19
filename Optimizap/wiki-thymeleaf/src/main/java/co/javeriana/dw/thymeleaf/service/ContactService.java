package co.javeriana.dw.thymeleaf.service;

import co.javeriana.dw.thymeleaf.model.ContactMessage;
import co.javeriana.dw.thymeleaf.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void saveMessage(ContactMessage message) {
        contactRepository.save(message);
    }
}