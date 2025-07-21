package app.repository;

import app.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Optional<Contact> findByPhoneNumber(String phoneNumber);

    List<Contact> findByRegion(String request);

    List<Contact> findByFirstName(String request);

    List<Contact> findByLastName(String request);
}
