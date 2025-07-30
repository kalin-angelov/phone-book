package app.serivce;

import app.exceprion.NoExistingContact;
import app.model.Contact;
import app.model.ContactActivity;
import app.model.ContactType;
import app.model.SearchCriteria;
import app.repository.ContactRepository;
import app.web.dto.ContactEditRequest;
import app.web.dto.ContactRequest;
import app.web.dto.SearchRequest;
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

    public Contact getContact(UUID id) {
        return contactRepository.findById(id).orElseThrow(NoExistingContact::new);
    }
    public List<Contact> getAllContacts () {
        return contactRepository.findAll();
    }

    public Contact getContactByPhoneNumber(SearchRequest request) {
        return contactRepository.findByPhoneNumber(request.getSearch()).orElseThrow(NoExistingContact::new);
    }

    public List<Contact> getAllContactsBy(SearchCriteria criteria, String search) {

        List<Contact> contactList = new ArrayList<>();

        switch (criteria) {
            case REGION -> contactList = contactRepository.findByRegion(search);
            case FIRST_NAME -> contactList = contactRepository.findByFirstName(search);
            case LAST_NAME -> contactList = contactRepository.findByLastName(search);
        }

        if (contactList.isEmpty()) {
            throw new NoExistingContact();
        }

        return contactList;
    }

    public List<Contact> getAllContactsByType(ContactType type) {

        List<Contact> contactList = new ArrayList<>();

        switch (type) {
            case WORK -> contactList = contactRepository.findByType(ContactType.WORK);
            case HOBBY -> contactList = contactRepository.findByType(ContactType.HOBBY);
            case FAMILY -> contactList = contactRepository.findByType(ContactType.FAMILY);
            case FRIEND -> contactList = contactRepository.findByType(ContactType.FRIEND);
        }

        if (contactList.isEmpty()) {
            throw new NoExistingContact();
        }

        return contactList;
    }


    public List<Contact> getAllContactsByActivity(ContactActivity activity) {

        List<Contact> contactList = new ArrayList<>();

        switch (activity) {
            case ACTIVE -> contactList = contactRepository.findByActivity(ContactActivity.ACTIVE);
            case BLOCKED -> contactList = contactRepository.findByActivity(ContactActivity.BLOCKED);
        }

        if (contactList.isEmpty()) {
            throw new NoExistingContact();
        }

        return contactList;
    }

    public Contact editContact(UUID id, ContactEditRequest contactEditRequest) {

        Contact contact = contactRepository.findById(id).orElseThrow(NoExistingContact::new);

        if (!contactEditRequest.getFirstName().equals(contact.getFirstName())) {
            contact.setFirstName(contactEditRequest.getFirstName());
        }

        if (!contactEditRequest.getLastName().equals(contact.getLastName())) {
            contact.setLastName(contactEditRequest.getLastName());
        }

        if (!contactEditRequest.getPhoneNumber().equals(contact.getPhoneNumber())) {
            contact.setPhoneNumber(contactEditRequest.getPhoneNumber());
        }

        if (!contactEditRequest.getRegion().equals(contact.getRegion())) {
            contact.setRegion(contactEditRequest.getRegion());
        }

        if (!contactEditRequest.getAvatar().equals(contact.getAvatar())) {
            contact.setAvatar(contactEditRequest.getAvatar());
        }

        if (!contactEditRequest.getType().equals(contact.getType())) {
            contact.setType(contactEditRequest.getType());
        }

        contact.setUpdatedOn(LocalDateTime.now());
        contactRepository.save(contact);
        return contact;
    }

    public void changeContactType(UUID id, ContactType type) {

        Contact contact = contactRepository.findById(id).orElseThrow(NoExistingContact::new);

        switch (type) {
            case WORK -> contact.setType(ContactType.WORK);
            case HOBBY -> contact.setType(ContactType.HOBBY);
            case FAMILY -> contact.setType(ContactType.FAMILY);
            case FRIEND -> contact.setType(ContactType.FRIEND);
        }

        contact.setUpdatedOn(LocalDateTime.now());
        contactRepository.save(contact);
    }

    public void deleteContact(UUID id) {

        contactRepository.deleteById(id);
    }

    public void changeContactActivity(UUID id) {

        Contact contact = contactRepository.findById(id).orElseThrow(NoExistingContact::new);

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
