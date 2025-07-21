package app.web;

import app.model.Contact;
import app.model.ContactActivity;
import app.model.ContactType;
import app.model.SearchCriteria;
import app.serivce.ContactService;
import app.web.dto.ContactEditRequest;
import app.web.dto.ContactRequest;
import app.web.dto.ContactResponse;
import app.web.dto.SearchResponse;
import app.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/contacts")
public class ContactControllerV2 {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {

        List<Contact> contactList = contactService.getAllContacts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactList);
    }

    @GetMapping("/{type}")
    public ResponseEntity<SearchResponse> getContactByContactType(@PathVariable ContactType type) {

        List<Contact> result = contactService.getAllContactsByType(type);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SearchResponse.builder()
                        .status(HttpStatus.OK.value())
                        .result(result)
                        .build());
    }

    @GetMapping("/{activity}")
    public ResponseEntity<SearchResponse> getContactByContactActivity(@PathVariable ContactActivity activity) {

        List<Contact> result = contactService.getAllContactsByActivity(activity);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SearchResponse.builder()
                        .status(HttpStatus.OK.value())
                        .result(result)
                        .build());
    }


    @GetMapping("/{criteria}/{search}")
    public ResponseEntity<SearchResponse> getContactByCriteriaAndSearch(@PathVariable SearchCriteria criteria, @PathVariable String search) {

        List<Contact> result = contactService.getAllContactsBy(criteria, search);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SearchResponse.builder()
                        .status(HttpStatus.OK.value())
                        .result(result)
                        .build());
    }

    @PostMapping("/new")
    public ResponseEntity<ContactResponse> createNewContact(@RequestBody ContactRequest contactRequest) {

        Contact newContact = contactService.addNewContact(contactRequest);
        ContactResponse response = DtoMapper.toContactResponse(newContact, HttpStatus.CREATED, "New contact successfully created.");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable UUID id, @RequestBody ContactEditRequest contactEditRequest) {

        Contact contact = contactService.editContact(id, contactEditRequest);
        ContactResponse response = DtoMapper.toContactResponse(contact, HttpStatus.OK, "Contact successfully edited.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<String> removeContact(@PathVariable UUID id) {

        contactService.deleteContact(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Contact successfully removed.");
    }
}
