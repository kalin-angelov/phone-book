package app.web.mapper;

import app.model.Contact;
import app.web.dto.ContactResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class DtoMapper {

    public ContactResponse toContactResponse(Contact contact, HttpStatus status, String message) {

        return ContactResponse.builder()
                .status(status.value())
                .message(message)
                .contact(Contact.builder()
                        .id(contact.getId())
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .region(contact.getRegion())
                        .avatar(contact.getAvatar())
                        .phoneNumber(contact.getPhoneNumber())
                        .type(contact.getType())
                        .activity(contact.getActivity())
                        .createdOn(contact.getCreatedOn())
                        .updatedOn(contact.getUpdatedOn())
                        .build())
                .build();
    }
}
