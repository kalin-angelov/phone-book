package app.serivce;

import app.exceprion.NoExistingContact;
import app.model.Contact;
import app.model.ContactActivity;
import app.model.ContactType;
import app.model.SearchCriteria;
import app.repository.ContactRepository;
import app.web.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact addNewContact (ContactRequest contactRequest) {

        Contact contact = initializeContact(contactRequest);
        contactRepository.save(contact);

        return contact;
    }

    public List<Contact> getAllContacts () {
        return contactRepository.findAll();
    }

    public Contact getContactByPhoneNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new NoExistingContact("Contact with phone number [%s] do not exist in contact list.".formatted(phoneNumber)));
    }

    public List<Contact> getAllContactsBy(String request, SearchCriteria criteria) {

        List<Contact> contactList = new ArrayList<>();

        switch (criteria) {
            case REGION -> contactList = contactRepository.findAllByIdRegion(request);
            case FIRST_NAME -> contactList = contactRepository.findAllByFirstName(request);
            case LAST_NAME -> contactList = contactRepository.findAllByLastName(request);
        }

        if (contactList.isEmpty()) {
            throw new NoExistingContact("No contact found.");
        }

        return contactList;
    }

    public Contact editContact(UUID id, ContactRequest contactRequest) {

        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NoExistingContact("Contact not found in database"));

        if (!contactRequest.getFirstName().isEmpty()) {
            contact.setFirstName(contactRequest.getFirstName());
        }

        if (!contactRequest.getLastName().isEmpty()) {
            contact.setLastName(contactRequest.getLastName());
        }

        if (!contactRequest.getPhoneNumber().isEmpty()) {
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
        }

        if (!contactRequest.getRegion().isEmpty()) {
            contact.setRegion(contactRequest.getRegion());
        }

        if (!contactRequest.getAvatar().isEmpty()) {
            contact.setAvatar(contactRequest.getAvatar());
        }

        contact.setUpdatedOn(LocalDateTime.now());
        contactRepository.save(contact);
        return contact;
    }

    public void changeContactType(UUID id, ContactType type) {

        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NoExistingContact("Contact not found in database"));

        switch (type) {
            case WORK -> contact.setType(ContactType.WORK);
            case HOBBY -> contact.setType(ContactType.HOBBY);
            case FAMILY -> contact.setType(ContactType.FAMILY);
            case FRIEND -> contact.setType(ContactType.FRIEND);
        }

        contact.setUpdatedOn(LocalDateTime.now());
        contactRepository.save(contact);
    }

    public void changeContactActivity(UUID id) {
        
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NoExistingContact("Contact not found in database"));

        if (contact.getActivity().equals(ContactActivity.ACTIVE)) {
            contact.setActivity(ContactActivity.BLOCKED);
        } else {
            contact.setActivity(ContactActivity.ACTIVE);
        }

        contact.setUpdatedOn(LocalDateTime.now());
        contactRepository.save(contact);
    }

    private Contact initializeContact (ContactRequest contactRequest) {

        return Contact.builder()
                .firstName(contactRequest.getFirstName())
                .lastName(contactRequest.getLastName())
                .region(contactRequest.getRegion())
                .avatar(contactRequest.getAvatar())
                .phoneNumber(contactRequest.getPhoneNumber())
                .type(ContactType.FRIEND)
                .activity(ContactActivity.ACTIVE)
                .createdOn(LocalDateTime.now())
                .updatedOn(null)
                .build();
    }
}
